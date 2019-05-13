package ru.job4j.ood.controllquality.storages;

import ru.job4j.ood.controllquality.Food;

/**
 * Декоратор, добавляющий проверку на заполненость хранилища.
 */
public class FillDecorator extends Storage {

    private Storage storage;
    private int storageSize;

    public FillDecorator(Storage storage, int size) {
        this.storage = storage;
        this.storageSize = size;
    }

    @Override
    public boolean addIfConditionPassed(double percentLeft, Food food) {
        boolean result = this.storage.getRacks().size() < this.storageSize;
        if (result) {
            result = this.storage.addIfConditionPassed(percentLeft, food);
        }
        return result;
    }
}
