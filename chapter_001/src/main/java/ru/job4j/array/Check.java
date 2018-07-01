package ru.job4j.array;

public class Check {
    public boolean mono(boolean[] data) {
        boolean result = true;

        for (int i = 1; i < data.length; i++){
            if (data[i] != data[i - 1]) {
                result = false;
            }
        }
        return result;
    }
}