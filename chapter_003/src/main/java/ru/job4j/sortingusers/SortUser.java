package ru.job4j.sortingusers;

import java.util.*;
import java.util.stream.Collectors;

public class SortUser {

    public Set<User> sort(List<User> list) {
        return new TreeSet<>(list);
    }

    public List<User> sortNameLength(List<User> list) {
        List<User> result = list.stream()
                .sorted(Comparator.comparing((User o) -> o.getName().length()))
                .collect(Collectors.toList());
        return result;
    }

    List<User> sortByAllFields(List<User> list) {
        List<User> result = list.stream()
                .sorted((o1, o2) -> {
                    int nameValue = o1.getName().compareTo(o2.getName());
                    int ageValue = Integer.compare(o1.getAge(), o2.getAge());
                    return nameValue != 0 ? nameValue : ageValue;
                })
                .collect(Collectors.toList());
        return result;
    }
}