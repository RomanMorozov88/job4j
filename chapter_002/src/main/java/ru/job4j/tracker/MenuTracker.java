package ru.job4j.tracker;

import java.util.*;
import java.util.function.Consumer;

/**
 * Поиск по id.
 */
class FindByIdAction extends BaseAction {
    public FindByIdAction(int key, String name) {
        super(key, name);
    }

    @Override
    public void execute(Input input, Tracker tracker) {
        String id = input.ask("Введите id нужной заявки: ");
        if (tracker.find(item -> item.getId().equals(id),
                item -> {
                    MenuTracker.infoNameId(item, System.out::println);
                    MenuTracker.infoDescCreate(item, System.out::println);
                })) {
            System.out.println("------------------------------------");
        } else {
            System.out.println("------------------------------------");
            System.out.println("Заявка с id " + id + " не найдена.");
            System.out.println("------------------------------------");
        }
    }
}

/**
 * Поиск по имени.
 */
class FindByNameAction extends BaseAction {
    public FindByNameAction(int key, String name) {
        super(key, name);
    }

    @Override
    public void execute(Input input, Tracker tracker) {
        String name = input.ask("Введите имя нужной заявки: ");
        if (tracker.find(item -> item.getName().equals(name),
                item -> MenuTracker.infoNameId(item, System.out::println))) {
        } else {
            System.out.println("------------------------------------");
            System.out.println("Заявок с таким именем не найдено.");
            System.out.println("------------------------------------");
        }
    }
}

/**
 * Выход.
 */
class ExitAction extends BaseAction {
    private StartUI ui;

    public ExitAction(int key, String name, StartUI ui) {
        super(key, name);
        this.ui = ui;
    }

    @Override
    public void execute(Input input, Tracker tracker) {
        this.ui.exitItem();
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
     * @param хранит ссылку на массив типа Integer, содержащий ключи действий.
     */
    public List<Integer> range = new ArrayList<>();

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
     * Метод заполняет массив ключами действий.
     */
    public void setRange() {
        for (UserAction i : actions) {
            range.add(i.key());
        }
    }

    /**
     * Вывод имени и id искомой заявки.
     * @param item
     * @param consumer
     */
    public static void infoNameId(Item item, Consumer consumer) {
        consumer.accept("Имя: " + item.getName());
        consumer.accept("id: " + item.getId());
        consumer.accept("------------------------------------");
    }

    /**
     * Вывод описания и времени создания искомой заявки.
     * @param item
     * @param consumer
     */
    public static void infoDescCreate(Item item, Consumer consumer) {
        consumer.accept("Текст: " + item.getDescription());
        consumer.accept("Время создания: " + item.getCreate());
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
        this.actions.add(this.new AddAction(0, "Добавить новую заявку."));
        this.actions.add(this.new FindAllAction(1, "Показать список всех заявок."));
        this.actions.add(new MenuTracker.EditAction(2, "Редактирование заявки."));
        this.actions.add(new MenuTracker.DeleteAction(3, "Удаление заявки."));
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
    public void show(Consumer<String> consumer) {
        consumer.accept("----------Меню----------");
        for (UserAction action : this.actions) {
            if (action != null) {
                consumer.accept(action.info());
            }
        }
    }

    /**
     * Добавление новой заявки.
     */
    private class AddAction extends BaseAction {
        public AddAction(int key, String name) {
            super(key, name);
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
    }

    /**
     * Вывод имён и id всех имеющихся заявок.
     */
    public class FindAllAction extends BaseAction {
        public FindAllAction(int key, String name) {
            super(key, name);
        }

        @Override
        public void execute(Input input, Tracker tracker) {
            if (tracker.findAll().size() == 0) {
                System.out.println("------------ В хранилище нет ни одной заявки ------------");
            } else {
                System.out.println("------------ Список имён всех заявок. --------------");
                for (Item item : tracker.findAll()) {
                    MenuTracker.infoNameId(item, System.out::println);
                    System.out.println("------------------------------------");
                }
            }
        }
    }

    /**
     * Редактирование заявки.
     */
    private static class EditAction extends BaseAction {
        public EditAction(int key, String name) {
            super(key, name);
        }

        @Override
        public void execute(Input input, Tracker tracker) {
            String id = input.ask("Введите id нужной заявки: ");
            String name = input.ask("Введите новое имя: ");
            String desc = input.ask("Введите новый текст: ");
            if (tracker.find(item -> item.getId().equals(id), item -> {
                item.name = name;
                item.description = desc;
            })) {
                System.out.println("------------ Заявка отредактирована ------------");
            } else {
                System.out.println("------------------------------------");
                System.out.println("Заявка для редактирования с id " + id + " не найдена.");
                System.out.println("------------------------------------");
            }
        }
    }

    /**
     * Удаление заявки.
     */
    private static class DeleteAction extends BaseAction {
        public DeleteAction(int key, String name) {
            super(key, name);
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
    }
}