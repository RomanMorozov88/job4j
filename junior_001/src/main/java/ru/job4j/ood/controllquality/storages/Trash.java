package ru.job4j.ood.controllquality.storages;

import ru.job4j.ood.controllquality.Food;

public class Trash extends Storage {

    @Override
    public boolean addIfConditionPassed(double percentLeft, Food food) {
        boolean result = percentLeft > MAXLIMIT;
        if (result) {
            this.racks.add(food);
        }
        return result;
    }
}