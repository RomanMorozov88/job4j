package ru.job4j.threadsafelist;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import ru.job4j.generic.simplearray.SimpleArray;

import java.util.Iterator;

/**
 * Обёртка над SimpleArray.
 * @param <T>
 */
@ThreadSafe
public class ThreadSafeList<T> implements Iterable<T> {

    @GuardedBy("this")
    private final SimpleArray<T> workList;

    public ThreadSafeList() {
        this.workList = new SimpleArray<>();
    }

    public ThreadSafeList(int size) {
        this.workList = new SimpleArray<>(size);
    }

    public ThreadSafeList(SimpleArray<T> workList) {
        this.workList = workList;
    }

    public synchronized void add(T model) {
        this.workList.add(model);
    }

    public synchronized void set(int index, T model) {
        this.workList.set(index, model);
    }

    public synchronized void remove(int index) {
        this.workList.remove(index);
    }

    public synchronized T get(int index) {
        return this.workList.get(index);
    }

    private SimpleArray<T> copy(SimpleArray<T> array) {
        SimpleArray<T> result = new SimpleArray<>();
        for (T t : array) {
            result.add(t);
        }
        return result;
    }

    @Override
    public synchronized Iterator<T> iterator() {
        return copy(this.workList).iterator();
    }
}
