package ru.job4j.bankaccounts.menu;

import ru.job4j.bankaccounts.UsersMap;

public class StartUI {
    private final Input input;
    private final UsersMap usersmap;
    private boolean stop = true;

    public StartUI(Input input, UsersMap usermap) {
        this.input = input;
        this.usersmap = usermap;
    }

    public void init() {
        MenuMap menu = new MenuMap(this.input, this.usersmap);
        menu.fillActions(this);
        menu.setRange();
        do {
            menu.show();
            menu.select(input.ask("Выберите нужный пункт меню: ", menu.range));
        } while (this.stop);
    }

    public void exitItem() {
        this.stop = false;
    }

    public static void main(String[] args) {
        new StartUI(
                new ValidateInput(
                        new ConsoleInput()
                ),
                new UsersMap()
        ).init();
    }
}
