package ru.job4j.additionaltasks.analize;

import java.util.*;

/**
 * Это задание сводиться к определению разницы между начальным состояние массива и измененным.
 */
public class Analize {

    public Info diff(List<User> previous, List<User> current) {
        Info result = new Info();
        //Счётчик совпадений id.
        int meetCounter = 0;
        //Карта, которую вводим для того, что бы избавиться от квадратичной временной сложности.
        HashMap<Integer, User> checkMap = new HashMap<>();
        for(User u : previous) {
            checkMap.put(u.id, u);
        }
        for(User u : current) {
            //Проверяем совпадения по id.
            User buffer = checkMap.get(u.id);
            if (buffer != null) {
                if (!buffer.name.equals(u.name)) {
                    //Если id совпадают, но name разные-
                    //считатем, что такой объект был отредактирован.
                    result.changed++;
                }
                meetCounter++;
            }
        }
        //Определяем количесвто удалённых элементов.
        result.deleted = previous.size() - meetCounter;
        //Определяем количество добавленных элементов.
        result.added = current.size() - meetCounter;

        return result;
    }

    static class User {
        int id;
        String name;

        public User(int id, String name) {
            this.id = id;
            this.name = name;
        }
    }

    static class Info {

        int added;
        int changed;

        int deleted;

    }
}