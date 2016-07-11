package engine;

import server.Listener;
import server.Service;
import server.Task;
import tools.Tools;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

/**
 * Created by Vladyslav Usenko on 10.07.2016.
 */
public class JSThreadsListener implements Runnable {
    private final Logger LOGGER = Logger.getLogger(Service.class.getName());
    private final String LOG_FILE = "E:/[AsynchJS]JSThreadsListenerLOG.log";

    private ConcurrentHashMap<UUID, Future<TaskTransferData>> map;
    private Listener listener;

    public JSThreadsListener(ConcurrentHashMap<UUID, Future<TaskTransferData>> map, Listener listener) {
        this.map = map;
        this.listener = listener;
        try {
            Tools.loggerInit(new FileHandler(), LOG_FILE, LOGGER);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        LOGGER.info("threads listener successfully started");
        while (true){
            TaskTransferData taskTransferData = null;
            for (Future<TaskTransferData> future : map.values()) {
                if(future.isDone()){
                    try {
                        taskTransferData = future.get();
                        LOGGER.info(taskTransferData.toString());
                        LOGGER.info("task: " + taskTransferData.getUuid() + "; finished");
                        listener.onListen(taskTransferData);
                        break;
                    } catch(CancellationException cancellation) {
                        UUID uuid = Tools.getKeyByValue(this.map, future);
                        LOGGER.info("task: " + uuid + "; killed");
                        taskTransferData = new TaskTransferData("", uuid, null, Task.Status.KILLED);
                        listener.onListen(taskTransferData);
                    } catch (Exception e){
                        LOGGER.info(e.toString());
                        break;
                    }
                }
            }
            if(taskTransferData != null)
                this.map.remove(taskTransferData.getUuid());
        }
    }

    public void addFuture(Future<TaskTransferData> future, UUID uuid){
        this.map.put(uuid, future);
    }
}
