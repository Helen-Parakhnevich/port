package com.epam.port.entity;

import com.epam.port.service.CustomInterruptedException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Port {
    private static Port instance;
    private static final int NUMBER_DOCK = 3;
    private static final int CAPACITY = 500;
    private final List<Dock> docks;
    private final Lock dockLock = new ReentrantLock();
    private final Lock storageLock = new ReentrantLock();
    private final Semaphore semaphore;
    private final int portCapacity;
    private int storageState;

    private final Logger LOGGER = LogManager.getLogger(Port.class);

    private Port(int numberDock, int capacity) {
        docks = new ArrayList<>(numberDock);
        semaphore = new Semaphore(numberDock);
        this.portCapacity = capacity;
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
        dockLock.lock();
        Dock currentDock = null;
        try {
            semaphore.acquire();
            for (Dock dock : docks) {
                if (!dock.isBusy()) {
                    dock.setBusy(true);
                    currentDock = dock;
                    break;
                }
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        dockLock.unlock();
        return currentDock;
    }

    public int shipLoadUnload(int idShip, int numberContainers, boolean addCargo) {
        storageLock.lock();
        int possibleNumberContainers = Math.min(addCargo ? portCapacity - storageState : storageState, numberContainers);
        if (possibleNumberContainers > 0) {
            try {
                TimeUnit.SECONDS.sleep((long) 0.8 * possibleNumberContainers);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                new CustomInterruptedException(e.getMessage());
            }
            storageState = storageState + (addCargo ? possibleNumberContainers : -possibleNumberContainers);
            LOGGER.info("Ship " + idShip + (addCargo ? " uploaded " : " loaded ") + possibleNumberContainers + " out of possible " + numberContainers
                    + ". Current occupancy of the warehouse is " + storageState);
        } else {
            LOGGER.info("Ship " + idShip + " cannot be served. "
                    + (addCargo ? "No storage spade for unloading. STORAGE IS FULL!" : "The storage has no containers to load."));
        }
        storageLock.unlock();
        return possibleNumberContainers;
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
