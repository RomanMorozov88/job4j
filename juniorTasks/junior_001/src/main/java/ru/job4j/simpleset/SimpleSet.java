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
     * Конструктор.
     */
    public SimpleSet() {
        this.innerList = new SimpleArray<>();
    }

    /**
     * Метод добавления элемента в список.
     *
     * @param t
     */
    public void add(T t) {
        if (this.dublicateCheck(t)) {
            this.innerList.add(t);
        }
    }

    /**
     * Метод для проверки наличия дубликатов.
     *
     * @param t - кандидат на добавление в список.
     * @return true - если совпадений нет.
     */
    private boolean dublicateCheck(T t) {
        boolean check = true;
        for (T x : innerList) {
            if (x.equals(t)) {
                check = false;
            }
        }
        return check;
    }

    /**
     * Итератор.
     *
     * @return - возвращает итераор внутреннего листа.
     */
    @Override
    public Iterator iterator() {
        return this.innerList.iterator();
    }
}