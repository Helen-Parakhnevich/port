package com.epam.port.entity;

public class Ship implements Runnable{
    private int id;
    private int cargo;
    private boolean loaded;

    public Ship() {
    }

    @Override
    public void run() {
        Port port = Port.getInstance();
        port.takeDock(this);
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
    public String toString() {
        return "Ship{" +
                "id=" + id +
                ", cargo=" + cargo +
                ", loaded=" + loaded +
                '}';
    }
}
