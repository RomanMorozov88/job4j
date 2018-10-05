package ru.job4j.bankaccounts.menu;

import ru.job4j.bankaccounts.*;
import java.util.*;

public class MenuMap {

    private Input input;
    private UsersMap usermap;
    private List<UserAction> actions = new ArrayList<>();
    public List<Integer> range = new ArrayList<>();

    public MenuMap(Input input, UsersMap usermap) {
        this.input = input;
        this.usermap = usermap;
    }

    public void setRange() {
        for (UserAction i : actions) {
            range.add(i.key());
        }
    }

    public void fillActions(StartUI ui) {
        this.actions.add(this.new ShowUserAction(0, "Показать список пользователей."));
        this.actions.add(this.new AddUserAction(1, "Добавить нового пользователя."));
        this.actions.add(this.new DeleteUserAction(2, "Удалить пользователя."));
        this.actions.add(this.new AddAccountAction(3, "Добавить счёт."));
        this.actions.add(this.new DeleteAccountAction(4, "Удалить счёт."));
        this.actions.add(this.new ShowAllAction(5, "Показать все счета пользователя."));
        this.actions.add(this.new ToOtherAccountAction(6, "Сделать перевод между счетами одного пользователя."));
        this.actions.add(this.new ToOtherUserAction(7, "Сделать перевод другому пользователю."));
        this.actions.add(this.new ExitAction(8, "Выход.", ui));
    }

    public void select(int key) {
        this.actions.get(key).execute(this.input, this.usermap);
    }

    public void show() {
        System.out.println("----------Меню----------");
        for (UserAction action : this.actions) {
            if (action != null) {
                System.out.println(action.info());
            }
        }
    }
    private class AddUserAction extends BaseAction {
        public AddUserAction(int key, String name) {
            super(key, name);
        }
        @Override
        public void execute(Input input, UsersMap usermap) {
            System.out.println("------------ Добавление нового пользователя. --------------");
            String name = input.ask("Введите имя пользователя:");
            String passport = input.ask("Введите паспорт:");
            User user = new User(name, passport);
            usermap.addUser(user);
            System.out.println("Имя: " + user.getName());
            System.out.println("Паспорт: " + user.getPassport());
            System.out.println("Счетов у пользователя пока нет.");
        }
    }
    private class DeleteUserAction extends BaseAction {
        public DeleteUserAction(int key, String name) {
            super(key, name);
        }
        @Override
        public void execute(Input input, UsersMap usermap) {
            System.out.println("------------ Удаление пользователя. --------------");
            String name = input.ask("Введите имя пользователя:");
            String passport = input.ask("Введите паспорт:");
            if (usermap.deleteUser(new User(name, passport))) {
                System.out.println("------------ Пользователь удалён. --------------");
            } else {
                System.out.println("------------ Пользователь не найден. --------------");
            }
        }
    }
    private class ShowUserAction extends BaseAction {
        public ShowUserAction(int key, String name) {
            super(key, name);
        }
        @Override
        public void execute(Input input, UsersMap usermap) {
            System.out.println("------------ Список пользователей. --------------");
            if (usermap.getUsers().size() != 0) {
                for (User u : usermap.getUsers()) {
                    System.out.println(u.getName());
                }
                System.out.println("-----------------------------------------");
            } else {
                System.out.println("Не зарегестрировано ни одного пользователя.");
            }
        }
    }
    private class AddAccountAction extends BaseAction {
        public AddAccountAction(int key, String name) {
            super(key, name);
        }
        @Override
        public void execute(Input input, UsersMap usermap) {
            try {
                System.out.println("------------ Добавление нового счёта. --------------");
                String passport = input.ask("Введите паспорт:");
                if (usermap.findUser(passport) != null) {
                    double value = Double.valueOf(input.ask("Введите сумму:"));
                    usermap.addAccountToUser(passport, new Account(value));
                    User user = usermap.findUser(passport);
                    System.out.println("Имя: " + user.getName());
                    System.out.println("Паспорт: " + user.getPassport());
                    System.out.println("Счета пользователя:");
                    for (Account a : usermap.getUserAccounts(passport)) {
                        System.out.println("-----------------------------------------");
                        System.out.println("Реквезиты: " + a.getRequisites());
                        System.out.println("Баланс: " + a.getValue());
                    }
                } else {
                    System.out.println("------------ Пользователь не найден. --------------");
                }
            } catch (NumberFormatException e) {
                System.out.println("Неверно введена сумма.");
            }
        }
    }
    private class DeleteAccountAction extends BaseAction {
        public DeleteAccountAction(int key, String name) {
            super(key, name);
        }
        @Override
        public void execute(Input input, UsersMap usermap) {
            try {
                System.out.println("------------ Удаление счёта. --------------");
                String passport = input.ask("Введите паспорт:");
                int requisite = Integer.valueOf(input.ask("Введите реквизиты:"));
                if (usermap.deleteAccountFromUser(passport, requisite)) {
                    System.out.println("------------ Счёт удалён. --------------");
                } else {
                    System.out.println("------------ Счёт не найден. --------------");
                }
            } catch (NumberFormatException e) {
            System.out.println("Неверно введены реквизиты.");
            }
        }
    }
    private class ToOtherAccountAction extends BaseAction {
        public ToOtherAccountAction(int key, String name) {
            super(key, name);
        }

