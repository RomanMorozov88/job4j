package ru.job4j.calculatefunctions;

import java.util.*;
import java.util.function.Function;

public class CalculateFunc {

    List<Double> diapason(int start, int end, Function<Double, Double> func) {
        List<Double> result = new ArrayList<>();
        for (double i = start; i <= end; i++) {
            result.add(func.apply(i));
        }
        return result;
    }
}