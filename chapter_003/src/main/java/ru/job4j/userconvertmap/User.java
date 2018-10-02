package ru.job4j.userconvertmap;

import java.util.Random;

public class User {
    public int id;
    public String name;
    public String city;

    public User(String name, String city) {
        this.name = name;
        this.city = city;
        this.id = idGenerate();
    }

    private int idGenerate() {
        Random rnd = new Random();
        return rnd.nextInt((10 - 1) + 1) + 1;
    }
}