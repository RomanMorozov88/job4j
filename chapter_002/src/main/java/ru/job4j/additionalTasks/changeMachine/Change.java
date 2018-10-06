package ru.job4j.additionalTasks.changeMachine;

import java.util.*;

public class Change {
    /**
     * Список купюр и монет.
     */
    private List<Integer> money = Arrays.asList(1, 2, 5, 10, 50, 100);

    public int[] changes(int value, int price) {
        List<Integer> listResult = new ArrayList<>();
        int temp = value - price;
        if (temp == 0) {
            listResult.add(0);
        } else if (temp < 0) {
            listResult.add(-1);
        }
        while (temp > 0) {
            for (int i = money.size() - 1; i >= 0; i--) {
                if (temp >= money.get(i)) {
                    listResult.add(money.get(i));
                    temp -= money.get(i);
                    break;
                }
            }
        }
        int[] result = new int[listResult.size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = listResult.get(i);
        }
        return result;
    }
}