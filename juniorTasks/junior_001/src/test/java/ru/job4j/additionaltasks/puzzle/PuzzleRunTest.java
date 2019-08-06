package ru.job4j.additionaltasks.puzzle;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class PuzzleRunTest {

    @Test
    public void whenInputWin() {
        int[] input = new int[]{1, 2, 3, 4, 0, 5, 6, 7};
        PuzzleRun run = new PuzzleRun();
        int[] result = run.resolve(input);
        assertThat(result.length, is(0));
    }

    @Test
    public void whenInputWrong() {
        int[] input = new int[]{1, 2, 3, 4, 5, 0, 6, 7};
        PuzzleRun run = new PuzzleRun();
        int[] result = run.resolve(input);
        assertThat(result.length, is(1));
        assertThat(result[0], is(5));
    }

    @Test
    public void whenInputWrongTwo() {
        int[] input = new int[]{2, 1, 3, 4, 0, 5, 6, 7};
        PuzzleRun run = new PuzzleRun();
        int[] result = run.resolve(input);
        assertThat(result.length, is(7));
    }

}