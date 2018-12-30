package ru.job4j.additionaltasks.arraytoint;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ArrayToIntTest {

    @Test
    public void calculateFromArray4Elements() {
        int[] inputTest = new int[]{1, 2, 3, 4};
        int result = ArrayToInt.calculateFromArray(inputTest);
        int expect = 20;
        assertThat(result, is(expect));
    }

    @Test
    public void calculateFromArray1Element() {
        int[] inputTest = new int[]{6};
        int result = ArrayToInt.calculateFromArray(inputTest);
        int expect = 36;
        assertThat(result, is(expect));
    }

    @Test
    public void calculateFromArray2Elements() {
        int[] inputTest = new int[]{1, 2};
        int result = ArrayToInt.calculateFromArray(inputTest);
        int expect = 4;
        assertThat(result, is(expect));
    }

    @Test
    public void calculateFromArrayZeroResult() {
        int[] inputTest = new int[]{1, 3, 5, 7};
        int result = ArrayToInt.calculateFromArray(inputTest);
        int expect = 0;
        assertThat(result, is(expect));
    }
}