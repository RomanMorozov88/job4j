package ru.job4j.chess.chessfigures;

import ru.job4j.chess.*;
import ru.job4j.chess.chessexceptions.ImpossibleMoveException;

public class BishopBlack extends Figure {

    public BishopBlack(Cell position) {
        super(position);
    }

    @Override
    public Cell[] way(Cell source, Cell dest) throws ImpossibleMoveException {
        int sizeX = Math.abs(dest.x - source.x);
        int sizeY = Math.abs(dest.y - source.y);
        if (sizeX != sizeY || sizeX == 0 || sizeY == 0) {
            throw new ImpossibleMoveException("Impossible way.");
        }
        Cell[] steps = new Cell[sizeX];
        int directX = source.x < dest.x ? 1 : -1;
        int directY = source.y < dest.y ? 1 : -1;
        int deltaX = source.x;
        int deltaY = source.y;

        for (int i = 0; i < sizeX; i++) {
            steps[i] = Cell.findCell(deltaX += directX, deltaY += directY);
        }
        return steps;
    }

    @Override
    public Figure copy(Cell dest) {
        return new BishopBlack(dest);
    }
}