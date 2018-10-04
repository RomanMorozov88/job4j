package ru.job4j.bankaccounts;

import java.util.Random;

public class Account {

    private double value;
    private final int requisites;

    public Account(double value) {
        this.requisites = this.requisitesGen();
        this.value = value;
    }
    private int requisitesGen() {
        Random rnd = new Random();
        return rnd.nextInt(1000) + 1;
    }
    public int getRequisites() {
        return this.requisites;
    }
    public double getValue() {
        return this.value;
    }
    public void editValue(double value) {
        this.value = this.value + value;
    }
}