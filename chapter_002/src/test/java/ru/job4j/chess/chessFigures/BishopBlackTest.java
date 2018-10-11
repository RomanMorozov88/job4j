package ru.job4j.chess.chessFigures;

import org.junit.Test;
import ru.job4j.chess.Board;
import ru.job4j.chess.Cell;
import ru.job4j.chess.chessExceptions.*;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;


public class BishopBlackTest {
    @Test
    public void bishopWay01() {
        Board board = new Board();
        BishopBlack figure = new BishopBlack(Cell.G1);
        board.add(figure);
        boolean result = board.move(Cell.G1, Cell.C5);
        boolean expected = true;
        assertThat(result, is(expected));
    }

    @Test
    public void bishopWay02() {
        Board board = new Board();
        BishopBlack figure = new BishopBlack(Cell.A8);
        board.add(figure);
        Cell[] way = figure.way(Cell.A8, Cell.E4);
        Cell[] expected = new Cell[]{Cell.B7, Cell.C6, Cell.D5, Cell.E4};
        assertThat(way, is(expected));
    }

    @Test
    public void bishopMoveCorrect() {
        Board board = new Board();
        BishopBlack figure = new BishopBlack(Cell.B1);
        board.add(figure);
        boolean way = board.move(Cell.B1, Cell.H7);
        boolean expected = true;
        assertThat(way, is(expected));
    }

    /**
     * Тесты исключений.
     */

    @Test(expected = FigureNotFoundException.class)
    public void bishopNotFound() {
        Board board = new Board();
        board.move(Cell.B2, Cell.H7);
    }

    @Test(expected = OccupiedWayException.class)
    public void bishopOccupatedCell() {
        Board board = new Board();
        BishopBlack figure = new BishopBlack(Cell.C6);
        BishopBlack figure2 = new BishopBlack(Cell.E4);
        board.add(figure);
        board.add(figure2);
        board.move(Cell.C6, Cell.G2);
    }

    @Test
    public void bishopImpossibleMove() {
        String testString = "Impossible way.";
        try {
            Board board = new Board();
            BishopBlack figure = new BishopBlack(Cell.B1);
            board.add(figure);
            board.move(Cell.B1, Cell.B4);
            fail("Expected NumberFormatException");
        } catch (ImpossibleMoveException inv) {
            assertThat(inv.getMessage(), containsString(testString));
        }
    }
}