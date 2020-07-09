package ru.job4j.ood.controllquality.storages;

import ru.job4j.ood.controllquality.Food;

/**
 * C помощью это декоратора добавляем проверку поля canReproduct.
 */
public class ReproductDecorator extends Storage {

    private Storage storage;

    public ReproductDecorator(Storage storage) {
        this.storage = storage;
    }

    @Override
    public boolean addIfConditionPassed(double percentLeft, Food food) {
        boolean result = false;
        if (!food.getCanReproduct()) {
            result = this.storage.addIfConditionPassed(percentLeft, food);
        }
        return result;
    }
}