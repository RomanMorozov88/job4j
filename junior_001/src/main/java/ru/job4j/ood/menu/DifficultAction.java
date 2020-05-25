package ru.job4j.ood.menu;

public class DifficultAction implements Action {
    @Override
    public String someMethod(String inputString) {
        return "Some difficult action of " + inputString;
    }
}
