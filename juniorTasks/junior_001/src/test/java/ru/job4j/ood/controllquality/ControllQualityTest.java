package ru.job4j.ood.controllquality;

import org.junit.Test;
import ru.job4j.ood.controllquality.storages.Shop;
import ru.job4j.ood.controllquality.storages.Storage;
import ru.job4j.ood.controllquality.storages.Trash;
import ru.job4j.ood.controllquality.storages.Warehouse;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class ControllQualityTest {

    @Test
    public void whenAddWithControllQuality() {
        ControllQuality cqTest = new ControllQuality();
        cqTest.setCurrentTime(LocalDate.of(2019, 6, 1));
        cqTest.distributionByStorages(new Food("food01", 15,
                LocalDate.of(2019, 5, 1),
                LocalDate.of(2019, 8, 1)));
        cqTest.distributionByStorages(new Food("food02", 15,
                LocalDate.of(2019, 5, 1),
                LocalDate.of(2019, 6, 10)));
        cqTest.distributionByStorages(new Food("food03", 15,
                LocalDate.of(2019, 5, 1),
                LocalDate.of(2019, 11, 1)));
        cqTest.distributionByStorages(new Food("food04", 15,
                LocalDate.of(2019, 5, 1),
                LocalDate.of(2019, 5, 31)));
        List<Storage> resultList = cqTest.getStorages();
        int resultSize = resultList.get(0).getRacks().size();
        assertThat(resultSize, is(1));
        resultSize = resultList.get(1).getRacks().size();
        assertThat(resultSize, is(2));
        resultSize = resultList.get(2).getRacks().size();
        assertThat(resultSize, is(1));

    }

    @Test
    public void whenAddInShopFirst() {
        Storage testShop = new Shop();
        double testPercentLeft = 50;
        Food foodTest = new Food("food01", 15,
                LocalDate.of(2019, 5, 1),
                LocalDate.of(2019, 8, 1));
        boolean result = testShop.addIfConditionPassed(testPercentLeft, foodTest);
        assertThat(result, is(true));
        int foodTestDiscount = foodTest.getDiscount();
        assertThat(foodTestDiscount, is(0));
    }
    @Test
    public void whenAddInShopSecond() {
        Storage testShop = new Shop();
        double testPercentLeft = 80;
        Food foodTest = new Food("food01", 15,
                LocalDate.of(2019, 5, 1),
                LocalDate.of(2019, 8, 1));
        boolean result = testShop.addIfConditionPassed(testPercentLeft, foodTest);
        assertThat(result, is(true));
        int foodTestDiscount = foodTest.getDiscount();
        assertThat(foodTestDiscount, is(5));
    }
    @Test
    public void whenAddInWarehouse() {
        Storage testWarehouse = new Warehouse();
        double testPercentLeft = 10;
        Food foodTest = new Food("food01", 15,
                LocalDate.of(2019, 5, 1),
                LocalDate.of(2019, 8, 1));
        boolean result = testWarehouse.addIfConditionPassed(testPercentLeft, foodTest);
        assertThat(result, is(true));
    }
    @Test
    public void whenAddInTrash() {
        Storage testTrash = new Trash();
        double testPercentLeft = 110;
        Food foodTest = new Food("food01", 15,
                LocalDate.of(2019, 5, 1),
                LocalDate.of(2019, 8, 1));
        boolean result = testTrash.addIfConditionPassed(testPercentLeft, foodTest);
        assertThat(result, is(true));
    }
}