package ru.job4j.chess.chessfigures;

import ru.job4j.chess.Cell;
import ru.job4j.chess.chessexceptions.ImpossibleMoveException;

import java.util.function.BiFunction;
import java.util.function.BiPredicate;

public abstract class Figure {

    public final Cell position;

    public Figure(Cell position) {
        this.position = position;
    }

    /**
     * Метод описывающий правила хода для фигуры.
     *
     * @param source - начальная позиция.
     * @param dest   - конечная позиция хода.
     * @return - возвращает массив, содержащий в себе все ячейки, через которые должна пройти фигура.
     * @throws ImpossibleMoveException
     */
    public Cell[] way(Cell source, Cell dest, BiPredicate<Cell, Cell> predicate,
                      BiFunction<Cell, Cell, Cell[]> function)
            throws ImpossibleMoveException {
        if (!predicate.test(source, dest)) {
            throw new ImpossibleMoveException("Impossible way.");
        }
        return function.apply(source, dest);
    }

    public abstract boolean wayCheck(Cell source, Cell dest);

    public abstract Cell[] arraySteps(Cell source, Cell dest);

    public abstract Figure copy(Cell dest);
}