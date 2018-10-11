package ru.job4j.chess.chessfigures;

import ru.job4j.chess.*;
import ru.job4j.chess.chessexceptions.ImpossibleMoveException;

public class PawnBlack extends Figure {

    public PawnBlack(Cell position) {
        super(position);
    }

    @Override
    public Cell[] way(Cell source, Cell dest) throws ImpossibleMoveException {
        Cell[] steps = new Cell[1];
        if (source.y == dest.y - 1 && source.x == dest.x) {
            steps[0] = dest;
        } else {
            throw new ImpossibleMoveException("Impossible way.");
        }
        return steps;
    }

    @Override
    public Figure copy(Cell dest) {
        return new PawnBlack(dest);
    }
}