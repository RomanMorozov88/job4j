package ru.job4j.generic.simplearray;

import org.junit.Test;

import java.util.Iterator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class SimpleArrayTest {

    //Проверяем .add Integer.
    @Test
    public void addIntMethodTest() {
        SimpleArray<Integer> saTest = new SimpleArray<>(5);
        saTest.add(10);
        int result = saTest.get(0);
        assertThat(result, is(10));
    }

    //Проверяем .add String.
    @Test
    public void addStringMethodTest() {
        SimpleArray<String> saTest = new SimpleArray<>(5);
        saTest.add("Ten");
        String result = saTest.get(0);
        assertThat(result, is("Ten"));
    }

    //Проверяем .set.
    @Test
    public void setStringMethodTest() {
        SimpleArray<String> saTest = new SimpleArray<>(5);
        saTest.add("Ten");
        saTest.set(0, "One");
        String result = saTest.get(0);
        assertThat(result, is("One"));
    }

    //Проверяем исключение при входящем индексе искомого эелемента, превышающем
    //значение длины внутреннего массива saTest.
    @Test(expected = IndexOutException.class)
    public void getExceptionMethodTestOne() {
        SimpleArray<Integer> saTest = new SimpleArray<>(5);
        saTest.get(5);
    }

    //Проверяем .remove и заодно .iterator.
    @Test
    public void removeIntMethodTest() {
        SimpleArray<String> saTest = new SimpleArray<>(5);
        saTest.add("One");
        saTest.add("Two");
        saTest.add("Three");
        saTest.add("Four");
        saTest.add("Five");

        //Удаляем элемент "Three".
        saTest.remove(2);

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