package ru.job4j.array;

public class Turn {
    public int[] turn(int[] array) {

        int tmp1 = 0;
        int j = array.length - 1;

        for (int i = 0; i < j; i++) {
            tmp1 = array[i];
            array[i] = array[j];
            array[j] = tmp1;
            j--;
        }
        return array;
    }
}