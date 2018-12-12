package ru.job4j.studentsorting;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class StudentFilterTest {

    @Test
    public void whenBoundIsFifty() {
        Student st1 = new Student("Петров Василий Николаевич", 32);
        Student st2 = new Student("Мишин Петр Юрьевич", 72);
        Student st3 = new Student("Иванова Марья Олеговна", 100);
        Student st4 = new Student("Каркарова Анастасия Петровна", 81);
        Student st5 = null;


        List<Student> students = Arrays.asList(st2, st1, null, st4, st5, st3);

        List<Student> result = StudentsFilter.levelOf(students, 50);
        List<Student> expect = Arrays.asList(st3, st4, st2);
        assertThat(result, is(expect));
    }
}
