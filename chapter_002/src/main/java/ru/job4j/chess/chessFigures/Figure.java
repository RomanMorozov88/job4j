package ru.job4j.chess.chessFigures;
import ru.job4j.chess.Cell;
import ru.job4j.chess.chessExceptions.ImpossibleMoveException;

public abstract class Figure {

    public final Cell position;

    public Figure(Cell position) {
        this.position = position;
    }

    /**
     * Абстрактный метод описывающий правила хода для фигуры.
     * @param source - начальная позиция.
     * @param dest - конечная позиция хода.
     * @return - возвращает массив, содержащий в себе все ячейки, через которые должна пройти фигура.
     * @throws ImpossibleMoveException
     */
    public abstract Cell[] way(Cell source, Cell dest) throws ImpossibleMoveException;

    public abstract Figure copy(Cell dest);
}