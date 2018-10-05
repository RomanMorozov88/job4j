package ru.job4j.bankaccounts.menu;

public class IncorrectDataException extends RuntimeException {
    public IncorrectDataException(String msg) {
        super(msg);
    }
}