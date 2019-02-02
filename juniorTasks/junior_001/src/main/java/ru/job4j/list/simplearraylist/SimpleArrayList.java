package ru.job4j.list.simplearraylist;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Класс SimpleArrayList.
 */
public class SimpleArrayList<E> implements Iterable<E> {

    private int size;

    private Node<E> first;

    //Счётчик изменений.
    private int modCount = 0;

    /**
     * Метод вставляет в начало списка данные.
     */
    public void add(E date) {
        Node<E> newLink = new Node<>(date);
        newLink.next = this.first;
        this.first = newLink;
        this.size++;
        this.modCount++;
    }

    /**
     * Реализовать метод удаления первого элемент в списке.
     */
    public E delete() {
        //Буфер.
        E result;
        //Если список пуст или после первого элемента ничего нет-
        //возвращаем null.
        if (this.first == null) {
            return null;
        }
        result = this.first.date;
        this.first = this.first.next;
        this.size--;
        this.modCount++;
        return result;
    }

    /**
     * Метод получения элемента по индексу.
     */
    public E get(int index) {
        Node<E> result = this.first;
        for (int i = 0; i < index; i++) {
            result = result.next;
        }
        return result.date;
    }

    /**
     * Метод получения размера коллекции.
     */
    public int getSize() {
        return this.size;
    }

    /**
     * Класс предназначен для хранения данных.
     */
    private static class Node<E> {

        E date;
        Node<E> next;

        Node(E date) {
            this.date = date;
        }
    }

    /**
     * Итератор.
     *
     * @return
     */
    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {

            //Счётчик для итератора.
            int indexIterator = 0;

            //Принимаем текущее на момент создания итератора значение
            //modCount.
            int expectedModCount = modCount;

            //Буфер
            Node<E> result = first;

            @Override
            public boolean hasNext() {
                return indexIterator < size;
            }

            @Override
            public E next() {
                this.checkForComodification();
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                //Присваиваем значение текущего элемента.
                E resultE = result.date;
                //Обновляем буфер для следующего обращения.
                result = result.next;
                //Меняем счётчик.
                indexIterator++;
                return resultE;
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