package ru.job4j.ood.controllquality.storages;

import ru.job4j.ood.controllquality.Food;

import java.util.ArrayList;
import java.util.List;

abstract public class Storage {

    protected static final double UPPERLIMIT = 75;
    protected static final double LOWERLIMIT = 25;
    protected static final double MAXLIMIT = 100;
    protected static final double MINLIMIT = 0;

    protected List<Food> racks = new ArrayList<>();

    public List<Food> getRacks() {
        return this.racks;
    }

    public void clearRacks() {
        this.racks.clear();
    }

    abstract public boolean addIfConditionPassed(double percentLeft, Food food);
}