        @Override
        public void execute(Input input, UsersMap usermap) {
            try {
                String passport = input.ask("Введите паспорт:");
                User user = usermap.findUser(passport);
                if (user != null) {
                    int outreq = Integer.valueOf(input.ask("Введите счёт списания:"));
                    int inreq = Integer.valueOf(input.ask("Введите счёт зачисления:"));
                    double value = Double.valueOf(input.ask("Введите сумму перевода:"));
                    if (usermap.transferMoney(passport, outreq, inreq, value)) {
                        System.out.println("Перевод выполнен.");
                    } else {
                        System.out.println("Недостаточно средств для осуществления операции.");
                    }
                } else {
                    System.out.println("------------ Пользователь не найден. --------------");
                }
            } catch (IncorrectDataException inv) {
                System.out.println("       **********");
                System.out.println("Неверно введены данные.");
            }
        }
    }
    private class ToOtherUserAction extends BaseAction {
        public ToOtherUserAction(int key, String name) {
            super(key, name);
        }

        @Override
        public void execute(Input input, UsersMap usermap) {
            try {
                String passportOut = input.ask("Введите паспорт отправителя:");
                String passportIn = input.ask("Введите паспорт получателя:");
                User userOut = usermap.findUser(passportOut);
                User userIn = usermap.findUser(passportIn);
                if (userOut != null && userIn != null) {
                    int reqOut = Integer.valueOf(input.ask("Введите счёт списания:"));
                    int reqIn = Integer.valueOf(input.ask("Введите счёт зачисления:"));
                    double value = Double.valueOf(input.ask("Введите сумму перевода:"));
                    if (usermap.transferMoney(passportOut, reqOut, passportIn, reqIn, value)) {
                        System.out.println("Перевод выполнен.");
                    } else {
                        System.out.println("Недостаточно средств для осуществления операции.");
                    }
                } else {
                    System.out.println("------------ Пользователь не найден. --------------");
                }
            } catch (IncorrectDataException inv) {
                System.out.println("       **********");
                System.out.println("Неверно введены данные.");
            }
        }
    }
    private class ShowAllAction extends BaseAction {
        public ShowAllAction(int key, String name) {
            super(key, name);
        }

        @Override
        public void execute(Input input, UsersMap usermap) {
            String passport = input.ask("Введите паспорт:");
            User user = usermap.findUser(passport);
            if (user != null) {
                System.out.println("------------ Список счетов пользователя"
                        + user.getName() + "--------------");
                List<Account> list = usermap.getUserAccounts(passport);
                if (list.size() != 0) {
                    for (Account a : usermap.getUserAccounts(passport)) {
                        System.out.println("-----------------------------------------");
                        System.out.println("Реквизиты: " + a.getRequisites());
                        System.out.println("Баланс: " + a.getValue());
                    }
                    System.out.println("-----------------------------------------");
                } else {
                    System.out.println("У пользователя нет счетов.");
                }
            } else {
                System.out.println("------------ Пользователь не найден. --------------");
            }
        }
    }
    private class ExitAction extends BaseAction {
        private StartUI ui;
        public ExitAction(int key, String name, StartUI ui) {
            super(key, name);
            this.ui = ui;
        }
        @Override
        public void execute(Input input, UsersMap usermap) {
            this.ui.exitItem();
        }
    }
}