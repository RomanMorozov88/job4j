package ru.job4j.tracker;

import java.util.List;

public class ValidateInput implements Input {

    private final Input input;

    public ValidateInput(final Input input) {
        this.input = input;
    }

    @Override
    public String ask(String question) {
        return this.input.ask(question);
    }

    public int ask(String question, int[] range) {
        boolean invalid = true;
        int value = -1;
        do {
            try {
                value = this.input.ask(question, range);
                invalid = false;
            } catch (MenuOutException inv1) {
                System.out.println("-Некорректо введён пункт меню.");
                System.out.println("-Введите целое число  " + range[0] + " до "
                        + range[range.length - 1] + ".");
            } catch (NumberFormatException inv2) {
                System.out.println("-Некорректо введён пункт меню.");
                System.out.println("-Введите целое число  " + range[0] + " до "
                        + range[range.length - 1] + ".");
            }
        } while (invalid) ;
            return value;
    }
}