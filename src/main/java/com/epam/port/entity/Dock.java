package com.epam.port.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;

public class Dock {

    private final int id;
    private boolean busy = false;

    private final Logger LOGGER = LogManager.getLogger(Dock.class);

    public Dock(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setBusy(boolean busy) {
        this.busy = busy;
    }

    public boolean isBusy() {
        return busy;
    }

}
