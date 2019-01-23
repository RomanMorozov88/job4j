package ru.job4j.list.simplearraylist;

/**
 * Класс SimpleArrayList.
 */
public class SimpleArrayList<E> {

    private int size;
    private Node<E> first;

    /**
     * Метод вставляет в начало списка данные.
     */
    public void add(E date) {
        Node<E> newLink = new Node<>(date);
        newLink.next = this.first;
        this.first = newLink;
        this.size++;
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
            //Сбрасываем первый элемент.
            this.first = null;
            return null;
            //Если следующего элемента нет- возвращаем значегие первого элемента и обнуляем его.
        } else if (this.first.next == null) {
            result = this.first.date;
            this.first = null;
            this.size--;
            return result;
        }
        //Присваиваем первому элементу значение следующего за ним элемента
        //тем самым избавившись от старого значения.
        this.first = this.first.next;
        //Уменьшаем размер.
        this.size--;
        //Возвращаем значение текущего(обновлённого) первого элемента.
        return this.first.date;
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
}