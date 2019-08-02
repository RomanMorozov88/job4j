package ru.job4j.blockingqueue;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class SimpleBlockingQueueTest {

    /**
     * Создаём один поток с Producer и два потока с Customer.
     * Так же в консоль выводятся сообщения из исполняемых методов.
     * Предел для очереди задаём равным двум для наглядности остановки\пробуждения потоков.
     *
     * @throws InterruptedException
     */
    @Test
    public void whenExecute2ThreadThen2() throws InterruptedException {
        SimpleBlockingQueue<Integer> testQueue = new SimpleBlockingQueue<>(2);

        Producer producer = new Producer(testQueue);
        Consumer consumer01 = new Consumer(testQueue);
        Consumer consumer02 = new Consumer(testQueue);
        Thread first = new Thread(producer);
        Thread second = new Thread(consumer01);
        Thread third = new Thread(consumer02);

        third.start();
        first.start();
        second.start();

        first.join();
        second.join();
        third.join();

        assertThat(consumer01.getValue().size(), is(3));
        assertThat(consumer02.getValue().size(), is(3));
    }

}