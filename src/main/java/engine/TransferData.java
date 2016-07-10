package engine;

import java.util.UUID;

/**
 * Created by Vladyslav Usenko on 29.06.2016.
 */
public class TransferData {
    private String consoleOutput;
    private UUID uuid;
    private Exception exception;

    public TransferData() {

    }

    public TransferData(String consoleOutput, UUID uuid, Exception exception) {
        this.consoleOutput = consoleOutput;
        this.uuid = uuid;
        this.exception = exception;
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
}
