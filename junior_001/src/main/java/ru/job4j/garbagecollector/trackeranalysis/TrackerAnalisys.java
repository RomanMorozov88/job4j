package ru.job4j.garbagecollector.trackeranalysis;

import ru.job4j.tracker.*;

/**
 * Памятка по ключам:
 * -Xmx5m
 * -XX:+UseSerialGC
 * -XX:+UseParallelGC
 * -XX:+UseConcMarkSweepGC
 */
public class TrackerAnalisys {

    public static void setInput(String[] items, Tracker tracker) {
        Input input = new StubInput(items);
        new StartUI(input, tracker).init();
    }

    public static void main(String[] args) throws InterruptedException {
        Thread.sleep(10000);
        Tracker tracker = new Tracker();

        for (int i = 0; i < 1000; i++) {
            setInput(new String[]{"0", ">>>" + i, "desc", "1", "6"}, tracker);
        }
    }
}