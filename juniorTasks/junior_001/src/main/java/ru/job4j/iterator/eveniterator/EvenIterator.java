package ru.job4j.iterator.eveniterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Итератор возвращающий только четные цифры.
 *
 */
public class EvenIterator implements Iterator<Integer> {

    public int index = 0;
    private final int[] values;

    public EvenIterator(int[] values) {
        this.values = values;
    }

    @Override
    public boolean hasNext() {
        while (index < this.values.length) {
            if (values[index] % 2 == 0) {
                return true;
            }
            index++;
        }
        return false;
    }

    @Override
    public Integer next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return values[index++];
    }
}