package ru.job4j.userconvertmap;

import java.util.*;

public class UserConvert {
    public HashMap<Integer, User> process(List<User> list) {
        HashMap<Integer, User> result = new HashMap<>();
        for (User i : list) {
            result.put(i.id, i);
        }
        return result;
    }
}