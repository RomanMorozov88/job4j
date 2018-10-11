package ru.job4j.additionaltasks.changemachine;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ChangeTest {

    @Test
    public void whenChangeIsTenAndFive() {
        Change change = new Change();
        int[] result = change.changes(50, 35);
        int[] expect = {10, 5};
        assertThat(result, is(expect));
    }

    @Test
    public void whenChangeIsFiftyAndTenAndFive() {
        Change change = new Change();
        int[] result = change.changes(100, 33);
        int[] expect = {50, 10, 5, 2};
        assertThat(result, is(expect));
    }

    @Test
    public void whenWithoutChange() {
        Change change = new Change();
        int[] result = change.changes(50, 50);
        int[] expect = {0};
        assertThat(result, is(expect));
    }

    @Test
    public void whenChangeIsOne() {
        Change change = new Change();
        int[] result = change.changes(40, 39);
        int[] expect = {1};
        assertThat(result, is(expect));
    }

    @Test
    public void whenValueLessThanPrice() {
        Change change = new Change();
        int[] result = change.changes(10, 35);
        int[] expect = {-1};
        assertThat(result, is(expect));
    }

    @Test
    public void whenChangeNinetyNine() {
        Change change = new Change();
        int[] result = change.changes(100, 1);
        int[] expect = {50, 10, 10, 10, 10, 5, 2, 2};
        assertThat(result, is(expect));
    }

    @Test
    public void whenChangeIsTwoAndOne() {
        Change change = new Change();
        int[] result = change.changes(50, 47);
        int[] expect = {2, 1};
        assertThat(result, is(expect));
    }
}