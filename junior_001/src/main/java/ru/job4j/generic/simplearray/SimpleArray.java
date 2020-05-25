package ru.job4j.generic.simplearray;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * В этом задании необходимо сделать универсальную обертку над массивом.
 * Итератор должен реализовывать fail-fast поведение
 *
 * @param <T>
 */
public class SimpleArray<T> implements Iterable<T> {

    //Внутреннй массив.
    private Object[] workArray;

    //Счётчик.
    private int index = 0;

    //Счётчик изменений.
    private int modCount = 0;

    /**
     * Конструктор.
     *
     * @param size - длина внутреннего массива workArray.
     */
    public SimpleArray(int size) {
        this.workArray = new Object[size];
    }

    /**
     * Конструктор без входящего параметра.
     * Создаём внутренний массив длиной 10.
     */
    public SimpleArray() {
        this.workArray = new Object[10];
    }

    /**
     * Добавлени элемента.
     *
     * @param model
     */
    public void add(T model) {
        //Проверяем- нет ли переполнения.
        //Если есть- расширяем workArray.
        this.sizeMod();
        this.workArray[index++] = model;
        //Изменяем счётчик modCount, так как меняется количество элементов во внутреннем массиве workArray.
        modCount++;
    }

    /**
     * Замена элемента.
     * В этом методе нет изменения modCount т.к. количество элементов не изменяется.
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

        //Изменяем счётчик modCount, так как меняется количество элементов во внутреннем массиве workArray.
        modCount++;
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
        if (input >= index) {
            throw new IndexOutException("Wrong index value.");
        }
    }

    /**
     * Метод для расширения внутреннего массива workArray
     * при его переполнении.
     * Длину массива увеличиваем в два раза.
     *
     * @return
     */
    private void sizeMod() {
        if (this.index == this.workArray.length) {
            //Буферный массив с новой длиной.
            Object[] workArrayBuffer = new Object[this.workArray.length * 2];
            //Копируем все элементы в буферный массив.
            System.arraycopy(this.workArray, 0, workArrayBuffer, 0, this.index);
            //Обновляем workArray.
            this.workArray = workArrayBuffer;
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

            //Счётчик для итератора.
            int indexIterator = 0;

            //Принимаем текущее на момент создания итератора значение
            //счётчика изменений modCount.
            int expectedModCount = modCount;

            @Override
            public boolean hasNext() {
                return indexIterator < index;
            }

            @Override
            public T next() {
                this.checkForComodification();
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return (T) workArray[indexIterator++];
            }

            /**
             * Метод, проверяющий, что во время итерации не произошло изменений хранилища.
             */
            final void checkForComodification() {
                if (modCount != expectedModCount) {
                    throw new ConcurrentModificationException();
                }
            }
        };
    }
}