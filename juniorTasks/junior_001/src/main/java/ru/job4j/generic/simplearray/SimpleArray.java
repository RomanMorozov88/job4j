package ru.job4j.generic.simplearray;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * В этом задании необходимо сделать универсальную обертку над массивом.
 *
 * @param <T>
 */
public class SimpleArray<T> implements Iterable<T> {

    //Внутреннй массив.
    private Object[] workArray;
    //Счётчик.
    private int index = 0;

    /**
     * Конструктор.
     *
     * @param size - длина внутреннего массива workArray.
     */
    public SimpleArray(int size) {
        this.workArray = new Object[size];
    }

    /**
     * Добавлени элемента.
     *
     * @param model
     */
    public void add(T model) {
        this.workArray[index++] = model;
    }

    /**
     * Замена элемента.
     *
     * @param index - положение заменяемого элемента.
     * @param model - новый элемент.
     */
    public void set(int index, T model) {
        //Проверяем.
        this.check(index);

        //Заменяем.
        this.workArray[index] = model;
    }

    /**
     * Удаление элемента.
     *
     * @param index - положение заменяемого элемента.
     */
    public void remove(int index) {
        //Проверяем.
        this.check(index);

        System.arraycopy(this.workArray, index + 1,
                this.workArray, index, this.workArray.length - index - 1);

        //Откидываем счётчик на шаг назад.
        this.index--;

        //Сбрасываем последний элемент.
        this.workArray[this.workArray.length - 1] = null;
    }

    /**
     * Получаем нужный элемент.
     *
     * @param index - положение заменяемого элемента.
     * @return
     */
    public T get(int index) {
        //Проверяем.
        this.check(index);

        return (T) this.workArray[index];
    }

    /**
     * Проверяем корректность вводимого идекса и не равен ли объект на нужной позиции null.
     *
     * @param input - вводимый индекс.
     */
    private void check(int input) {
        if (input > index) {
            throw new IndexOutException("Wrong index value.");
        }
    }

    /**
     * Итератор.
     *
     * @return
     */
    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {

            int indexIterator = 0;

            @Override
            public boolean hasNext() {
                return indexIterator < index;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return (T) workArray[indexIterator++];
            }
        };
    }
}