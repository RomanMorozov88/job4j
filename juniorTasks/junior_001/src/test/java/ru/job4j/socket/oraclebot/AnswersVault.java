package ru.job4j.socket.oraclebot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Хранилище ответов, которые использует серверная часть. Просто для примера.
 */
public class AnswersVault {

    List<String> vault = new ArrayList<>(Arrays.asList("Yes.", "No.", "I don`t know."));

    /**
     * Возвращает случайный ответ из листа.
     * @return
     */
    public String getAnswer() {
        int index = (int) (Math.random() * vault.size());
        return vault.get(index);
    }
}