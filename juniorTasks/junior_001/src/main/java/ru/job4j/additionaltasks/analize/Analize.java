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

        //С помощью двух for`ов пробегаемся каждым элементом одного списка
        //по каждому элементу другого.
        for (User uOne : previous) {
            for (User uTwo : current) {
                //Проверяем совпадения по id.
                if (uOne.id == uTwo.id) {
                    if (!uOne.name.equals(uTwo.name)) {
                        //Если id совпадают, но name разные-
                        //считатем, что такой объект был отредактирован.
                        result.changed++;
                    }
                    meetCounter++;
                }
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