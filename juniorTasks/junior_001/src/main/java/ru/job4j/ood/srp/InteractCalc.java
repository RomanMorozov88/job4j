package ru.job4j.ood.srp;

import java.io.InputStream;
import java.util.*;
import java.util.function.Consumer;

/**
 * Используя класс Calculator.
 * 1. Сделать класс InteractCalc.
 * 2. В классе должен быть пользовательский ввод.
 * 3. Повторный выбор операции и переиспользование предыдущего вычисления.
 * 4. Проект должен следовать SRP.
 */
public class InteractCalc {

    public Scanner scanner;
    private Consumer consumer;

    private BasicActionReader bar = new BasicActionReader();
    private TrigonometricActionReader trgar = new TrigonometricActionReader();

    /**
     * Конструктор.
     *
     * @param in       - как получаем информацию от пользователя.
     * @param consumer - как выводим информацию пользователю.
     */
    public InteractCalc(InputStream in, Consumer consumer) {
        this.scanner = new Scanner(in);
        this.consumer = consumer;
    }

    /**
     * Главный метод, запускающий цикл и проверяющий входящую от пользователя строку.
     */
    public void mainLoopForCalculate() {
        Double result = null;
        String workString;
        boolean exitCase;
        do {
            workString = this.scanner.nextLine();
            exitCase = bar.verifyIputExpression(workString)
                    || trgar.verifyIputExpression(workString);
            if (exitCase) {
                result = bar.checkAndRun(workString, result);
                result = trgar.checkAndRun(workString, result);
                this.consumer.accept(result);
            } else {
                this.consumer.accept("There is no mathematical expression.");
            }
        } while (exitCase);
    }
}
