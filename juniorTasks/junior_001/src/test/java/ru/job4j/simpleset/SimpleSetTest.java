package ru.job4j.simpleset;

import org.junit.Test;

import java.util.Iterator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class SimpleSetTest {

    @Test
    public void addAndIteratorTest() {
        SimpleSet<String> saTest = new SimpleSet<>();
        saTest.add("One");
        saTest.add("Two");
        saTest.add("Four");
        saTest.add("Four"); //Уже есть таклй элемент.
        saTest.add("Five");

        Iterator<String> it = saTest.iterator();

        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is("One"));
        assertThat(it.hasNext(), is(true));
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is("Two"));
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is("Four"));
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is("Five"));
        assertThat(it.hasNext(), is(false));
    }
}