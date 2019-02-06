package ru.job4j.map;

import java.util.Calendar;

public class User {

    public String name;
    public int children;
    public Calendar birthday;


    public User(String name, int children, Calendar calendar) {
        this.name = name;
        this.children = children;
        this.birthday = calendar;
    }

    @Override
    public String toString() {
        return "name: " + name
                + "; hash: " + hashCode()
                + "; bDay: " + birthday.get(Calendar.DAY_OF_MONTH) + "/"
                + birthday.get(Calendar.MONTH) + "/" + birthday.get(Calendar.YEAR)
                + "\n";
    }
}