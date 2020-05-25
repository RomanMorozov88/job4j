package ru.job4j.ood.controllquality.storages;

import ru.job4j.ood.controllquality.Food;

/**
 * Склад для продуктов, у которых вышел срок годности и
 * которые можно переработать.
 */
public class StorageForReproduct extends Storage {
    @Override
    public boolean addIfConditionPassed(double percentLeft, Food food) {
        boolean result = percentLeft > MAXLIMIT && food.getCanReproduct();
        if (result) {
            this.racks.add(food);
        }
        return result;
    }
}