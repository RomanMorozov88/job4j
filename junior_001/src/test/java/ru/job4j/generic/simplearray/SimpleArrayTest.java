package ru.job4j.generic.simplearray;

import org.junit.Test;

import java.util.ConcurrentModificationException;
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

    //Проверяем на исключение ConcurrentModificationException.
    @Test(expected = ConcurrentModificationException.class)
    public void getConcurrentModificationException() {
        SimpleArray<Integer> saTest = new SimpleArray<>(5);
        saTest.add(1);
        saTest.add(2);
        saTest.add(3);
        saTest.add(4);
        saTest.add(5);

        Iterator<Integer> itTest = saTest.iterator();

        while (itTest.hasNext()) {
            //Если находим тройку то пытаемся во время итерации добавить новый элемент.
            //Должны поймать ConcurrentModificationException.
            if (itTest.next() == 3) {
                saTest.add(33);
            }
        }
    }

    //Косвенно проверяем, что при заполнении хранилище расширяется.
    //Косвенно- что бы не прописывать методы для вывода длины внутреннего массива
    //и не менять его модификатор доступа.
    @Test
    public void sizeModMethodTest() {
        SimpleArray<String> saTest = new SimpleArray<>(2);
        saTest.add("One");
        saTest.add("Two");
        //Тут происходит расширение хранилища до 4 (2 * 2).
        saTest.add("Three");
        saTest.add("Four");
        //Тут происходит расширение хранилища до 8 (4 * 2).
        saTest.add("Five");

        Iterator<String> itTest = saTest.iterator();

        //Все пять элементов на месте.
        assertThat(itTest.next(), is("One"));
        assertThat(itTest.next(), is("Two"));
        assertThat(itTest.next(), is("Three"));
        assertThat(itTest.next(), is("Four"));
        assertThat(itTest.next(), is("Five"));
        assertThat(itTest.hasNext(), is(false));
    }
}