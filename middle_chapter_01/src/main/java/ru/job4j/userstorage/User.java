package ru.job4j.userstorage;

public class User {

    private final int id;
    private int amount = 0;

    public User(int id) {
        this.id = id;
    }

    public User(int id, int amount) {
        this.id = id;
        this.amount = amount;
    }

    public int geId() {
        return id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}