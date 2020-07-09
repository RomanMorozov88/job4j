package ru.job4j.list.simplearraylist;

import org.hamcrest.MatcherAssert;
import org.junit.Test;
import org.junit.Before;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class SimpleArrayListTest {

    private SimpleArrayList<Integer> list;

    @Before
    public void beforeTest() {
        list = new SimpleArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
    }

    @Test
    public void whenAddThreeElementsThenUseGetOneResultTwo() {
        assertThat(list.get(1), is(2));
    }

    @Test
    public void whenAddThreeElementsThenUseGetSizeResultThree() {
        assertThat(list.getSize(), is(3));
    }

    @Test
    public void whenDeleteOne() {
        assertThat(list.delete(), is(3));
    }

    @Test
    public void whenDeleteOneTwoThree() {
        assertThat(list.delete(), is(3));
        assertThat(list.delete(), is(2));
        assertThat(list.delete(), is(1));
    }

    //Проверяем на исключение ConcurrentModificationException.
    @Test(expected = ConcurrentModificationException.class)
    public void getConcurrentModificationException() {
        SimpleArrayList<Integer> saTest = new SimpleArrayList<>();
        saTest.add(1);
        saTest.add(2);
        saTest.add(3);
        saTest.add(4);
        saTest.add(5);

        Iterator<Integer> itr = saTest.iterator();

        while (itr.hasNext()) {
            //Если находим тройку то пытаемся во время итерации добавить новый элемент.
            //Должны поймать ConcurrentModificationException.
            if (itr.next() == 3) {
                saTest.delete();
            }
        }
    }

    //Проверяем .iterator.
    @Test
    public void removeIntMethodTest() {
        SimpleArrayList<String> saTest = new SimpleArrayList<>();
        saTest.add("One");
        saTest.add("Two");
        saTest.add("Three");
        saTest.add("Four");
        saTest.add("Five");

        Iterator<String> it = saTest.iterator();

        MatcherAssert.assertThat(it.hasNext(), is(true));
        MatcherAssert.assertThat(it.next(), is("Five"));
        MatcherAssert.assertThat(it.hasNext(), is(true));
        MatcherAssert.assertThat(it.hasNext(), is(true));
        MatcherAssert.assertThat(it.next(), is("Four"));
        MatcherAssert.assertThat(it.hasNext(), is(true));
        MatcherAssert.assertThat(it.next(), is("Three"));
        MatcherAssert.assertThat(it.hasNext(), is(true));
        MatcherAssert.assertThat(it.next(), is("Two"));
        MatcherAssert.assertThat(it.hasNext(), is(true));
        MatcherAssert.assertThat(it.next(), is("One"));
        MatcherAssert.assertThat(it.hasNext(), is(false));
    }
}