package ru.job4j.list;

import java.util.List;

public class ConvertList2Array {
    public int[][] toArray(List<Integer> list, int rows) {
        int cells;
        int listIndex = 0;
        /**
         * Определяем, сколько элементов будет содержаться в одной строке
         * Если количество строк больше длины list- то в каждой строке будет по одному элементу.
         */
        if (rows > list.size()) {
            cells = 1;
        } else {
            cells = (list.size() / rows) + (list.size() % rows);
        }
        int[][] array = new int[rows][cells];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cells; j++) {
                if (listIndex > list.size() - 1) {
                    break;
                } else {
                    array[i][j] = list.get(listIndex);
                }
                listIndex++;
            }
        }
        return array;
    }
}