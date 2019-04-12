package ru.job4j.additionaltasks.arraysanalize;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Данный класс содержит метод для сравнения двух числовых массивов.
 */
public class ArraysAnalize<T extends Number> {

    public ArraysAnalize() {
    }

    public boolean arraysComparison(T[] left, T[] right) {
        boolean result = true;
        int checkSize = left.length;
        Set<T> buffer = new HashSet<>();

        if (left.length != right.length) {
            result = false;
        } else {
            buffer.addAll(Arrays.asList(left));
            buffer.addAll(Arrays.asList(right));
            if (buffer.size() != checkSize) {
                result = false;
            }
        }
        return result;
    }
}