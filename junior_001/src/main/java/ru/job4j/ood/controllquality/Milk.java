package ru.job4j.ood.controllquality;

import java.time.LocalDate;

public class Milk extends Food {
    public Milk(String name, double price, LocalDate createDate, LocalDate expaireDate) {
        super(name, price, createDate, expaireDate);
    }
}