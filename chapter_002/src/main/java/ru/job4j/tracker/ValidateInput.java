package ru.job4j.tracker;

import java.util.List;

public class ValidateInput extends ConsoleInput {

    private final Input input;

    public ValidateInput(final Input input) {
        this.input = input;
    }

    @Override
    public String ask(String question) {
        return this.input.ask(question);
    }

    public int ask(String question, List<Integer> range) {
        boolean invalid = true;
        int value = -1;
        do {
            try {
                value = this.input.ask(question, range);
                invalid = false;
            } catch (MenuOutException inv1) {
                System.out.println("-Некорректо введён пункт меню.");
                System.out.println("-Введите целое число  " + range.get(0) + " до "
                        + range.get(range.size() - 1) + ".");
            } catch (NumberFormatException inv2) {
                System.out.println("-Некорректо введён пункт меню.");
                System.out.println("-Введите целое число  " + range.get(0) + " до "
                        + range.get(range.size() - 1) + ".");
            }
        } while (invalid) ;
            return value;
    }
}