package ru.job4j.ood.controllquality.storages;

import ru.job4j.ood.controllquality.Food;

/**
 * Холодильник для обработаных продуктов.
 */
public class Refrigerator extends Storage {

    @Override
    public boolean addIfConditionPassed(double percentLeft, Food food) {
        boolean result = (percentLeft < LOWERLIMIT && percentLeft > MINLIMIT) && food.getProcessed();
        if (result) {
            this.racks.add(food);
        }
        return result;
    }
}