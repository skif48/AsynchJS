package engine;

import server.Task;

import java.util.UUID;

/**
 * Created by Vladyslav Usenko on 29.06.2016.
 */
public class TaskTransferData {
    private String consoleOutput;
    private UUID uuid;
    private Exception exception;
    private Task.Status status;

    public TaskTransferData() {

    }

    public TaskTransferData(String consoleOutput, UUID uuid, Exception exception, Task.Status status) {
        this.consoleOutput = consoleOutput;
        this.uuid = uuid;
        this.exception = exception;
        this.status = status;
    }

    public String getConsoleOutput() {
        return consoleOutput;
    }

    public void setConsoleOutput(String consoleOutput) {
        this.consoleOutput = consoleOutput;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public Task.Status getStatus() {
        return status;
    }

    public void setStatus(Task.Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "TaskTransferData{" +
                "consoleOutput='" + consoleOutput + '\'' +
                ", uuid=" + uuid +
                ", exception=" + exception +
                ", status=" + status +
                '}';
    }
}
