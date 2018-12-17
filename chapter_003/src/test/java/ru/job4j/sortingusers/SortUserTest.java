package ru.job4j.sortingusers;

import org.junit.Test;

import java.util.*;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class SortUserTest {
    @Test
    public void whenListSortTreeSet() {

        List<User> a1 = List.of(
                new User("Vanya", 25),
                new User("Sasha", 22),
                new User("Artem", 23)

        );

        TreeSet<User> result = (TreeSet<User>) new SortUser().sort(a1);
        //Проверяем, что последний элемент в result- это первый элемент во вхожящем листе.
        assertThat(result.last().getName(), is(a1.get(0).getName()));
    }

    @Test
    public void wheneSortByAllFields() {
        User u1 = new User("Иван", 25);
        User u2 = new User("Николай", 22);
        User u3 = new User("Артём", 20);
        User u4 = new User("Артём", 25);

        List<User> a1 = List.of(
                u1,
                u2,
                u3,
                u4
        );

        List<User> result = new SortUser().sortByAllFields(a1);
        List<User> expect = List.of(
                u3,
                u4,
                u1,
                u2
        );

        assertThat(result, is(expect));
    }

    @Test
    public void wheneSortNameLength() {
        User u1 = new User("Иван", 25);
        User u2 = new User("Николай", 22);
        User u3 = new User("Артём", 20);
        User u4 = new User("Артём", 25);

        List<User> a1 = List.of(
                u1,
                u2,
                u3,
                u4
        );

        List<User> result = new SortUser().sortNameLength(a1);
        List<User> expect = List.of(
                u1,
                u3,
                u4,
                u2
        );

        assertThat(result, is(expect));
    }
}