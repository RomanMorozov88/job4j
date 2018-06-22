package ru.job4j.converter;

/**
 * Корвертор валюты.
 */
public class Converter {

    /**
     * Конвертируем рубли в евро.
     * @param value Рубли.
     * @return Евро.
     * 1 евро = 70 рублей.
     */
    public int rubleToEuro(int value) {
        return value / 70;
    }

    /**
     * Конвертируем рубли в доллары.
     * @param value Рубли.
     * @return Доллары
     * 1 доллар = 60 рублей.
     */
    public int rubleToDollar(int value) {
        return value / 60;
    }

    /**
     * Конвертируем рубли в доллары.
     * @param value Доллары.
     * @return Рубли.
     * 1 доллар = 60 рублей.
     */
    public int dollarToRuble(int value) {
        return value * 60;
    }

    /**
     * Конвертируем рубли в евро.
     * @param value Евро.
     * @return Рубли.
     * 1 евро = 70 рублей.
     */
    public int euroToRuble(int value) {
        return value * 70;
    }
}