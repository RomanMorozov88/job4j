package ru.job4j.bankaccounts;

import java.util.Random;

/**
 * Класс Account, содержащий информацию о счёте.
 */
public class Account {

    private double value;
    private final int requisites;

    /**
     * Конструктор аккаунта.
     *
     * @param value - сумма, помещающаяся на новый счёт.
     */
    public Account(double value) {
        this.requisites = this.requisitesGen();
        this.value = value;
    }

    /**
     * Генерация реквизитов.
     *
     * @return возвращает реквизиты в виде целого числа.
     */
    private int requisitesGen() {
        Random rnd = new Random();
        return rnd.nextInt(1000) + 1;
    }

    public int getRequisites() {
        return this.requisites;
    }

    public double getValue() {
        return this.value;
    }

    /**
     * Работа с балансом счёта.
     *
     * @param value
     */
    public void editValue(double value) {
        this.value = this.value + value;
    }
}