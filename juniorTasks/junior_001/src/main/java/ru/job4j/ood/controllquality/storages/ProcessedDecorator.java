package ru.job4j.ood.controllquality.storages;

import ru.job4j.ood.controllquality.Food;

/**
 * C помощью это декоратора добавляем проверку поля processed.
 */
public class ProcessedDecorator extends Storage {

    private Storage storage;

    public ProcessedDecorator(Storage storage) {
        this.storage = storage;
    }

    @Override
    public boolean addIfConditionPassed(double percentLeft, Food food) {
        boolean result = false;
        if (!food.getProcessed()) {
            result = this.storage.addIfConditionPassed(percentLeft, food);
        }
        return result;
    }
}