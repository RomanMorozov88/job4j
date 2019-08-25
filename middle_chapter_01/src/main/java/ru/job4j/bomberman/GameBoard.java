package ru.job4j.bomberman;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Реализовать игру бомбермен. Без графики, без меню и без пользовательского ввода.
 * Интересует только логика и дизайн.
 * Первый этап:
 * <p>
 * 1. Есть игровое поле Board. Представляющее из себя массив ReentrantLock[][] board.
 * 2. Есть две нити. Одна нить эмулирует поведение героя. То есть герой стоит на клетке board.
 * Клетка в этом случае должна быть заблокирована lock.lock();
 * 3. Герой должен каждую секунду двигаться на новую клетку. При движении надо занять новую клетку,
 * то есть tryLock() - если не получилось в течении 500 мс.,
 * то изменить движение на другую клетку.
 * 4. Избежать появление deadlock.
 * 5. Поле board при движении героя не должно блокироваться целиком, блокируется только ячейка.
 * 6. В коде использовать только immutable объекты, то есть все поля обозначить final.
 * <p>
 * Board.move(Cell source, Cell dist) : boolean - у вас все должен делать этот метод.
 */
public class GameBoard {

    private final ReentrantLock[][] board;

    public GameBoard(int x, int y) {
        this.board = new ReentrantLock[x][y];
    }

    /**
     * Если удаётся занять и заблокировать новую клетку (tryLock),
     * то снимает блокировку с той ячейки, откуда произошёл ход.
     *
     * @param source - текущая позиция
     * @param dist   - новая позиция
     * @return
     * @throws InterruptedException
     */
    public boolean move(Cell source, Cell dist) throws InterruptedException {
        boolean result = false;
        if (this.board[dist.getX()][dist.getY()].tryLock(500, TimeUnit.MILLISECONDS)) {
            try {
                result = true;
            } finally {
                this.board[source.getX()][source.getY()].unlock();
            }
        }
        return result;
    }

    /**
     * Получаем варианты возможных ходов.
     * Тут ход только по вертикали или горизонтали.
     *
     * @param source
     * @return
     */
    public List<Cell> getOptionsForMove(Cell source) {
        List<Cell> result = new ArrayList<>();
        int bufferX = source.getX();
        int bufferY = source.getY();
        if (bufferX + 1 < this.board.length) {
            result.add(new Cell(bufferX + 1, source.getY()));
        }
        if (bufferX - 1 > 0) {
            result.add(new Cell(bufferX - 1, source.getY()));
        }
        if (bufferY + 1 < this.board[bufferX].length) {
            result.add(new Cell(bufferX, bufferY + 1));
        }
        if (bufferY - 1 > 0) {
            result.add(new Cell(bufferX, bufferY - 1));
        }
        return result;
    }

    public ReentrantLock getCell(int x, int y) {
        return this.board[x][y];
    }
}

/**
 * Нить игрока.
 * Герой должен каждую секунду двигаться на новую клетку. При движении надо занять новую клетку,
 * то есть tryLock() - если не получилось в течении 500 мс.,
 * то изменить движение на другую клетку.
 */
class PlayerThread implements Runnable {

    private final GameBoard board;
    private final Cell start;

    /**
     * В конструкторе инициализируется поле board, стартовая позиция
     * и происходит блокировка стартовой позиции.
     *
     * @param board
     * @param start
     */
    PlayerThread(GameBoard board, Cell start) {
        this.board = board;
        this.start = start;
    }

    /**
     * В самом начале cоздаются нужные локальные переменные и запускается цикл.
     */
    @Override
    public void run() {
        boolean check;
        Cell current = this.start;
        List<Cell> forMove;
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Thread.sleep(1000);
                forMove = this.board.getOptionsForMove(current);
                for (Cell c : forMove) {
                    check = this.board.move(current, c);
                    if (check) {
                        current = c;
                        break;
                    }
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }
}