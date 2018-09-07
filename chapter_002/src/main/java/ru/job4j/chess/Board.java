package ru.job4j.chess;
import ru.job4j.chess.chessExceptions.*;
import ru.job4j.chess.chessFigures.*;

public class Board {

    Figure[] figures = new Figure[32];
    private int count = 0;

    public void add(Figure figure) {
        this.figures[count++] = figure;
    }

    public boolean move(Cell source, Cell dest) throws ImpossibleMoveException, OccupiedWayException, FigureNotFoundException {
        int index = this.existFigure(source);
        if (index == -1) {
            throw new FigureNotFoundException("Not found.");
        }
        Cell[] path = this.figures[index].way(source, dest);
        for (int i = 0; i < path.length; i++) {
            if(path[i] != null && this.existFigure(path[i]) > -1) {
                throw new OccupiedWayException("Occupated.");
            }
        }
        figures[index] = figures[index].copy(dest);
        return true;
    }

    /**
     * Метод для проверки- занята ли данная ячейка фигурой.
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