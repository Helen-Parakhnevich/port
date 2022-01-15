package com.epam.port.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Dock {
    //private final Semaphore dockSemaphore = new Semaphore(1);
    private final Lock dockLock = new ReentrantLock();

    private final Logger LOGGER = LogManager.getLogger(Dock.class);

    public synchronized void takeDock(Ship ship) {
        try {
            //dockSemaphore.acquire();
            dockLock.lock();
            if (ship.isLoaded()) {
                shipUpload(ship);
            }
            else {
                shipDownload(ship);
            }
        } catch (InterruptedException e) {

        } finally {
            //dockSemaphore.release();
            dockLock.unlock();
        }
    }

    public synchronized void shipUpload(Ship ship) throws InterruptedException {
        LOGGER.info("Ship " + ship.getId() + " uploading in the process " + ship.getCargo() + " containers");
        TimeUnit.SECONDS.sleep((long)0.1*ship.getCargo());
    }

    public synchronized void shipDownload(Ship ship) throws InterruptedException {
        LOGGER.info("Ship " + ship.getId() + " downloading in the process "+ ship.getCargo() + " containers");
        TimeUnit.SECONDS.sleep((long)0.2*ship.getCargo());
    }

}
