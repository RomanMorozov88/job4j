package ru.job4j.sortingUsers;

import org.junit.Test;

import java.util.*;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class SortUserTest {
    @Test
    public void whenListSortTreeSet() {
        List<User> a1 = new ArrayList<>();
        a1.add(new User("Vanya", 25));
        a1.add(new User("Sasha", 22));
        a1.add(new User("Artem", 23));

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
        List<User> a1 = new ArrayList<>();
        a1.add(u1);
        a1.add(u2);
        a1.add(u3);
        a1.add(u4);
        List<User> result = new SortUser().sortByAllFields(a1);
        List<User> expect = new ArrayList<>();
        expect.add(u3);
        expect.add(u4);
        expect.add(u1);
        expect.add(u2);
        assertThat(result, is(expect));
    }

    @Test
    public void wheneSortNameLength() {
        User u1 = new User("Иван", 25);
        User u2 = new User("Николай", 22);
        User u3 = new User("Артём", 20);
        User u4 = new User("Артём", 25);
        List<User> a1 = new ArrayList<>();
        a1.add(u1);
        a1.add(u2);
        a1.add(u3);
        a1.add(u4);
        List<User> result = new SortUser().sortNameLength(a1);
        List<User> expect = new ArrayList<>();
        expect.add(u1);
        expect.add(u3);
        expect.add(u4);
        expect.add(u2);
        assertThat(result, is(expect));
    }
}