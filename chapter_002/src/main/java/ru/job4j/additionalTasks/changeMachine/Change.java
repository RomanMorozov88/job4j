package ru.job4j.additionalTasks.changeMachine;

import java.util.*;

public class Change {
    /**
     * Список купюр.
     */
    private List<Integer> banknotes = Arrays.asList(50, 100);
    /**
     * Список монет.
     */
    private List<Integer> coins = Arrays.asList(1, 2, 5, 10);

    public int[] changes(int value, int price) {
        /**
         * Список для использования внутри метода, так как мы не знаем длину выходящего массива.
         */
        List<Integer> result01 = new ArrayList<>();
        int temp = value - price;
        /**
         * Если сдачи не будет(можно накинуть исключение с выводом соответствующего сообщения).
         */
        if (temp == 0) {
            result01.add(0);
        }
        if (temp >= 0 && this.banknotes.contains(value)) {
            for (Integer i : this.banknotes) {
                if (i <= temp) {
                    result01.add(i);
                    temp -=i;
                    break;
                }
            }
            /**
             * Монеты перебираем от большей к меньшей.
             */
            for (int i = this.coins.size() - 1; i >= 0; i--) {
                if (this.coins.get(i) <= temp) {
                    result01.add(this.coins.get(i));
                    temp -=this.coins.get(i);
                }
            }
            /**
             * else если купюры со значение value или value меньше price.
             * так же можно накинуть исключение с выводом соответствующего сообщения.
             */
        } else result01.add(-1);
        /**
         * Конвертация списка в массив.
         */
        int[] result = new int[result01.size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = result01.get(i);
        }
        return result;
    }
}