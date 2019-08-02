package ru.job4j.blockingqueue;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Блокирующая очередь.
 * При пустой очереди или при количестве элементов в очереди
 * более чем значение sizeLimit будет вызван метод wait()
 * для вызывающего потока.
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

    public synchronized void offer(T value) throws InterruptedException {
        while (this.queue.size() >= this.sizeLimit) {
                System.out.println("Awaiting for Customer");
                wait();
        }
        System.out.println("Place in queue");
        this.queue.offer(value);
        notify();
    }

    public synchronized T poll() throws InterruptedException {
        while (this.queue.size() < 1) {
                System.out.println("Awaiting for Producer");
                wait();
        }
        notify();
        System.out.println("Take from queue");
        return this.queue.poll();
    }
}

/**
 * В этом классе будем добавлять в очередь семь элементов.
 */
class Producer implements Runnable {

    private SimpleBlockingQueue<Integer> queue;

    public Producer(SimpleBlockingQueue<Integer> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        for (int i = 0; i < 7; i++) {
            try {
                this.queue.offer(i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

/**
 * В этом классе будем получать во внутренни список три элемента из очереди.
 */
class Consumer implements Runnable {

    private SimpleBlockingQueue<Integer> queue;
    private List<Integer> value = new ArrayList<>();

    public Consumer(SimpleBlockingQueue<Integer> queue) {
        this.queue = queue;
    }

    public List<Integer> getValue() {
        return this.value;
    }

    @Override
    public void run() {
        for (int i = 0; i < 3; i++) {
            try {
                this.value.add(this.queue.poll());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}