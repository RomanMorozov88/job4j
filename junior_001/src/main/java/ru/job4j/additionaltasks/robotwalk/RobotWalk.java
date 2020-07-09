package ru.job4j.additionaltasks.robotwalk;

import java.util.*;

/**
 * Задан двухмерный массив. Массив заполнен нулями и единицами.
 * Робот может ходить только по единицам. Задана начальная и конечная точки.
 * Робот может ходить вверх, вниз, влево и вправо.
 * Необходимо найти минимальный путь до конечной точки.
 */
public class RobotWalk {

    /**
     * Обход по ширине.
     *
     * @param board массив, по которому идёт движение.
     * @param sx    Х координата стартовой точки.
     * @param sy    Y координата стартовой точки.
     * @param fx    X координата финишной точки.
     * @param fy    Y координата финишной точки.
     * @return расстояние от начальной точки до конечной.
     * Если координаты точек совпадают- вернёт 0.
     * Если до финишной точки не добраться
     * или значение board[fx][fy] является 0 - вернёт -1.
     */
    public int minWay(int[][] board, int sx, int sy, int fx, int fy) {
        int result = -1;
        if (board[fx][fy] == 0) {
            return result;
        }
        int boardLength = board.length;
        NodeForWalk buffer;
        int stepX;
        int stepY;
        int newWay;
        boolean[][] control = new boolean[boardLength][boardLength];
        Queue<NodeForWalk> search = new LinkedList<>(Arrays.asList(new NodeForWalk(sx, sy, 0)));
        while (!search.isEmpty()) {
            buffer = search.poll();
            stepX = buffer.getX();
            stepY = buffer.getY();
            newWay = buffer.getWay();
            control[stepX][stepY] = true;
            if (stepX == fx && stepY == fy) {
                return buffer.getWay();
            } else {
                newWay++;
                if (stepX + 1 < boardLength && board[stepX + 1][stepY] == 1 && !control[stepX + 1][stepY]) {
                    search.add(new NodeForWalk(stepX + 1, stepY, newWay));
                }
                if (stepX - 1 >= 0 && board[stepX - 1][stepY] == 1 && !control[stepX - 1][stepY]) {
                    search.add(new NodeForWalk(stepX - 1, stepY, newWay));
                }
                if (stepY + 1 < boardLength && board[stepX][stepY + 1] == 1 && !control[stepX][stepY + 1]) {
                    search.add(new NodeForWalk(stepX, stepY + 1, newWay));
                }
                if (stepY - 1 >= 0 && board[stepX][stepY - 1] == 1 && !control[stepX][stepY - 1]) {
                    search.add(new NodeForWalk(stepX, stepY - 1, newWay));
                }
            }
        }
        return result;
    }
}