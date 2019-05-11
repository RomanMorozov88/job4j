package ru.job4j.ood.srp;

import java.util.*;
import java.util.function.BiFunction;

/**
 * Базовые арифметические вычисления.
 */
public class BasicActionReader extends ActionReader {

    private String patternMark = "[\\+\\-\\/\\*]";
    private String generalPattern = "^\\d+(\\.\\d+)?\\s?" + this.patternMark + "\\s?\\d+(\\.\\d+)?$";
    private String continuePattern = "^\\s?" + this.patternMark + "\\s?\\d+(\\.\\d+)?$";
    private Map<String, BiFunction<Double, Double, Double>> basicActionsMap = new HashMap<>();

    public BasicActionReader() {
        this.mapInit();
    }

    @Override
    String getPatternMark() {
        return this.patternMark;
    }

    @Override
    String getGeneralPattern() {
        return this.generalPattern;
    }

    @Override
    String getContinuePattern() {
        return this.continuePattern;
    }

    @Override
    void mapInit() {
        basicActionsMap.put("+", Calculator::add);
        basicActionsMap.put("-", Calculator::subtract);
        basicActionsMap.put("/", Calculator::div);
        basicActionsMap.put("*", Calculator::multiple);
    }

    @Override
    public double calculateFromMap(List<String> list) {
        String key = list.get(0);
        Double a = Double.parseDouble(list.get(1));
        Double b = Double.parseDouble(list.get(2));
        return basicActionsMap.get(key).apply(a, b);
    }

    @Override
    public double calculateFromMap(List<String> string, Double prevResult) {
        String key = string.get(0);
        Double b = Double.parseDouble(string.get(2));
        return basicActionsMap.get(key).apply(prevResult, b);
    }
}
