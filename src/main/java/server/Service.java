package server;

import engine.JSInterpreter;
import engine.JSThreadsListener;
import engine.TaskTransferData;
import tools.Tools;

import javax.script.ScriptException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.*;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

/**
 * Created by Vladyslav Usenko on 10.07.2016.
 */
public class Service implements Listener{
    private final Logger LOGGER = Logger.getLogger(Service.class.getName());
    private final String LOG_FILE = "E:/[AsynchJS]ServiceLOG.log";

    private final Repository repository;
    private final LinkedBlockingQueue<Task> queue;
    private final ExecutorService executorService;
    private final ConcurrentHashMap<UUID, Future<TaskTransferData>> uuidFutureConcurrentHashMap;
    private JSThreadsListener jsThreadsListener;

    public Service(Repository repository, LinkedBlockingQueue<Task> queue, ConcurrentHashMap<UUID, Future<TaskTransferData>> uuidFutureConcurrentHashMap) {
        this.repository = repository;
        this.queue = queue;
        this.executorService = Executors.newCachedThreadPool();
        this.uuidFutureConcurrentHashMap = uuidFutureConcurrentHashMap;
        this.jsThreadsListener = new JSThreadsListener(new ConcurrentHashMap<UUID, Future<TaskTransferData>>(), this);

        init();
    }

    private void init(){
        try {
            Tools.loggerInit(new FileHandler(), LOG_FILE, LOGGER);
        } catch (IOException e) {
            e.printStackTrace();
        }

        LOGGER.info("setting up threads listener...");
        executorService.submit(jsThreadsListener);
    }

    public void manageTask(String javascript, UUID uuid, int timeout){
        LOGGER.info("task: " + uuid.toString() + "; entering service...");
        this.repository.putTask(new Task(javascript, uuid, timeout));
        try{
            JSInterpreter.preCompileJS(javascript);
        } catch (ScriptException exc){
            LOGGER.info("script exception occurred with " + uuid.toString() + ": " + exc.getMessage());
            this.repository.setCompleted(uuid);
        }
        queue.add(new Task(javascript, uuid, timeout));
        LOGGER.info("task: " + uuid.toString() + "; sending for execution...");
        runTask(new Task(javascript, uuid, timeout));
    }

    private void runTask(Task task){
        Future<TaskTransferData> future = executorService.submit(new JSInterpreter(new Task(task)));
        uuidFutureConcurrentHashMap.put(task.getUuid(), future);
        this.repository.setRunning(task.getUuid());
        this.jsThreadsListener.addFuture(future, task.getUuid());
    }

    public Task getTaskInfo(UUID uuid){
        return this.repository.getTask(uuid);
    }

    public Repository getRepository() {
        return repository;
    }

    public void killTask(UUID uuid){
        LOGGER.info("task: " + uuid.toString() + "; killing execution...");
        try {
            this.uuidFutureConcurrentHashMap.get(uuid).cancel(true);
        } catch (Exception e){
            LOGGER.info("task: " + uuid.toString() + "; exception during killing the task: " + e.toString());
        }
    }

    public ArrayList<Task> getAllTasksInfo(){
        return new ArrayList<Task>(this.repository.getRepository().values());
    }

    public void deleteTask(UUID uuid){
        Task task = this.repository.getTask(uuid);
        if(task.getStatus().equals(Task.Status.RUNNING)){
            killTask(uuid);
        }
        this.repository.getRepository().remove(uuid);
    }

    public void onListen(TaskTransferData taskTransferData) {
        this.repository.getTask(taskTransferData.getUuid()).getDataFromTaskTransferData(taskTransferData);
    }
}
