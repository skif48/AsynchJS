package server;

import engine.JSInterpreter;
import engine.TransferData;
import tools.Tools;

import javax.script.ScriptException;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.*;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

/**
 * Created by Vladyslav Usenko on 18.06.2016.
 */
public enum Service {
    INSTANCE;

    private final Logger LOGGER = Logger.getLogger(Service.class.getName());
    private final String LOG_FILE = "E:/[AsynchJS]ServiceLOG.log";

    private final Repository repository;
    private final LinkedBlockingQueue<Task> queue;
    private final ExecutorService executorService;
    private final ConcurrentHashMap<UUID, Future<TransferData>> uuidFutureConcurrentHashMap;

    Service() {
        this.repository = new Repository();
        this.queue = new LinkedBlockingQueue<Task>();
        this.executorService = Executors.newCachedThreadPool();
        this.uuidFutureConcurrentHashMap = new ConcurrentHashMap<UUID, Future<TransferData>>();
        try {
            Tools.loggerInit(new FileHandler(), LOG_FILE, LOGGER);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void manageTask(String javascript, UUID uuid, int timeout){
        LOGGER.info("MANAGE TASK");
        this.repository.putTask(new Task(javascript, uuid, timeout));
        try{
            JSInterpreter.preCompileJS(javascript);
        } catch (ScriptException exc){
            LOGGER.info("Script exception occurred with " + uuid.toString() + ": " + exc.getMessage());
            this.repository.setCompleted(uuid);
        }
        queue.add(new Task(javascript, uuid, timeout));
        runTask(new Task(javascript, uuid, timeout));
    }

    private void runTask(Task task){
        LOGGER.info(task.getUuid() + " started running");
        Future<TransferData> future = executorService.submit(new JSInterpreter(new Task(task)));
        uuidFutureConcurrentHashMap.put(task.getUuid(), future);
        for(;;){
            if(future.isDone()){
                LOGGER.info("FUTURE IS DONE: " + future.isDone());
                try {
                    TransferData transferData = future.get();
                    this.repository.getTask(task.getUuid()).setStatus(Task.Status.COMPLETED);
                    this.repository.getTask(task.getUuid()).setConsoleOutput(transferData.getConsoleOutput());
                    this.repository.getTask(task.getUuid()).setException(transferData.getException());
                    break;
                } catch (Exception e){
                    LOGGER.info(e.toString());
                    break;
                }
            }
        }
    }

    public Task getTaskInfo(UUID uuid){
        return this.repository.getTask(uuid);
    }

    public Repository getRepository() {
        return repository;
    }
}
