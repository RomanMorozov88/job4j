package ru.job4j.zadachi;

public class Otrezki {

    public boolean srv(int[] arr1, int[] arr2) {
        boolean result = false;
        if (arr1[0] < arr1[1] && arr2[0] < arr2[1]) {
            if (arr1[0] < arr2[1] && arr2[0] < arr1[1]) {
                result = true;
            }
        }
        return result;
    }
}