package ru.job4j.bomberman;

import java.util.List;

/**
 * Чудовища должны двигаться автоматически.
 * 6. Предусмотреть, что если чудовище не может двинуться на клетку, например,
 * там стоит другое чудовище, проверять в течении 0.5 сек. и двигаться в другую строну.
 */
public class MonsterThread extends Thread {

    private final GameBoard board;
    private final Cell start;

    public MonsterThread(GameBoard board, Cell start) {
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
