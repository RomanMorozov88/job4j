package ru.job4j.array;

import java.util.Arrays;

public class ArrayDuplicate {

    public String[] remove(String[] array) {
        String tmp;
        int z = array.length;
        for (int i = 0; i < z - 1; i++) {
            for (int j = i + 1; j < z; j++) {
                if (array[i].equals(array[j])) {
                    tmp = array[j];
                    array[j] = array[z - 1];
                    array[z - 1] = tmp;
                    z--;
                    j--;
                }
            }
        }
        return Arrays.copyOf(array, z);
    }
}