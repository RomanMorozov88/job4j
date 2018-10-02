package ru.job4j.sortingusers;

import java.util.*;

public class SortUser {

    public Set<User> sort(List<User> list) {
        return new TreeSet<>(list);
    }

    public List<User> sortNameLength(List<User> list) {
        list.sort(new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                return Integer.compare(o1.getName().length(),
                        o2.getName().length());
            }
        });
        return list;
    }

    List<User> sortByAllFields(List<User> list) {
        list.sort(new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                int nameValue = o1.getName().compareTo(o2.getName());
                int ageValue = Integer.compare(o1.getAge(), o2.getAge());
                return nameValue != 0 ? nameValue : ageValue;
            }
        });
        return list;
    }
}