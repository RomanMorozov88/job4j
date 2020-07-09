package ru.job4j.ood.srp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Тут указываем, что должны делать классы-наследники,
 * отвечающие за анализ входящей строки и вычисления.
 */
public abstract class ActionReader {

    /**
     * Получаем шаблон полного выражения.
     * @return
     */
    abstract String getGeneralPattern();

    /**
     * Получаем шаблон выражения с использованием предыдущего вычисления.
     * @return
     */
    abstract String getContinuePattern();

    /**
     * Получаем шаблон элемента(-ов), над которыми производим вычисления.
     * @return
     */
    abstract String getPatternMark();

    /**
     * Инициализируем карту, отвечающую за вызов нужного метода.
     */
    abstract void mapInit();

    /**
     * Производим вычисление "с нуля".
     * @param list
     * @return - результат вычисления.
     */
    abstract double calculateFromMap(List<String> list);

    /**
     * Производим вычисление с использованием
     * предыдущего вычисления.
     * @param list
     * @param prevResult - результат предыдущего вычисления.
     * @return
     */
    abstract double calculateFromMap(List<String> list, Double prevResult);

    /**
     * Проверяем, какой шаблон подходит и вызываем соответствующий метод.
     * @param inputString
     * @param prevResult
     * @return
     */
    public Double checkAndRun(String inputString, Double prevResult) {
        Double result = prevResult;
        List<String> calculateBricks = this.extractKeys(inputString);
        if (inputString.matches(this.getContinuePattern()) && result != null) {
            result = this.calculateFromMap(calculateBricks, prevResult);
        } else if (inputString.matches(this.getGeneralPattern())) {
            result = this.calculateFromMap(calculateBricks);
        }
        return result;
    }

    /**
     * Проверяем, что входящая строка подходит под один из шаблонов.
     * @param inputString
     * @return
     */
    public boolean verifyIputExpression(String inputString) {
        boolean result = false;
        if (inputString.matches(this.getGeneralPattern())
                || inputString.matches(this.getContinuePattern())) {
            result = true;
        }
        return result;
    }

    /**
     * Раскладываем входящую строку на необходимые элементы.
     * @param inputString
     * @return
     */
    public List<String> extractKeys(String inputString) {
        List<String> result = new ArrayList<>();
        Pattern pattern = Pattern.compile(this.getPatternMark());
        Matcher matcher = pattern.matcher(inputString);
        while (matcher.find()) {
            result.add(matcher.group().trim());
        }
        result.addAll(Arrays.asList(inputString.split(this.getPatternMark())));
        return result;
    }
}