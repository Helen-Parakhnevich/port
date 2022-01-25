package com.epam.port.entity;

import com.sun.xml.internal.bind.v2.model.core.ID;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class Ship implements Runnable {
    private int id;
    private int cargo;
    private boolean loaded;

    private final Logger LOGGER = LogManager.getLogger(Dock.class);

    public Ship() {
    }

    public int getCargo() {
        return cargo;
    }

    public boolean isLoaded() {
        return loaded;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCargo(int cargo) {
        this.cargo = cargo;
    }

    public void setLoaded(boolean loaded) {
        this.loaded = loaded;
    }

    @Override
    public void run() {

        Port port = Port.getInstance();

        Dock currentDock = port.takeDock();
        LOGGER.info("Ship " + id + " arrived at the dock №" + (currentDock.getId() + 1));
        if (loaded) {
            download();
        } else {
            upload();
        }
        port.releaseDock(currentDock);
        LOGGER.info("Ship " + id + " left at the dock №" + (currentDock.getId() + 1));
    }

    public void upload() {
        LOGGER.info("Ship " + id + " uploading in the process " + cargo + " containers");
        try {
            TimeUnit.SECONDS.sleep((long) 0.8 * cargo);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void download() {
        LOGGER.info("Ship " + id + " downloading in the process " + cargo + " containers");
        try {
            TimeUnit.SECONDS.sleep((long) 0.5 * cargo);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Ship)) {
            return false;
        }
        Ship ship = (Ship) o;
        return getId() == ship.getId() && getCargo() == ship.getCargo() && isLoaded() == ship.isLoaded();
    }

    @Override
    public int hashCode() {
        int result = 31*id;
        result = 31*result + cargo;
        return result;
    }

    @Override
    public String toString() {
        return "Ship{" +
                "id=" + id +
                ", cargo=" + cargo +
                ", loaded=" + loaded +
                '}';
    }
}
