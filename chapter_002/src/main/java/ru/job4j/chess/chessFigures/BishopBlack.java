package ru.job4j.chess.chessFigures;

import ru.job4j.chess.*;
import ru.job4j.chess.chessExceptions.ImpossibleMoveException;

public class BishopBlack extends Figure {

    public BishopBlack (Cell position) {
        super(position);
    }

    @Override
    public Cell[] way(Cell source, Cell dest) throws ImpossibleMoveException {
        int size = Math.abs(dest.x - source.x);
        if (size == 0) {
            throw new ImpossibleMoveException("Impossible way.");
        }

        Cell[] steps = new Cell[size];
        int directX = source.x < dest.x ? 1 : -1;
        int directY = source.y < dest.y ? 1 : -1;
        int deltaX = source.x;
        int deltaY = source.y;

        for (int i = 0; i < size; i++) {
            if ((Math.abs(dest.x - deltaX) == size - i) &&
                    (Math.abs(dest.y - deltaY) == size - i)) {
                steps[i] = Cell.findCell(deltaX += directX, deltaY += directY);
            } else throw new ImpossibleMoveException("Impossible way.");
        }
        return steps;
    }

    @Override
    public Figure copy(Cell dest) {
        return new BishopBlack(dest);
    }
}