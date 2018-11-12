package ru.job4j.chess;

import ru.job4j.chess.chessexceptions.*;
import ru.job4j.chess.chessfigures.*;

import java.util.Arrays;

public class Board {

    Figure[] figures = new Figure[32];
    private int count = 0;

    public void add(Figure figure) {
        this.figures[count++] = figure;
    }

    public boolean move(Cell source, Cell dest)
            throws ImpossibleMoveException, OccupiedWayException, FigureNotFoundException {
        int index = this.existFigure(source);
        if (index == -1) {
            throw new FigureNotFoundException("Not found.");
        }
        //для каждой фигуры вызывается через лямбду соответствующие ей методы правил хода.
        Cell[] path = this.figures[index].way(source, dest,
                this.figures[index]::wayCheck, this.figures[index]::arraySteps);
        if (Arrays.stream(path)
                .anyMatch(x -> x != null && this.existFigure(x) > -1)) {
            throw new OccupiedWayException("Occupated.");
        }
        figures[index] = figures[index].copy(dest);
        return true;
    }

    /**
     * Метод для проверки- занята ли данная ячейка фигурой.
     *
     * @param source - ячейка для проверки.
     * @return возвращает индекс фигуры, занимающей ячейку
     * (или возвращает -1 если ячейка не занята)
     */
    public int existFigure(Cell source) {
        int index = -1;
        for (int i = 0; i < this.figures.length; i++) {
            if (figures[i] != null && figures[i].position.equals(source)) {
                index = i;
            }
        }
        return index;
    }
}