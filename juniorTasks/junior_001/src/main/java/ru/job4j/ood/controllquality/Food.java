package ru.job4j.ood.controllquality;

import java.time.LocalDate;

abstract public class Food {

    private String name;
    private double price;
    private int disscount = 0;
    private LocalDate createDate;
    private LocalDate expaireDate;
    protected boolean canReproduct = false;
    protected boolean processed = false;

    protected Food(String name, double price, LocalDate createDate, LocalDate expaireDate) {
        this.name = name;
        this.price = price;
        this.createDate = createDate;
        this.expaireDate = expaireDate;
    }

    public String getName() {
        return name;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public LocalDate getExpaireDate() {
        return expaireDate;
    }

    public void setDisscount(int discountValue) {
        this.disscount = discountValue;
    }

    public double getPrice() {
        return this.price;
    }

    public int getDiscount() {
        return this.disscount;
    }

    public boolean getCanReproduct() {
        return this.canReproduct;
    }

    public boolean getProcessed() {
        return this.processed;
    }

    public double getFinallyPrice() {
        double percent = this.price / 100;
        return (100 - this.disscount) * percent;
    }
}