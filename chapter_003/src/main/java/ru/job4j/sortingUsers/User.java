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

        int result = this.age.compareTo(o.age);
        if (result == 0) {
            result = 1;
        }
        return result;
    }
}