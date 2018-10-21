package ru.job4j.chess.chessfigures;

import ru.job4j.chess.*;
import ru.job4j.chess.chessexceptions.ImpossibleMoveException;

public class PawnBlack extends Figure {

    public PawnBlack(Cell position) {
        super(position);
    }

    @Override
    public boolean wayCheck(Cell source, Cell dest) {
        return source.y == dest.y - 1 && source.x == dest.x;
    }

    @Override
    public Cell[] arraySteps(Cell source, Cell dest) {
        Cell[] steps = new Cell[1];
        steps[0] = dest;
        return steps;
    }

    @Override
    public Figure copy(Cell dest) {
        return new PawnBlack(dest);
    }
}