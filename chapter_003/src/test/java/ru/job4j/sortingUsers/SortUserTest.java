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
}