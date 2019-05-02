package ru.job4j.ood.srp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * Базовые тригонометрические вычисления и квадратный корень.
 */
public class TrigonometricActionReader extends ActionReader {

    private String patternMark = "(?i)(cos|sin|tan|cotan|sqrt)";
    private String generalPattern = "^\\s?" + this.patternMark + "\\s?\\d+(\\.\\d+)?$";
    private String continuePattern = "^\\s?" + this.patternMark + "$";
    private Map<String, Function<Double, Double>> basicActionsMap = new HashMap<>();

    public TrigonometricActionReader() {
        this.mapInit();
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
    String getPatternMark() {
        return this.patternMark;
    }

    @Override
    void mapInit() {
        basicActionsMap.put("cos", Calculator::cosinus);
        basicActionsMap.put("sin", Calculator::sinus);
        basicActionsMap.put("tan", Calculator::tangents);
        basicActionsMap.put("cotan", Calculator::cotangents);
        basicActionsMap.put("sqrt", Calculator::squareroot);
    }

    @Override
    double calculateFromMap(List<String> list) {
        String key = list.get(0);
        Double a = Double.parseDouble(list.get(2));
        return basicActionsMap.get(key).apply(a);
    }

    @Override
    double calculateFromMap(List<String> list, Double prevResult) {
        String key = list.get(0);
        return basicActionsMap.get(key).apply(prevResult);
    }
}
