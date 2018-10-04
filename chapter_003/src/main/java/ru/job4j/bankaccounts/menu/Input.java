package ru.job4j.bankaccounts.menu;

import java.util.List;

public interface Input {
    String ask(String question);

    int ask(String question, List<Integer> range);

}