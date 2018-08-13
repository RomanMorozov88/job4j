package ru.job4j.tracker;

import java.util.*;

/**
 * Поиск по id.
 */
class FindByIdAction implements UserAction {
    private int key;
    private String name;
    public FindByIdAction(int key, String name) {
        this.key = key;
        this.name = name;
    }
    @Override
    public int key() {
        return key;
    }
    @Override
    public void execute(Input input, Tracker tracker) {
        String id =  input.ask("Введите id нужной заявки: ");
        if (tracker.findById(id) != null) {
            System.out.println("------------------------------------");
            System.out.println("Имя: " + tracker.findById(id).getName());
            System.out.println("id: " + tracker.findById(id).getId());
            System.out.println("Текст: " + tracker.findById(id).getDescription());
            System.out.println("Время создания: " + tracker.findById(id).getCreate());
        } else {
            System.out.println("------------------------------------");
            System.out.println("Заявка с id " + id + " не найдена.");
            System.out.println("------------------------------------");
        }
    }
    @Override
    public String info() {
        return String.format("%d. %s", key, name);
    }
}

/**
 * Поиск по имени.
 */
class FindByNameAction implements UserAction {
    private int key;
    private String name;
    public FindByNameAction(int key, String name) {
        this.key = key;
        this.name = name;
    }
    @Override
    public int key() {
        return key;
    }
    @Override
    public void execute(Input input, Tracker tracker) {
        String name = input.ask("Введите имя нужной заявки: ");
        if (tracker.findByName(name).length > 0) {
            System.out.println("------------------------------------");
            System.out.println(String.format("Список заявок с именем %s:\n", name));
            for (Item item : tracker.findByName(name)) {
                System.out.println("Имя: " + item.getName());
                System.out.println("id: " + item.getId());
                System.out.println("------------------------------------");
            }
        } else {
            System.out.println("------------------------------------");
            System.out.println("Заявок с таким именем не найдено.");
            System.out.println("------------------------------------");
        }
    }
    @Override
    public String info() {
        return String.format("%d. %s", key, name);
    }
}

/**
 * Выход.
 */
class ExitAction implements UserAction {
    private StartUI ui;
    private int key;
    private String name;
    public ExitAction(int key, String name, StartUI ui) {
        this.key = key;
        this.name = name;
        this.ui = ui;
    }
    @Override
    public int key() {
        return key;
    }
    @Override
    public void execute(Input input, Tracker tracker) {
        this.ui.exitItem();
    }
    @Override
    public String info() {
        return String.format("%d. %s", key, name);
    }
}

public class MenuTracker {
    /**
     * @param хранит ссылку на объект .
     */
    private Input input;
    /**
     * @param хранит ссылку на объект .
     */
    private Tracker tracker;
    /**
     * @param хранит ссылку на массив типа UserAction.
     */
    private List<UserAction> actions = new ArrayList<>();

    /**
     * Конструктор.
     *
     * @param input   объект типа Input
     * @param tracker объект типа Tracker
     */
    public MenuTracker(Input input, Tracker tracker) {
        this.input = input;
        this.tracker = tracker;
    }

    /**
     * Метод для получения массива меню.
     *
     * @return длину массива
     */
    public int getActionsLentgh() {
        return this.actions.size();
    }

    /**
     * Метод заполняет массив.
     */
    public void fillActions(StartUI ui) {
        this.actions.add(this.new AddAction(0,"Добавить новую заявку."));
        this.actions.add(this.new FindAllAction(1, "Показать список всех заявок."));
        this.actions.add(new MenuTracker.EditAction(2,"Редактирование заявки."));
        this.actions.add(new MenuTracker.DeleteAction(3,"Удаление заявки."));
        this.actions.add(new FindByIdAction(4, "Поиск по id."));
        this.actions.add(new FindByNameAction(5, "Поиск заявки по имени."));
        this.actions.add(new ExitAction(6, "Выход.", ui));
    }

    /**
     * Метод в зависимости от указанного ключа, выполняет соотвествующие действие.
     *
     * @param key ключ операции
     */
    public void select(int key) {
        this.actions.get(key).execute(this.input, this.tracker);
    }

    /**
     * Метод выводит на экран меню.
     */
    public void show() {
        System.out.println("----------Меню----------");
        for (UserAction action : this.actions) {
            if (action != null) {
                System.out.println(action.info());
            }
        }
    }

    /**
     * Добавление новой заявки.
     */
    private class AddAction implements UserAction {
        private int key;
        private String name;
        public AddAction(int key, String name) {
            this.key = key;
            this.name = name;
        }
        @Override
        public int key() {
            return key;
        }
        @Override
        public void execute(Input input, Tracker tracker) {
            System.out.println("------------ Добавление новой заявки. --------------");
            String name = input.ask("Введите имя заявки:");
            String desc = input.ask("Введите текст заявки:");
            Item item = new Item(name, desc);
            tracker.add(item);
            System.out.println("Имя: " + item.getName());
            System.out.println("id: " + item.getId());
            System.out.println("Текст: " + item.getDescription());
        }
        @Override
        public String info() {
            return String.format("%d. %s", key, name);
        }
    }

    /**
     * Вывод имён и id всех имеющихся заявок.
     */
    public class FindAllAction implements UserAction {
        private int key;
        private String name;
        public FindAllAction(int key, String name) {
            this.key = key;
            this.name = name;
        }
        @Override
        public int key() {
            return key;
        }
        @Override
        public void execute(Input input, Tracker tracker) {
            if (tracker.findAll().length == 0) {
                System.out.println("------------ В хранилище нет ни одной заявки ------------");
            } else {
                System.out.println("------------ Список имён всех заявок. --------------");
                for (Item item : tracker.findAll()) {
                    System.out.println(String.format("Имя: %s", item.getName()));
                    System.out.println(String.format("id: %s", item.getId()));
                    System.out.println("------------------------------------");
                }
            }
        }
        @Override
        public String info() {
            return String.format("%d. %s", key, name);
        }
    }

    /**
     * Редактирование заявки.
     */
    private static class EditAction implements UserAction {
        private int key;
        private String name;
        public EditAction(int key, String name) {
            this.key = key;
            this.name = name;
        }
        @Override
        public int key() {
            return key;
        }
        @Override
        public void execute(Input input, Tracker tracker) {
           String id =  input.ask("Введите id нужной заявки: ");
           String name = input.ask("Введите новое имя: ");
           String desc = input.ask("Введите новый текст: ");
           Item item = new Item(name, desc);
           item.setId(id);
            if (tracker.replace(id, item)) {
                System.out.println("------------ Заявка отредактирована ------------");
            } else {
                System.out.println("------------------------------------");
                System.out.println("Заявка для редактирования с id " + id + " не найдена.");
                System.out.println("------------------------------------");
            }
        }
        @Override
        public String info() {
            return String.format("%d. %s", key, name);
        }
    }

    /**
     * Удаление заявки.
     */
    private static class DeleteAction implements UserAction {
        private int key;
        private String name;
        public DeleteAction(int key, String name) {
            this.key = key;
            this.name = name;
        }
        @Override
        public int key() {
            return key;
        }
        @Override
        public void execute(Input input, Tracker tracker) {
            String id = input.ask("Введите id нужной заявки: ");
            if (tracker.delete(id)) {
                System.out.println("------------ Заявка удалена ------------");
            } else {
                System.out.println("------------------------------------");
                System.out.println("Заявка с id " + id + " не найдена.");
                System.out.println("------------------------------------");
            }
        }
        @Override
        public String info() {
            return String.format("%d. %s", key, name);
        }
    }
}