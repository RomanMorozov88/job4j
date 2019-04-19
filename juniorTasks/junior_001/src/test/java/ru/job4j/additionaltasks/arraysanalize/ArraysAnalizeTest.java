package ru.job4j.additionaltasks.arraysanalize;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class ArraysAnalizeTest {

    public <T extends Number> boolean support(T[] left, T[] right) {
        ArraysAnalize<T> arraysAnalize = new ArraysAnalize<>();
        return arraysAnalize.arraysComparison(left, right);
    }

    @Test
    public void whenBothIntegersArraysIsEquals() {
        Integer[] left = new Integer[]{1, 2, 3};
        Integer[] right = new Integer[]{1, 2, 3};
        boolean result = support(left, right);
        assertThat(result, is(true));
    }

    @Test
    public void whenBothIntegersArraysHaveDoubleFirst() {
        Integer[] left = new Integer[]{1, 1, 3};
        Integer[] right = new Integer[]{1, 3, 3};
        boolean result = support(left, right);
        assertThat(result, is(false));
    }

    @Test
    public void whenBothIntegersArraysHaveDoubleSecond() {
        Integer[] left = new Integer[]{1, 2, 1, 3};
        Integer[] right = new Integer[]{2, 1, 1, 3};
        boolean result = support(left, right);
        assertThat(result, is(true));
    }

    @Test
    public void whenBothIntegersArraysIsNotEquals() {
        Integer[] left = new Integer[]{1, 3, 2};
        Integer[] right = new Integer[]{1, 2, 3};
        boolean result = support(left, right);
        assertThat(result, is(true));
    }

    @Test
    public void whenBothIntegersArraysIsNotEqualsByLength() {
        Integer[] left = new Integer[]{10, 15, 26};
        Integer[] right = new Integer[]{10, 15};
        boolean result = support(left, right);
        assertThat(result, is(false));
    }

    @Test
    public void whenBothDoublesArraysIsEquals() {
        Double[] left = new Double[]{1.5, 2.4, 3.7};
        Double[] right = new Double[]{1.5, 2.4, 3.7};
        boolean result = support(left, right);
        assertThat(result, is(true));
    }

}