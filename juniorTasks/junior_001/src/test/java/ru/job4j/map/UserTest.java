package ru.job4j.map;

import org.junit.Test;

import java.util.*;

public class UserTest {


    @Test
    public void map() {
        User u1 = new User("One", 1, new GregorianCalendar(2017, 1 , 25));
        User u2 = new User("One", 1, new GregorianCalendar(2017, 1 , 25));

        Map<String, User> map = new HashMap<>();
        map.put(u1.name, u1);
        map.put(u2.name, u2);

        //Выводим карту.
        System.out.println(map);

        System.out.println("================================");

        Set<User> set = new HashSet<>();

        set.add(u1);
        set.add(u2);

        //Выводим множество.
        System.out.println(set);
    }
}
