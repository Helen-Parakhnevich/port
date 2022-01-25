package com.epam.port.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Port {
    private static Port instance;
    private final List<Dock> docks;
    private static final int NUMBER_DOCK = 3;
    private static final int CAPACITY = 500;
    private final int portCapacity;
    private Lock lockDock = new ReentrantLock();
    private static Lock lockPort = new ReentrantLock();
    private Semaphore semaphore;

    private final Logger LOGGER = LogManager.getLogger(Port.class);

    private Port(int numberDock, int capacity) {
        docks = new ArrayList<>(numberDock);
        this.portCapacity = capacity;
        semaphore = new Semaphore(numberDock);
        for (int i = 0; i < numberDock; i++) {
            Dock dock = new Dock(i);
            docks.add(dock);
        }
    }

    public void releaseDock(Dock dock) {
        dock.setBusy(false);
        semaphore.release();
    }

    public Dock takeDock() {
        lockDock.lock();
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Dock localDock = null;
        for (Dock dock : docks) {
            if (!dock.isBusy()) {
                dock.setBusy(true);
                localDock = dock;
                break;
            }
        }
        lockDock.unlock();
        return localDock;
    }

    public static Port getInstance() {

        if (instance == null) {
            synchronized (Port.class) {
                if (instance == null) {
                    instance = new Port(NUMBER_DOCK, CAPACITY);
                }
            }
        }
        return instance;
    }
}
