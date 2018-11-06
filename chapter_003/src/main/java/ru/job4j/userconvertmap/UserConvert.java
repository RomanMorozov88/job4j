package ru.job4j.userconvertmap;

import java.util.*;

public class UserConvert {
    public HashMap<Integer, User> process(List<User> list) {
        HashMap<Integer, User> result = new HashMap<>();
        list.stream()
                .forEach(x -> result.put(x.id, x));
        return result;
    }
}