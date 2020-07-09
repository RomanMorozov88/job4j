package ru.job4j.ood.controllquality.storages;

import ru.job4j.ood.controllquality.Food;

import java.util.HashMap;
import java.util.Map;

public class Shop extends Storage {

    private Map<String, Integer> discountMap = new HashMap<>();

    @Override
    public boolean addIfConditionPassed(double percentLeft, Food food) {
        boolean result = false;
        if (percentLeft >= LOWERLIMIT && percentLeft <= UPPERLIMIT) {
            this.racks.add(food);
            result = true;
        } else if (percentLeft > UPPERLIMIT && percentLeft <= MAXLIMIT) {
            food.setDisscount(this.discountMap.getOrDefault(food.getName(), 5));
            this.racks.add(food);
            result = true;
        }
        return result;
    }
}