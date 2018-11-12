package ru.job4j.chess.chessfigures;

import ru.job4j.chess.*;

public class BishopBlack extends Figure {

    public BishopBlack(Cell position) {
        super(position);
    }

    @Override
    public boolean wayCheck(Cell source, Cell dest) {
        return Math.abs(dest.x - source.x) == Math.abs(dest.y - source.y);
    }

    @Override
    public Cell[] arraySteps(Cell source, Cell dest) {
        int size = Math.abs(dest.x - source.x);
        Cell[] steps = new Cell[size];
        int directX = source.x < dest.x ? 1 : -1;
        int directY = source.y < dest.y ? 1 : -1;
        int deltaX = source.x;
        int deltaY = source.y;

        for (int i = 0; i < size; i++) {
            deltaX += directX;
            deltaY += directY;
            steps[i] = Cell.findCell(deltaX, deltaY);
        }
        return steps;
    }

    @Override
    public Figure copy(Cell dest) {
        return new BishopBlack(dest);
    }
}