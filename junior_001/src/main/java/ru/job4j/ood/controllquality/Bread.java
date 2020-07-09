package ru.job4j.ood.controllquality;

import java.time.LocalDate;

public class Bread extends Food {
    public Bread(String name, double price, LocalDate createDate, LocalDate expaireDate) {
        super(name, price, createDate, expaireDate);
    }
}