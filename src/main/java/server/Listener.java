package server;

import engine.TaskTransferData;

/**
 * Created by Vladyslav Usenko on 11.07.2016.
 */
public interface Listener {
    void onListen(TaskTransferData taskTransferData);
}
