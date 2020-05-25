package ru.job4j.additionaltasks.puzzle;

import java.util.*;

/**
 * Описание игрового поля.
 * Карта, содержащая в качестве ключа нужную позицию а в качестве значение-
 * список позиций, на которые можно переместиться из нужной позиции.
 */
public class GameField {

    private Map<Integer, List<Integer>> field = new HashMap<>();

    public GameField() {
        for (int i = 0; i < 8; i++) {
            field.put(i, new ArrayList<>());
        }
        field.get(0).addAll(Arrays.asList(1, 2));
        field.get(1).addAll(Arrays.asList(0, 2, 3));
        field.get(2).addAll(Arrays.asList(0, 1, 5));
        field.get(3).addAll(Arrays.asList(1, 4, 6));
        field.get(4).addAll(Arrays.asList(3, 5));
        field.get(5).addAll(Arrays.asList(2, 4, 7));
        field.get(6).addAll(Arrays.asList(3, 7));
        field.get(7).addAll(Arrays.asList(5, 6));
    }

    /**
     * Возвращаем список подходящих для хода из key позиций.
     * @param key
     * @return
     */
    public List<Integer> getForMove(int key) {
        return this.field.get(key);
    }
}
