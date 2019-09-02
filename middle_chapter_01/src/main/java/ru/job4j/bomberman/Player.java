package ru.job4j.bomberman;

/**
 * Класс игрока.
 */
public class Player {

    private final GameBoard board;
    private final Cell start;

    public Player(GameBoard board, Cell start) {
        this.board = board;
        this.start = start;
    }

    /**
     * Метод для движения. Если на новую позици можно перейти- вернёт true.
     *
     * @param source - откуда начинаем движение.
     * @param dist   - куда хотим перейти.
     * @return
     * @throws InterruptedException
     */
    public boolean step(Cell source, Cell dist) throws InterruptedException {
        return this.board.move(source, dist);
    }
}
