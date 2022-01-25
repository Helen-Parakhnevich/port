package com.epam.port.entity;

public class Dock {

    private final int id;
    private boolean busy = false;

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
