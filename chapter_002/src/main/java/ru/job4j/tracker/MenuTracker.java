package ru.job4j.tracker;

import java.util.*;

class FindByIdAction implements UserAction {
    @Override
    public int key() {
        return 4;
    }
    @Override
    public void execute(Input input, Tracker tracker) {
        String id =  input.ask("Введите id нужной заявки: ");
        if (tracker.findById(id) != null) {
            System.out.println("-------------------------");
            System.out.println("имя: " + tracker.findById(id).getName());
            System.out.println("id: " + tracker.findById(id).getId());
            System.out.println("текст: " + tracker.findById(id).getDescription());
            System.out.println("время создания: " + tracker.findById(id).getCreate());
        } else {
            System.out.println("Заявка с id " + id + " не найдена.");
        }
    }
    @Override
    public String info() {
        return "4. Поиск заявки по id.";
    }
}
class FindByNameAction implements UserAction {
    @Override
    public int key() {
        return 5;
    }
    @Override
    public void execute(Input input, Tracker tracker) {
        String name = input.ask("Введите имя нужной заявки: ");
        if (tracker.findByName(name).length > 0) {
            System.out.println(String.format("Список заявок с именем %s:", name));
            for (Item item : tracker.findByName(name)) {
                System.out.println("-------------------------");
                System.out.println(String.format(
                        "Имя: %s \n id: %s", item.getName(), item.getId())
                );
            }
        } else {
            System.out.println("Заявок с таким именем не найдено.");
        }
    }
    @Override
    public String info() {
        return "5. Поиск заявки по имени.";
    }
}
class ExitAction implements UserAction {
    @Override
    public int key() {
        return 6;
    }
    @Override
    public void execute(Input input, Tracker tracker) {
        System.exit(0);
    }
    @Override
    public String info() {
        return "6. Выход.";
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
    public void fillActions() {
        this.actions.add(this.new AddAction());
        this.actions.add(this.new FindAllAction());
        this.actions.add(new MenuTracker.EditAction());
        this.actions.add(new MenuTracker.DeleteAction());
        this.actions.add(new FindByIdAction());
        this.actions.add(new FindByNameAction());
        this.actions.add(new ExitAction());
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
        for (UserAction action : this.actions) {
            if (action != null) {
                System.out.println(action.info());
            }
        }
    }

    private class AddAction implements UserAction {
        @Override
        public int key() {
            return 0;
        }
        @Override
        public void execute(Input input, Tracker tracker) {
            System.out.println("------------ Добавление новой заявки. --------------");
            String name = input.ask("Введите имя заявки:");
            String desc = input.ask("Введите текст заявки:");
            Item item = new Item(name, desc);
            tracker.add(item);
            System.out.println("------------ Имя : " + item.getName());
            System.out.println("------------ Id : " + item.getId());
            System.out.println("------------ Текст : " + item.getDescription());
        }
        @Override
        public String info() {
            return "0. Добавление новой заявки.";
        }
    }

    public class FindAllAction implements UserAction {
        @Override
        public int key() {
            return 1;
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
                    System.out.println("-----------------------------------");
                }
            }
        }
        @Override
        public String info() {
            return "1. Показать все существующие заявки.";
        }
    }

    private static class EditAction implements UserAction {
        @Override
        public int key() {
            return 2;
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
                System.out.println("Заявка для редактирования с id " + id + " не найдена.");
            }
        }
        @Override
        public String info() {
            return "2. Редактирование заявки.";
        }
    }

    private static class DeleteAction implements UserAction {
        @Override
        public int key() {
            return 3;
        }
        @Override
        public void execute(Input input, Tracker tracker) {
            String id = input.ask("Введите id нужной заявки: ");
            if (tracker.delete(id)) {
                System.out.println("------------ Заявка удалена ------------");
            } else {
                System.out.println("Заявка с id " + id + " не найдена.");
            }
        }
        @Override
        public String info() {
            return "3. Удаление заявки.";
        }
    }
}