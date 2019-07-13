package ru.job4j.additionaltasks.robotwalk;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class RobotWalkTest {

    @Test
    public void whenCantReachFinish() {
        int[][] board = new int[][]{
                {1, 0, 1},
                {1, 0, 0},
                {1, 1, 1}
        };
        RobotWalk testWalk = new RobotWalk();
        int result = testWalk.minWay(board, 0, 0, 0, 2);
        assertThat(result, is(-1));
    }

    @Test
    public void whenFinishIsZero() {
        int[][] board = new int[][]{
                {1, 0, 0},
                {1, 0, 1},
                {1, 1, 1}
        };
        RobotWalk testWalk = new RobotWalk();
        int result = testWalk.minWay(board, 0, 0, 0, 2);
        assertThat(result, is(-1));
    }

    @Test
    public void whenSimpleRunOne() {
        int[][] board = new int[][]{
                {1, 0, 1},
                {1, 0, 0},
                {1, 1, 1}
        };
        RobotWalk testWalk = new RobotWalk();
        int result = testWalk.minWay(board, 0, 0, 2, 2);
        assertThat(result, is(4));
    }

    @Test
    public void whenSimpleRunTwo() {
        int[][] board = new int[][]{
                {1, 0, 0, 1},
                {1, 0, 1, 1},
                {1, 0, 1, 0},
                {1, 1, 1, 0}
        };
        RobotWalk testWalk = new RobotWalk();
        int result = testWalk.minWay(board, 0, 3, 0, 0);
        assertThat(result, is(9));
    }

    @Test
    public void whenSimpleRunThree() {
        int[][] board = new int[][]{
                {1, 1, 1},
                {1, 1, 1},
                {1, 1, 1}
        };
        RobotWalk testWalk = new RobotWalk();
        int result = testWalk.minWay(board, 0, 2, 0, 0);
        assertThat(result, is(2));
    }

}