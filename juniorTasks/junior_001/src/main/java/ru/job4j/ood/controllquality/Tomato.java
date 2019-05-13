package ru.job4j.ood.controllquality;

import java.time.LocalDate;

public class Tomato extends Food {
    public Tomato(String name, double price, LocalDate createDate,
                  LocalDate expaireDate, boolean processed) {
        super(name, price, createDate, expaireDate);
        this.processed = processed;
    }
}