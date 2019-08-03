package ru.job4j.blockingqueue;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Блокирующая очередь.
 * При пустой очереди или при количестве элементов в очереди
 * более чем значение sizeLimit будет вызван метод wait()
 * для вызывающего потока.
 *
 * @param <T>
 */
@ThreadSafe
public class SimpleBlockingQueue<T> {

    @GuardedBy("this")
    private Queue<T> queue = new LinkedList<>();
    private final int sizeLimit;

    public SimpleBlockingQueue(int sizeLimit) {
        this.sizeLimit = sizeLimit;
    }

    public synchronized boolean isEmpty() {
        return this.queue.isEmpty();
    }

    public synchronized void offer(T value) throws InterruptedException {
        while (this.queue.size() >= this.sizeLimit) {
            wait();
        }
        this.queue.offer(value);
        notify();
    }

    public synchronized T poll() throws InterruptedException {
        while (this.queue.size() < 1) {
            wait();
        }
        notify();
        return this.queue.poll();
    }
}