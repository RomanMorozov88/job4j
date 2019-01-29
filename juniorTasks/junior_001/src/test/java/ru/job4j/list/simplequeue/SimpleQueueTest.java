package ru.job4j.list.simplequeue;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class SimpleQueueTest {

    @Test
    public void whenAddElementsThenDelete() {
        SimpleQueue<Integer> sq = new SimpleQueue<>();

        sq.push(1);
        sq.push(2);
        sq.push(3);

        assertThat(sq.poll(), is(1));
        assertThat(sq.poll(), is(2));
        assertThat(sq.poll(), is(3));

        sq.push(4);
        sq.push(5);

        assertThat(sq.poll(), is(4));

        sq.push(6);
        sq.push(7);
        sq.push(8);

        assertThat(sq.poll(), is(5));
        assertThat(sq.poll(), is(6));
        assertThat(sq.poll(), is(7));
    }

    @Test
    public void whenEmptyQueueFirst() {
        SimpleQueue<Integer> sq = new SimpleQueue<>();

        sq.poll();

        sq.push(1);
        sq.push(2);
        sq.push(3);

        assertThat(sq.poll(), is(1));
        assertThat(sq.poll(), is(2));
        assertThat(sq.poll(), is(3));
    }
}