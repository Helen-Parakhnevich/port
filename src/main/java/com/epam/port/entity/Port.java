package com.epam.port.entity;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Semaphore;

public class Port {
    private static Port instance;
    private static final int NUMBER_DOCK = 3;
    private static final int CAPACITY_STORAGE = 500;
    private static final List<Dock> DOCKS = new ArrayList<Dock>(NUMBER_DOCK);
    private static final Semaphore docksSemaphore = new Semaphore(NUMBER_DOCK, true);
    private static final Map<Dock, Integer> dockUse = new HashMap<>();

    private final Logger LOGGER = LogManager.getLogger(Port.class);

    static {
        for (int i = 0; i < 3; i++) {
            Dock dock = new Dock();
            DOCKS.add(dock);
            dockUse.put(dock, 0);
        }
    }

    public synchronized void takeDock(Ship ship) {
        try {
            docksSemaphore.acquire();
            Optional<Dock> freeDock = getFreeDock();
            if (freeDock.isPresent()) {
                Dock currentDock = freeDock.get();
                setUseDock(currentDock, 1);
                LOGGER.info("Ship " + ship.getId() + " arrived at the dock №" + (DOCKS.indexOf(currentDock)+1));
                currentDock.takeDock(ship);
                LOGGER.info("Ship " + ship.getId() + " left at the dock №" + (DOCKS.indexOf(currentDock)+1));
                setUseDock(currentDock, 0);
            }
        } catch (InterruptedException e) {

        } finally {
            docksSemaphore.release();
        }
    }

    private static Optional<Dock> getFreeDock() {
        Optional<Dock> result = dockUse.entrySet()
                .stream()
                .filter(entry -> entry.getValue() == 0)
                .map(Map.Entry::getKey)
                .findFirst();

        return result;
    }

    private static void setUseDock(Dock dock, Integer use) {
        Optional<Dock> result = dockUse.entrySet()
                .stream()
                .filter(entry -> dock.equals(entry.getKey()))
                .map(Map.Entry::getKey)
                .findFirst();

        if (result.isPresent()) {
             dockUse.computeIfPresent(result.get(), (k,v) -> v=use);
        }
    }

    public static Port getInstance() {
        Port localInstance = instance;
        if (localInstance == null) {
            localInstance = instance;
            if (localInstance == null) {
                instance = localInstance = new Port();
            }
        }
        return localInstance;
    }
}
