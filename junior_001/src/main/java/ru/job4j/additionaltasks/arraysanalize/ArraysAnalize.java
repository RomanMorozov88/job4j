package ru.job4j.additionaltasks.arraysanalize;

import java.util.*;

/**
 * Данный класс содержит метод для сравнения двух числовых массивов.
 */
public class ArraysAnalize<T extends Number> {

    public ArraysAnalize() {
    }

    public boolean arraysComparison(T[] left, T[] right) {
        boolean result = false;
        HashMap<T, Integer> map = new HashMap<>();
        T buffer;
        if (left.length == right.length) {
            for (int i = 0; i < left.length; i++) {
                buffer = left[i];
                map.computeIfPresent(buffer, (key, value) -> value + 1);
                map.putIfAbsent(buffer, 1);
            }
            for (int i = 0; i < right.length; i++) {
                buffer = right[i];
                map.computeIfPresent(buffer, (key, value) -> value - 1);
                map.putIfAbsent(buffer, 1);
            }
            result = true;
            for (Integer v : map.values()) {
                if (v != 0) {
                    result = false;
                    break;
                }
            }
        }
        return result;
    }
}