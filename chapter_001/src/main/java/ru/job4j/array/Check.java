package ru.job4j.array;

public class Check {
    public boolean mono(boolean[] data) {
        boolean result = false;

        for (int i = 1; i < data.length; i++) {
            if (data[i] == data[i - 1]) {
                result = true;
            } else {
                result = false;
                return result;
            }
        }
        return result;
    }
}