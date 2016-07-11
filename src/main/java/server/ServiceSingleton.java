package server;

import engine.TaskTransferData;

import java.util.UUID;
import java.util.concurrent.*;

/**
 * Created by Vladyslav Usenko on 18.06.2016.
 */
public enum ServiceSingleton {
    INSTANCE;

    private final Service service;

    ServiceSingleton() {
        this.service = new Service(new Repository(), new LinkedBlockingQueue<Task>(), new ConcurrentHashMap<UUID, Future<TaskTransferData>>());
    }

    public Service getService(){
        return this.service;
    }

}
