package ru.job4j.threadsafelist;

import org.junit.Test;

import java.util.Iterator;

public class ThreadSafeListTest {

    /**
     * Этим потоком будем менять содержимое списка.
     */
    private class ThreadEdit extends Thread {
        private final ThreadSafeList<Integer> list;
        private final int point;

        private ThreadEdit(final ThreadSafeList list, int point) {
            this.list = list;
            this.point = point;
        }

        @Override
        public void run() {
            for (int i = point; i < point + 50; i++) {
                this.list.add(i);
            }
        }
    }

    /**
     * Этим потоком создаём итератор
     * и проходим по нему.
     */
    private class ThreadIterator extends Thread {
        private final ThreadSafeList<Integer> list;

        private ThreadIterator(final ThreadSafeList list) {
            this.list = list;
        }

        @Override
        public void run() {
            Iterator iterator = this.list.iterator();
            while (iterator.hasNext()) {
                System.out.println(iterator.next());
            }
        }
    }

    /**
     * Простой демонстрационный метод, в котором можно увидеть,
     * что в поток, создающий итератор может попасть как список
     * уже отредактированный, так и не отредактированный.
     * Добавление в список для наглядности ведут два одинаковых потока.
     *
     * @throws InterruptedException
     */
    @Test
    public void whenExecuteThreads() throws InterruptedException {
        final ThreadSafeList<Integer> testList = new ThreadSafeList<>(20);
        testList.add(100);
        testList.add(200);
        testList.add(300);
        Thread first = new ThreadEdit(testList, 0);
        Thread second = new ThreadIterator(testList);
        Thread third = new ThreadEdit(testList, 100);
        third.start();
        first.start();
        second.start();
        first.join();
        second.join();
        third.join();
    }

}