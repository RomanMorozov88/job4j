package ru.job4j.threadpool;

import java.util.LinkedList;
import java.util.List;

import ru.job4j.blockingqueue.SimpleBlockingQueue;

/**
 * Пул - это хранилища для ресурсов, которые можно переиспользовать.
 * Клиент берет ресурс из пула, выполняет свою работу и возвращается обратно в пул.
 * Общая схема реализации пула.
 * 1. Инициализация пула должна быть по количеству ядер в системе.
 * int size = Runtime.getRuntime().availableProcessors()
 * В каждую нить передается блокирующая очередь tasks.
 * Количество нитей всегда одинаковое и равно size.
 * В методе run мы должны получить задачу их очереди tasks.
 * tasks - это блокирующая очередь. Если в очереди нет элементов, то нить переводиться в состоянии waiting.
 * Когда приходит новая задача, всем нитям в состоянии waiting посылается сигнал проснуться и начать работу.
 * 2. Создать метод work(Runnable job). - этот метод должен добавлять задачи в блокирующую очередь tasks.
 */
public class ThreadPool {
    private final List<Thread> threads = new LinkedList<>();
    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>(10);
    private final int size = Runtime.getRuntime().availableProcessors();

    public void threadPoolInit() {
        for (int i = 0; i < size; i++) {
            Thread t = new ThreadTask(tasks);
            t.start();
            threads.add(t);
        }
    }

    public void work(Runnable job) throws InterruptedException {
        tasks.offer(job);
    }

    public void shutdown() {
        for (Thread t : threads) {
            t.interrupt();
        }
    }
}

/**
 * Нить, которая работает с задачами из SimpleBlockingQueue<Runnable> tasks.
 */
class ThreadTask extends Thread {

    private final SimpleBlockingQueue<Runnable> inputTasks;

    public ThreadTask(SimpleBlockingQueue<Runnable> inputTasks) {
        this.inputTasks = inputTasks;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                inputTasks.poll().run();
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }
    }
}