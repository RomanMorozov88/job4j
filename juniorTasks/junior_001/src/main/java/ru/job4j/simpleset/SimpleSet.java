package ru.job4j.simpleset;

import ru.job4j.generic.simplearray.SimpleArray;

import java.util.Iterator;

/**
 * Реализовать коллекцию SimpleSet.
 * Коллекция не должна хранить дубликаты.
 * Set - внутри для хранения данных использует обычные массивы.
 *
 * @param <T>
 */
public class SimpleSet<T> implements Iterable {

    //Внутренний лист для хранения.
    private SimpleArray<T> innerList;

    /**
     *Конструктор.
     */
    public SimpleSet() {
        this.innerList = new SimpleArray<>();
    }

    /**
     * Метод добавления элемента в список.
     * @param t
     */
    public void add(T t) {
        //Переменная с помощью которой проверяем- имеется ли уже в списке
        //объект равный t.
        boolean check = true;
        for (T x : innerList) {
            if (x.equals(t)) {
                check = false;
            }
        }
        if (check) {
            this.innerList.add(t);
        }
    }

    /**
     * Итератор.
     * @return - возвращает итераор внутреннего листа.
     */
    @Override
    public Iterator iterator() {
        return this.innerList.iterator();
    }
}