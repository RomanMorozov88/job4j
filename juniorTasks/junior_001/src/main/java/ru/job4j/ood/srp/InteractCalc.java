package ru.job4j.ood.srp;

import java.io.InputStream;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Используя класс Calculator.
 * 1. Сделать класс InteractCalc.
 * 2. В классе должен быть пользовательский ввод.
 * 3. Повторный выбор операции и переиспользование предыдущего вычисления.
 * 4. Проект должен следовать SRP.
 */
public class InteractCalc {

    /**
     * Получаем информацию от пользователя.
     */
    public Scanner scanner;
    /**
     * ]
     * Выводим ответ пользователю.
     */
    private Consumer consumer;
    /**
     * Паттерны для работы с полученой от пользователя строкой.
     * patternMark вынесен в отдельное поле для того, что бы легче было
     * его редактировать при необходимости.
     */
    private String patternMark = "[\\+\\-\\/\\*]";
    private String generalPattern = "^\\d+(\\.\\d+)?\\s?" + this.patternMark + "\\s?\\d+(\\.\\d+)?$";
    private String continuePattern = this.patternMark + "\\s?\\d+(\\.\\d+)?$";
    /**
     * Карта, хранящая в себе ссылки на соответствующие математическим знакам
     * методы класса Calculator.
     */
    private Map<String, BiFunction<Double, Double, Double>> actionsMap = new HashMap<>();

    /**
     * Конструктор.
     *
     * @param in       - как получаем информацию от пользователя.
     * @param consumer - как выводим информацию пользователю.
     */
    public InteractCalc(InputStream in, Consumer consumer) {
        this.scanner = new Scanner(in);
        this.consumer = consumer;
        this.mapInit();
    }

    /**
     * Заполняем карту.
     */
    private void mapInit() {
        actionsMap.put("+", Calculator::add);
        actionsMap.put("-", Calculator::subtract);
        actionsMap.put("/", Calculator::div);
        actionsMap.put("*", Calculator::multiple);
    }

    /**
     * Главный метод, запускающий цикл и проверяющий входящую от пользователя строку.
     */
    public void mainLoopForCalculate() {
        Double result = null;
        String workString = this.scanner.nextLine();
        boolean exitCase = true;
        do {
            List<String> calculateBricks = this.extractKeys(workString);
            if (workString.matches(this.generalPattern)) {
                result = this.calculateFromMap(calculateBricks);
            } else if (workString.matches(this.continuePattern)) {
                result = this.calculateFromMap(calculateBricks, result);
            } else {
                this.consumer.accept("There is no mathematical expression.");
                exitCase = false;
            }
            if (result != null && exitCase) {
                this.consumer.accept(result);
                workString = this.scanner.nextLine();
            }
        } while (exitCase);
    }

    /**
     * Метод для вычисления "с нуля".
     *
     * @param stringArray
     * @return
     */
    private double calculateFromMap(List<String> stringArray) {
        String key = stringArray.get(0);
        Double a = Double.parseDouble(stringArray.get(1));
        Double b = Double.parseDouble(stringArray.get(2));
        return actionsMap.get(key).apply(a, b);
    }

    /**
     * Перегруженный метод для вычисления с использованием
     * результата предыдущего вычисления.
     *
     * @param string     - строка для анализа.
     * @param prevResult - результат вычисления.
     * @return
     */
    private double calculateFromMap(List<String> string, Double prevResult) {
        String key = string.get(0);
        Double b = Double.parseDouble(string.get(2));
        return actionsMap.get(key).apply(prevResult, b);
    }

    /**
     * Тут получаем из входящей строки все необходимые элементы в виде листа,
     * содержащего ТРИ строки.
     *
     * @param inputString
     * @return
     */
    private List<String> extractKeys(String inputString) {
        List<String> result = new ArrayList<>();
        Pattern pattern = Pattern.compile(this.patternMark);
        Matcher matcher = pattern.matcher(inputString);
        while (matcher.find()) {
            result.add(matcher.group().trim());
        }
        result.addAll(Arrays.asList(inputString.split(this.patternMark)));
        return result;
    }
}
