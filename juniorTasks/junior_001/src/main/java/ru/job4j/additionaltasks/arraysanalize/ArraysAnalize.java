package ru.job4j.additionaltasks.arraysanalize;

/**
 * Данный класс содержит метод для сравнения двух числовых массивов.
 */
public class ArraysAnalize<T extends Number> {

    public ArraysAnalize() {
    }

    public boolean arraysComparison(T[] left, T[] right) {
        boolean result = true;

        if (left.length != right.length) {
            result = false;
        } else {
            for (int i = 0; i < left.length; i++) {
                if (!left[i].equals(right[i])) {
                    result = false;
                    break;
                }
            }
        }
        return result;
    }
}