package ru.job4j.studentsorting;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StudentsFilter {

    private StudentsFilter() {
    }

    public static List<Student> levelOf(List<Student> students, int bound) {
        List<Student> result =
                students
                        .stream()
                        //избавляемся от null объектов.
                        .flatMap(Stream::ofNullable)
                        //сортируем по убыванию баллов для корректной работы .takeWhile().
                        .sorted((o1, o2) -> Integer.compare(o2.getScope(), o1.getScope()))
                        .takeWhile(x -> x.getScope() >= bound)
                        //сортируем по имени используя переопределённый в классе Student метод compareTo().
                        .sorted()
                        .collect(Collectors.toList());

        return result;
    }
}
