package ru.job4j.list;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConvertMatrix2List {
    public List<Integer> toList(int[][] array) {
        ArrayList<Integer> list = new ArrayList<>();
        Arrays
                .stream(array)
                .map(Arrays::stream)
                .forEach(x -> x.forEach(list::add));
        return list;
    }

    public List<Integer> convert(List<int[]> list) {
        List<Integer> result = new ArrayList<>();
        list
                .stream()
                .map(Arrays::stream)
                .forEach(x -> x.forEach(result::add));
        return result;
    }

    /**
     * Интереса ради написал коныертацию трёхмерного массива в список
     * (А вот четырёхмерного уже не получилось)
     * @param array
     * @return
     */
    public List<Integer> threeDToList(int[][][] array) {
        ArrayList<Integer> list = new ArrayList<>();
        Arrays
                .stream(array)
                .map(Arrays::stream)
                .map(x -> x.map(Arrays::stream))
                .forEach(x -> x.forEach(y -> y.forEach(list::add)));

        return list;
    }
}