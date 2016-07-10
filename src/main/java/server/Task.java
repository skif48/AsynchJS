package server;

import java.util.UUID;
import java.util.logging.Logger;

/**
 * Created by Vladyslav Usenko on 18.06.2016.
 */
public class Task {
    private static final Logger LOGGER = Logger.getLogger(Task.class.getName());

    private String javascript;
    private UUID uuid;
    private long instanceTime = System.currentTimeMillis();
    private Status status;
    private int timeout;

    private String consoleOutput;
    private Exception exception;

    public enum Status{
        WAITING, RUNNING, COMPLETED, TERMINATED, ERROR, KILLED
    }

    public Task(String javascript, UUID uuid, int timeout){
        this.javascript = javascript;
        this.uuid = uuid;
        this.status = Status.WAITING;
        this.timeout = timeout;
        this.consoleOutput = "";
        this.exception = null;
    }

    public Task(Task task) {
        LOGGER.info("Task constructor");
        LOGGER.info(task.toString());
        this.javascript = task.getJavascript();
        this.uuid = task.getUuid();
        this.instanceTime = task.instanceTime;
        this.status = task.getStatus();
        this.timeout = task.getTimeout();
        this.exception = task.getException();
        this.consoleOutput = task.getConsoleOutput();
    }

    public String getConsoleOutput() {
        return consoleOutput;
    }

    public void setConsoleOutput(String consoleOutput) {
        this.consoleOutput = consoleOutput;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getJavascript() {
        return javascript;
    }

    public void setJavascript(String javascript) {
        this.javascript = javascript;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Task task = (Task) o;

        if (instanceTime != task.instanceTime) return false;
        if (javascript != null ? !javascript.equals(task.javascript) : task.javascript != null) return false;
        return uuid != null ? uuid.equals(task.uuid) : task.uuid == null;

    }

    @Override
    public int hashCode() {
        int result = javascript != null ? javascript.hashCode() : 0;
        result = 31 * result + (uuid != null ? uuid.hashCode() : 0);
        result = 31 * result + (int) (instanceTime ^ (instanceTime >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "Task{" +
                "javascript='" + javascript + '\'' +
                ", uuid=" + uuid +
                ", instanceTime=" + instanceTime +
                ", status=" + status +
                ", timeout=" + timeout +
                ", consoleOutput='" + consoleOutput + '\'' +
                ", exception=" + exception +
                '}';
    }
}
