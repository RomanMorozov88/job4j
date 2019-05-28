package ru.job4j.additionaltasks.games.tictac;

import org.junit.Test;
import ru.job4j.additionaltasks.games.tictac.players.Player;

import java.util.Scanner;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class TicTacGameLogicTest {

    private class TestPlayer implements Player {
        private Integer[] testWork = new Integer[2];
        private String name = "TestPlayer";
        private String testMark = " T ";
        public void setArray(int a, int b) {
            this.testWork[0] = a;
            this.testWork[1] = b;
        }
        @Override
        public Integer[] move() {
            return this.testWork;
        }
        @Override
        public void setName(String name) {
            this.name = name;
        }
        @Override
        public String getMark() {
            return this.testMark;
        }
        @Override
        public String getName() {
            return this.name;
        }
    }

    @Test
    public void wenJustAddMark() {
        TicTacGameLogic logicTest = new TicTacGameLogic(new Scanner(System.in));
        logicTest.initGameField();
        TestPlayer tp = new TestPlayer();
        tp.setArray(1, 1);
        boolean result = logicTest.checkCell(1, 1);
        assertThat(result, is(true));
        logicTest.getMove(tp);
        result = logicTest.checkCell(1, 1);
        assertThat(result, is(false));
    }

    @Test
    public void wenWrongValue() {
        TicTacGameLogic logicTest = new TicTacGameLogic(new Scanner(System.in));
        logicTest.initGameField();
        TestPlayer tp = new TestPlayer();
        tp.setArray(0, 0);
        boolean result = logicTest.getMove(tp);
        assertThat(result, is(false));
    }

    @Test
    public void wenWinHorizont() {
        TicTacGameLogic logicTest = new TicTacGameLogic(new Scanner(System.in));
        logicTest.initGameField();
        TestPlayer tp = new TestPlayer();
        tp.setArray(1, 1);
        logicTest.getMove(tp);
        tp.setArray(1, 2);
        logicTest.getMove(tp);
        tp.setArray(1, 3);
        logicTest.getMove(tp);
        boolean result = logicTest.win(tp.getMark());
        assertThat(result, is(true));
    }

    @Test
    public void wenWinVertical() {
        TicTacGameLogic logicTest = new TicTacGameLogic(new Scanner(System.in));
        logicTest.initGameField();
        TestPlayer tp = new TestPlayer();
        tp.setArray(1, 2);
        logicTest.getMove(tp);
        tp.setArray(2, 2);
        logicTest.getMove(tp);
        tp.setArray(3, 2);
        logicTest.getMove(tp);
        boolean result = logicTest.win(tp.getMark());
        assertThat(result, is(true));
    }

    @Test
    public void wenWinReverseDiagonal() {
        TicTacGameLogic logicTest = new TicTacGameLogic(new Scanner(System.in));
        logicTest.initGameField();
        TestPlayer tp = new TestPlayer();
        tp.setArray(3, 1);
        logicTest.getMove(tp);
        tp.setArray(2, 2);
        logicTest.getMove(tp);
        tp.setArray(1, 3);
        logicTest.getMove(tp);
        boolean result = logicTest.win(tp.getMark());
        assertThat(result, is(true));
    }
}