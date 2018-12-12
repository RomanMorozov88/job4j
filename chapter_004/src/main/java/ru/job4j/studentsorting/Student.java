package ru.job4j.studentsorting;

public class Student implements Comparable<Student> {

    private String name;
    private Integer scope;

    public Student(String name, Integer scope) {
        this.name = name;
        this.scope = scope;
    }

    public String getName() {
        return this.name;
    }

    public int getScope() {
        return this.scope;
    }

    @Override
    public int compareTo(Student o) {
        return this.name.compareTo(o.name);
    }
}
