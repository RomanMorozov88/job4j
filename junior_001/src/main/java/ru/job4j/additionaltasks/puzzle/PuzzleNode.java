package ru.job4j.additionaltasks.puzzle;

import java.util.Arrays;
import java.util.List;

/**
 * Узел, хранящий в себе состояние
 * и пройденный до данного состояния путь.
 */
public class PuzzleNode {

    private int[] state;
    private List<Integer> path;

    public PuzzleNode(int[] state, List<Integer> path) {
        this.state = Arrays.copyOf(state, state.length);
        this.path = path;
    }

    public int[] getState() {
        return this.state;
    }

    public List<Integer> getPath() {
        return this.path;
    }

}