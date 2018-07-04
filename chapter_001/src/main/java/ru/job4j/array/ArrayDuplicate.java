package ru.job4j.array;

import java.util.Arrays;

public class ArrayDuplicate {

    public String[] remove(String[] array) {

        String tmp;

        int z = array.length;

        for (int i = 0; i < array.length - 1; i++) {
            for (int j = i + 1; j < array.length; j++) {
                if (array[i].equals(array[j])) {
                    tmp = array[j];
                    // Смещение элементов после дубликата влево.
                    for (int s = j; s < array.length - 1; s++) {
                        array[s] = array[s + 1];
                    }
                    // Присвоение последнему элементу значения дубликата.
                    array[array.length - 1] = tmp;
                }
            }
        }

        // Счётчик z для того, что бы знать размер нового массива.
      for (int i = 0; i < array.length; i++) {
            for (int j = i + 1; j < array.length; j++) {
                if (array[i].equals(array[j])) {
                    z--;
                }
            }
        }
        return Arrays.copyOf(array, z);
    }
}