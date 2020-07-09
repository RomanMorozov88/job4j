package ru.job4j.ood.menu;

public class SimpleAction implements Action {
    @Override
    public String someMethod(String inputString) {
        return "Some simple action of " + inputString;
    }
}
