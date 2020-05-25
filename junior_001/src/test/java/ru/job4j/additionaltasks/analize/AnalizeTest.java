package ru.job4j.additionaltasks.analize;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class AnalizeTest {

    Analize analizeTest;

    //Начальное состояние.
    List<Analize.User> oldtest;
    //Изменённое состояние.
    List<Analize.User> newTest;

    Analize.User u1;
    Analize.User u2;
    Analize.User u3;
    Analize.User u4;
    Analize.User u5;

    @Before
    public void beforeTest() {
        analizeTest = new Analize();

        u1 = new Analize.User(1, "One");
        u2 = new Analize.User(2, "Two");
        u3 = new Analize.User(3, "Three");
        u4 = new Analize.User(4, "Four");
        u5 = new Analize.User(5, "Five");
    }

    @Test
    public void whenNoAnyChange() {

        oldtest = new ArrayList<>(Arrays.asList(u1, u2, u3, u4));
        newTest = new ArrayList<>(Arrays.asList(u1, u2, u3, u4));

        Analize.Info result = analizeTest.diff(oldtest, newTest);

        assertThat(result.added, is(0));
        assertThat(result.changed, is(0));
        assertThat(result.deleted, is(0));
    }

    @Test
    public void numberOfDeleted() {

        oldtest = new ArrayList<>(Arrays.asList(u1, u2, u3, u4));
        newTest = new ArrayList<>(Arrays.asList(u1, u2));

        Analize.Info result = analizeTest.diff(oldtest, newTest);

        assertThat(result.added, is(0));
        assertThat(result.changed, is(0));
        assertThat(result.deleted, is(2));
    }

    @Test
    public void numberOfAdded() {

        oldtest = new ArrayList<>(Arrays.asList(u1, u2, u3, u4));
        newTest = new ArrayList<>(Arrays.asList(u1, u2, u3, u4, u5));

        Analize.Info result = analizeTest.diff(oldtest, newTest);

        assertThat(result.added, is(1));
        assertThat(result.changed, is(0));
        assertThat(result.deleted, is(0));
    }

    @Test
    public void numberOfChanged() {

        oldtest = new ArrayList<>(Arrays.asList(u1, u2, u3, u4));

        //Будем считать, что этот объект является отредактированным объектом u2.
        Analize.User u2changed = new Analize.User(2, "TwoChanged");

        newTest = new ArrayList<>(Arrays.asList(u1, u2changed, u3, u4));

        Analize.Info result = analizeTest.diff(oldtest, newTest);

        assertThat(result.added, is(0));
        assertThat(result.changed, is(1));
        assertThat(result.deleted, is(0));
    }

    @Test
    public void numberOfAllPoints() {

        oldtest = new ArrayList<>(Arrays.asList(u1, u2, u3, u4));

        //Будем считать, что этот объект является отредактированным объектом u2.
        Analize.User u2changed = new Analize.User(2, "TwoChanged");

        newTest = new ArrayList<>(Arrays.asList(u1, u2changed, u5));

        Analize.Info result = analizeTest.diff(oldtest, newTest);

        assertThat(result.added, is(1));
        assertThat(result.changed, is(1));
        assertThat(result.deleted, is(2));
    }
}