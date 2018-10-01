package ru.job4j.stringComparator;

import java.util.Comparator;

public class StringsCompare implements Comparator<String> {

    @Override
    public int compare(String left, String right) {
        int result = 0;
        int leftSize = left.length();
        int rightSize = right.length();
        /**
         * Переменная для цикла for.
         */
        int lowerSize = leftSize > rightSize ? rightSize : leftSize;
            for (int i = 0; i < lowerSize; i++) {
                result = Integer.compare(left.charAt(i), right.charAt(i));
                if (result != 0) {
                    break;
                }
            }
        /**
         * Если сравнение через цикл for дало 0 но строки не равны по длине.
         */
        if (result == 0 && leftSize != rightSize) {
                result = Integer.compare(leftSize, rightSize);
            }
        return result;
    }
}