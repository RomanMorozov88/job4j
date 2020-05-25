package ru.job4j.additionaltasks.games.tictac;

import ru.job4j.additionaltasks.games.tictac.players.Player;
import ru.job4j.additionaltasks.games.tictac.players.AlivePlayer;
import ru.job4j.additionaltasks.games.tictac.players.SillyRobotPlayer;

import java.util.Scanner;

/**
 * Игровая логика и все необходимые объекты(игроки)
 */
public class TicTacGameLogic {

    private Player player01;
    private Player player02;
    private String emptyMark = " * ";

    private static final int DEFAULTVICTORYCONDITION = 3;
    private int size;
    private String[][] gameField;
    private int victoryCondition;

    private Scanner scanner;


    /**
     * Создаём игру по умолчанию с полем 3х3,
     * победная комбинация- 3,
     * с игроком-пользователем (Player 1, метка О)
     * и игроком ботом (Player 2, метка Х).
     */
    public TicTacGameLogic(Scanner scanner) {
        this.scanner = scanner;
        this.size = 4;
        this.victoryCondition = DEFAULTVICTORYCONDITION;
        this.player01 = new AlivePlayer("Player 1", "X", this.scanner);
        this.player02 = new SillyRobotPlayer("Player 2", "O", this.size);
    }

    /**
     * Проверяем наличие победной комбинации по нужному направлению.
     *
     * @param mark   - метка, по которой проверяем.
     * @param startX - координата по X, от которой начинаем проверку.
     * @param startY - координата по Y, от которой начинаем проверку.
     * @param deltaX - направление по X
     * @param deltaY - направление по Y
     * @return
     */
    private boolean fillBy(String mark, int startX, int startY, int deltaX, int deltaY) {
        boolean result = false;
        int count = 0;
        for (int i = 0; i < this.victoryCondition; i++) {
            String cell = this.gameField[startX][startY];
            startX += deltaX;
            startY += deltaY;
            if (!cell.equals(mark)) {
                count = 0;
            }
            count++;
            if (startX >= this.size || startY >= this.size
                    || startX <= 0 || startY <= 0) {
                break;
            }
        }
        if (count == this.victoryCondition) {
            result = true;
        }
        return result;
    }

    /**
     * Проверяем наличие победной комбинации на всём поле.
     *
     * @param mark - метк, по которой проверяем.
     * @return
     */
    public boolean win(String mark) {
        boolean result = false;
        for (int i = 1; i < this.size; i++) {
            for (int j = 1; j < this.size; j++) {
                if (this.gameField[i][j].equals(mark)) {
                    result = this.fillBy(mark, i, j, 1, 0)
                            || this.fillBy(mark, i, j, 0, 1)
                            || this.fillBy(mark, i, j, 1, 1)
                            || this.fillBy(mark, i, j, -1, 1);
                    if (result) {
                        return result;
                    }
                }
            }
        }
        return result;
    }

    /**
     * Устанавливаем первого игрока.
     *
     * @param player01
     */
    public void setPlayer01(Player player01) {
        this.player01 = player01;
    }

    /**
     * Устанавливаем второго игрока.
     *
     * @param player02
     */
    public void setPlayer02(Player player02) {
        this.player02 = player02;
    }

    /**
     * Устанавливаем размер игрового поля.
     *
     * @param size
     */
    public void setSize(int size) {
        this.size = size + 1;
    }

    /**
     * Устанавливаем условие победы.
     *
     * @param victoryCondition
     */
    public void setVictoryCondition(int victoryCondition) {
        this.victoryCondition = victoryCondition;
    }

    /**
     * Сбрасываем значение условия победы.
     * Нужно при изменении размера игрового поля.
     */
    public void resetVictoryCondition() {
        this.victoryCondition = DEFAULTVICTORYCONDITION;
    }

    /**
     * Заполняем игровое поле.
     */
    public void initGameField() {
        this.gameField = new String[this.size][this.size];
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                if (i == 0) {
                    this.gameField[i][j] = " " + j + " ";
                } else if (j == 0) {
                    this.gameField[i][j] = " " + i + " ";
                } else {
                    this.gameField[i][j] = this.emptyMark;
                }
            }
        }
    }

    public int getSize() {
        return size;
    }

    public String[][] getGameField() {
        return this.gameField;
    }

    public Player getPlayer01() {
        return this.player01;
    }

    public Player getPlayer02() {
        return this.player02;
    }

    /**
     * Проверяем- не занята ли ячейка по координатам x, y.
     */
    public boolean checkCell(int x, int y) {
        return this.gameField[x][y].equals(this.emptyMark);
    }

    /**
     * Проверяем есть ли на игровом поле пустые ячейки.
     */
    public boolean checkForEmptyCell() {
        boolean result = false;
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                if (this.gameField[i][j].equals(this.emptyMark)) {
                    result = true;
                    break;
                }
            }
        }
        return result;
    }

    /**
     * Получаем ход от игрока.
     *
     * @param player
     */
    public boolean getMove(Player player) {
        boolean result;
        Integer[] buffer = player.move();
        Integer x = buffer[0];
        Integer y = buffer[1];
        if (x != null && y != null
                && x < this.size && y < this.size
                && x > 0 && y > 0
                && this.gameField[x][y].equals(this.emptyMark)) {
            this.gameField[x][y] = player.getMark();
            result = true;
        } else {
            result = false;
        }
        return result;
    }
}