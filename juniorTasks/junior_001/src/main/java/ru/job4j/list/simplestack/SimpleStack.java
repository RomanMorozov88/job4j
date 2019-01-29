package ru.job4j.list.simplestack;

import ru.job4j.list.simplearraylist.SimpleArrayList;

public class SimpleStack<T> extends SimpleArrayList<T> {

    public T poll() {
        return this.delete();
    }

    public void push(T value) {
        this.add(value);
    }
}