package ru.job4j.additionaltasks.puzzle;

import java.util.*;

/**
 * Головоломка, в которой цифры от 1 до 7 располагаются в ячейках, а одна из ячеек свободна.
 * Она используется для проверки интеллекта роботов.
 * Для ее решения необходимо переставить цифры, используя свободную ячейку,
 * чтобы цифры расположились так же, как в массиве finalCondition.
 * Рисунок: Puzzle.png в папке resources.
 * Перемещение цифры в свободную ячейку возможно, только если ячейки соединены линией.
 */
public class PuzzleRun implements PuzzleResolver {

    private int[] finalCondition = new int[]{1, 2, 3, 4, 0, 5, 6, 7};
    private GameField cells = new GameField();

    /**
     * Возвращает подходящий для решения путь.
     * Где значения массива- не позиции, по которым прошли,
     * а значений, которые перемещали.
     *
     * @param start - стартовое состояние.
     * @return
     */
    @Override
    public int[] resolve(int[] start) {
        return this.throughTheQueue(start);
    }

    /**
     * Обход в ширину.
     * Поиск по первому совпадению.
     *
     * @param state
     * @return
     */
    private int[] throughTheQueue(int[] state) {
        int[] result = new int[0];
        Queue<PuzzleNode> wayQueue = new LinkedList<>(
                Arrays.asList(new PuzzleNode(state, new ArrayList<>())));
        List<int[]> cameFrom = new ArrayList<>();
        cameFrom.add(Arrays.copyOf(state, state.length));
        PuzzleNode bufferNode;
        List<Integer> bufferPath;
        while (!wayQueue.isEmpty()) {
            bufferNode = wayQueue.poll();
            int[] bufferState = bufferNode.getState();
            if (Arrays.equals(bufferState, finalCondition)) {
                bufferPath = bufferNode.getPath();
                result = new int[bufferPath.size()];
                for (int i = 0; i < result.length; i++) {
                    result[i] = bufferPath.get(i);
                }
                break;
            } else {
                int zero = this.getZeroPosition(bufferState);
                List<Integer> neighbors = this.cells.getForMove(zero);
                for (Integer i : neighbors) {
                    int[] newState = this.oneStep(zero, i, bufferState);
                    if (!wasHere(cameFrom, newState)) {
                        bufferPath = new ArrayList<>();
                        bufferPath.addAll(bufferNode.getPath());
                        bufferPath.add(bufferState[i]);
                        PuzzleNode intoWayQueue = new PuzzleNode(newState, bufferPath);
                        wayQueue.add(intoWayQueue);
                        cameFrom.add(Arrays.copyOf(newState, newState.length));
                    }
                }
            }
        }
        return result;
    }

    /**
     * Возвращает индекс Нуля в текущем состоянии.
     *
     * @param state - текущее состояние
     * @return
     */
    private int getZeroPosition(int[] state) {
        int result = -1;
        for (int i = 0; i < state.length; i++) {
            if (state[i] == 0) {
                result = i;
            }
        }
        return result;
    }

    /**
     * Ход.
     *
     * @param from  - откуда перемещаемся.
     * @param to    - куда перемещаемся.
     * @param state - новое состояние.
     * @return
     */
    private int[] oneStep(int from, int to, int[] state) {
        int[] result = Arrays.copyOf(state, state.length);
        int buffer = result[from];
        result[from] = result[to];
        result[to] = buffer;
        return result;
    }

    /**
     * Проверяет, встречалось ли уже состояние
     * идентичное текущему.
     *
     * @param list  - список уже посещённых состояний.
     * @param state - текущее состояние.
     * @return
     */
    private boolean wasHere(List<int[]> list, int[] state) {
        boolean result = false;
        for (int[] array : list) {
            if (Arrays.equals(array, state)) {
                result = true;
                break;
            }
        }
        return result;
    }
}
