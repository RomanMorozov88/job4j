package ru.job4j.ood.controllquality;

import ru.job4j.ood.controllquality.storages.Shop;
import ru.job4j.ood.controllquality.storages.Storage;
import ru.job4j.ood.controllquality.storages.Trash;
import ru.job4j.ood.controllquality.storages.Warehouse;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс должен перераспределять еду по хранилищам в зависимости от условиый.
 * 3.1. Если срок годности израсходован меньше чем на 25% направить в Warehouse.
 * 3.2 Если срок годности от 25% до 75% направить в Shop
 * 3.3. Если срок годности больше 75% то выставить скидку на продукт и отправить в Shop
 * 3.4. Если срок годности вышел. Отправить продукт в мусорку.
 */
public class ControllQuality {

    private List<Storage> storages = new ArrayList<>();
    private LocalDate currentTime;

    public ControllQuality() {
        this.storages.add(new Warehouse());
        this.storages.add(new Shop());
        this.storages.add(new Trash());
    }

    public void setNewStorage(Storage newStorage) {
        this.storages.add(newStorage);
    }

    public void setCurrentTime(LocalDate newCurrent) {
        this.currentTime = newCurrent;
    }

    public void currentTimeRefresh() {
        this.currentTime = LocalDate.now();
    }

    public List<Storage> getStorages() {
        return this.storages;
    }

    /**
     * В этом методе вычисляем, насколько израсходован срок годности в процентах.
     *
     * @param food - продукт, который смотрим.
     * @return
     */
    private double getPercents(Food food) {
        LocalDate createFromFood = food.getCreateDate();
        long general = ChronoUnit.DAYS.between(createFromFood, food.getExpaireDate());
        long leftover = ChronoUnit.DAYS.between(createFromFood, this.currentTime);
        double percent = (double) general / 100;
        double result = Math.round((double) leftover / percent);
        return result;
    }

    /**
     * Направляем продукт в нужное хранилище.
     *
     * @param food
     */
    public void distributionByStorages(Food food) {
        double percentLeft = this.getPercents(food);
        for (Storage s : this.storages) {
            if (s.addIfConditionPassed(percentLeft, food)) {
                break;
            }
        }
    }

    /**
     * Перераспределяем уже хранящиеся продукты.
     */
    public void resort() {
        List<Food> buffer = new ArrayList<>();
        for (Storage s : this.storages) {
            buffer.addAll(s.getRacks());
            s.clearRacks();
        }
        for (Food f : buffer) {
            this.distributionByStorages(f);
        }
    }
}