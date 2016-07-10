package server;

import tools.Tools;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

/**
 * Created by Vladyslav Usenko on 18.06.2016.
 */
public class Repository {
    private final Map<UUID, Task> repository;
    private static final Logger LOGGER = Logger.getLogger(Repository.class.getName());
    private static final String LOG_FILE = "E:/[AsynchJS]RepositoryLOG.log";

    public Repository() {
        this.repository = new ConcurrentHashMap<UUID, Task>();
        try {
            Tools.loggerInit(new FileHandler(), LOG_FILE, LOGGER);
        } catch (IOException io){
            io.printStackTrace();
        }
    }

    public void putTask(Task task){
        Task temp = new Task(task);
        this.repository.put(temp.getUuid(), temp);
        LOGGER.info("Current repo: " + this.repository.toString());
    }

    public Task getTask(UUID uuid){
        LOGGER.info("getTask method");
        if(Tools.isValidUUID(uuid.toString())){
            LOGGER.info(uuid.toString() + " is valid");
        }
        LOGGER.info("Current repo: " + this.repository.toString());
        Task task = this.repository.get(uuid);
        LOGGER.info(task.getJavascript());
        LOGGER.info(this.repository.get(uuid).toString());
        return this.repository.get(uuid);
    }

    public void setKilled(UUID uuid){
        this.repository.get(uuid).setStatus(Task.Status.KILLED);
    }

    public void setCompleted(UUID uuid){
        this.repository.get(uuid).setStatus(Task.Status.COMPLETED);
    }

    public void setTerminated(UUID uuid){
        this.repository.get(uuid).setStatus(Task.Status.TERMINATED);
    }

    public void setError(UUID uuid){
        this.repository.get(uuid).setStatus(Task.Status.ERROR);
    }

    public void setRunning(UUID uuid){
        this.repository.get(uuid).setStatus(Task.Status.RUNNING);
    }

    public Map<UUID, Task> getRepository() {
        LOGGER.info("Current repo: " + this.repository.toString());
        return repository;
    }
}
