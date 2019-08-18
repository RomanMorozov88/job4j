package ru.job4j.nonblockingcache;

import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicReference;

import static org.hamcrest.Matchers.is;

public class NonBlockingCacheTest {

    @Test
    public void whenThrowException() throws InterruptedException {
        NonBlockingCache test = new NonBlockingCache();
        test.add(new Base(1));
        AtomicReference<Exception> ex = new AtomicReference<>();
        Thread thread1 = new Thread(
                () -> {
                    try {
                        test.update(new Base(1));
                    } catch (OptimisticException e) {
                        ex.set(e);
                    }
                }
        );
        Thread thread2 = new Thread(
                () -> {
                    try {
                        test.update(new Base(1));
                    } catch (OptimisticException e) {
                        ex.set(e);
                    }
                }
        );
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        Assert.assertThat(ex.get().getMessage(), is("Version mismatch"));
    }
}