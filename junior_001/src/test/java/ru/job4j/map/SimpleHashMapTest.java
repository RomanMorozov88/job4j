package ru.job4j.map;

import org.junit.Test;

import java.util.Iterator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class SimpleHashMapTest {

    //Просто добавляем новый элемент.
    @Test
    public void insertFirst() {
        SimpleHashMap<String, Integer> hmTest = new SimpleHashMap<>();
        boolean result = hmTest.insert("One", 1);
        assertThat(result, is(true));
    }

    //Добавляем элемент с уже существующим ключом. Значение перезапишется.
    @Test
    public void insertSecond() {
        SimpleHashMap<String, Integer> hmTest = new SimpleHashMap<>();
        hmTest.insert("One", 1);
        boolean result = hmTest.insert("One", 10);
        assertThat(result, is(false));
    }

    //Получаем элемент по ключу.
    @Test
    public void get() {
        SimpleHashMap<String, Integer> hmTest = new SimpleHashMap<>();
        hmTest.insert("One", 1);
        assertThat(hmTest.get("One"), is(1));
    }

    //Удаляем существующий элемент.
    @Test
    public void deleteFirst() {
        SimpleHashMap<String, Integer> hmTest = new SimpleHashMap<>();
        hmTest.insert("One", 1);
        boolean result = hmTest.delete("One");
        assertThat(result, is(true));
    }

    //Пытаемся удалить несуществующий элемент.
    @Test
    public void deleteSecond() {
        SimpleHashMap<String, Integer> hmTest = new SimpleHashMap<>();
        hmTest.insert("One", 1);
        boolean result = hmTest.delete("Two");
        assertThat(result, is(false));
    }

    //Создаём итератор и выводим на экран содержимое.
    @Test
    public void iterator() {
        SimpleHashMap<String, Integer> hmTest = new SimpleHashMap<>();
        hmTest.insert("One", 1);
        hmTest.insert("Two", 2);
        hmTest.insert("Three", 3);
        hmTest.insert("Four", 4);

        Iterator<SimpleHashMap.Node> itr = hmTest.iterator();

        //Здесь будет виден результат коллизии- с ключом "One" в итоге будет значение 4.
        //И всего элементов будет не четыре- а три.
        while (itr.hasNext()) {
            System.out.println(itr.next().toString());
        }
    }
}