package ru.job4j.nonblockingcache;

public class Base {

    private int id;
    private int version = 0;

    public Base(int id) {
        this.id = id;
    }

    public void setId(int newId) {
        this.id = newId;
    }

    public int getId() {
        return this.id;
    }

    public void changeVersion() {
        ++this.version;
    }

    public int getVersion() {
        return this.version;
    }
}