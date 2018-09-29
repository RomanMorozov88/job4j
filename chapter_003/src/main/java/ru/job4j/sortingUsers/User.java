package ru.job4j.sortingUsers;

public class User implements Comparable<User>{

    private String name;
    private Integer age;

    public User(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return this.name;
    }
    public int getAge() {
        return this.age;
    }

    @Override
    public int compareTo(User o) {
        int ageValue = this.age.compareTo(o.age);
        int nameValue = this.name.compareTo(o.name);
        return ageValue != 0 ? ageValue : nameValue;
    }
}