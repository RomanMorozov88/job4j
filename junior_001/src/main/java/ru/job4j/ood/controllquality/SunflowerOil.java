package ru.job4j.ood.controllquality;

import java.time.LocalDate;

public class SunflowerOil extends Food {
    public SunflowerOil(String name, double price, LocalDate createDate,
                        LocalDate expaireDate, boolean canReproduct) {
        super(name, price, createDate, expaireDate);
        this.canReproduct = canReproduct;
    }
}