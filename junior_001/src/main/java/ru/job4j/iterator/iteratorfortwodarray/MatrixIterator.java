package ru.job4j.iterator.iteratorfortwodarray;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Итератор для двухмерного массива.
 */
public class MatrixIterator implements Iterator<Integer> {

    private int indeX = 0;
    private int indeY = 0;
    private final int[][] values;

    public MatrixIterator(int[][] values) {
        this.values = values;
    }

    @Override
    public boolean hasNext() {
        //Вполне хватит проверки счётчика indeX.
        return values.length > indeX;
    }

    @Override
    public Integer next() {
        //Вспомогательная переменная.
        int support;
        if (!hasNext()) {
            throw new NoSuchElementException();
        } else if (values[indeX].length - 1 == indeY) {
            //Так как при этом условии необходимо обнулить счётчик indeY
            //то используем переменную support, которой присваиваем текущее значение indeY
            // для корректной работы.
            support = indeY;
            indeY = 0;
            return values[indeX++][support];
        } else {
            return values[indeX][indeY++];
        }
    }
}