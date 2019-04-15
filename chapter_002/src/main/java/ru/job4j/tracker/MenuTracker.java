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
    public void execute(Input input, ITracker tracker, Consumer consumer) {
        String id = input.ask("Введите id нужной заявки: ");
        Item result = tracker.findById(id);
        if (result != null) {
            consumer.accept("------------------------------------");
            consumer.accept("Заявка с id " + id + ":");
            MenuTracker.infoNameId(result, System.out::println);
            MenuTracker.infoDescCreate(result, System.out::println);
        } else {
            consumer.accept("------------------------------------");
            consumer.accept("Заявка с id " + id + " не найдена.");
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
    public void execute(Input input, ITracker tracker, Consumer consumer) {
        String name = input.ask("Введите имя нужной заявки: ");
        List<Item> result = tracker.findByName(name);
        if (result.size() != 0) {
            consumer.accept("------------------------------------");
            consumer.accept("Список заявок с именем " + name + ":");
            result
                    .stream()
                    .forEach(x -> MenuTracker.infoNameId(x, System.out::println));
        } else {
            consumer.accept("------------------------------------");
            consumer.accept("Заявок с таким именем не найдено.");
            consumer.accept("------------------------------------");
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
    public void execute(Input input, ITracker tracker, Consumer consumer) {
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
    private ITracker tracker;
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
    public MenuTracker(Input input, ITracker tracker) {
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
     *
     * @param item
     * @param consumer
     */
    public static void infoNameId(Item item, Consumer consumer) {
        consumer.accept("Имя: " + item.getName());
        consumer.accept("id: " + item.getId());
    }

    /**
     * Вывод описания и времени создания искомой заявки.
     *
     * @param item
     * @param consumer
     */
    public static void infoDescCreate(Item item, Consumer consumer) {
        consumer.accept("Текст: " + item.getDescription());
        consumer.accept("Время создания: " + item.getCreate());
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
        this.actions.get(key).execute(this.input, this.tracker, System.out::println);
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
        public void execute(Input input, ITracker tracker, Consumer consumer) {
            consumer.accept("------------ Добавление новой заявки. --------------");
            String name = input.ask("Введите имя заявки:");
            String desc = input.ask("Введите текст заявки:");
            Item item = new Item(name, desc);
            tracker.add(item);
            consumer.accept("Имя: " + item.getName());
            consumer.accept("id: " + item.getId());
            consumer.accept("Текст: " + item.getDescription());
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
        public void execute(Input input, ITracker tracker, Consumer consumer) {
            List<Item> result = tracker.findAll();
            if (result.size() == 0) {
                consumer.accept("------------ В хранилище нет ни одной заявки ------------");
            } else {
                consumer.accept("------------ Список имён всех заявок. --------------");
                result
                        .stream()
                        .forEach(x -> {
                            MenuTracker.infoNameId(x, System.out::println);
                        });
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
        public void execute(Input input, ITracker tracker, Consumer consumer) {
            String id = input.ask("Введите id нужной заявки: ");
            String name = input.ask("Введите новое имя: ");
            String desc = input.ask("Введите новый текст: ");
            if (tracker.replace(id, name, desc)) {
                consumer.accept("------------ Заявка отредактирована ------------");
            } else {
                consumer.accept("------------------------------------");
                consumer.accept("Заявка для редактирования с id " + id + " не найдена.");
                consumer.accept("------------------------------------");
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
        public void execute(Input input, ITracker tracker, Consumer consumer) {
            String id = input.ask("Введите id нужной заявки: ");
            if (tracker.delete(id)) {
                consumer.accept("------------ Заявка удалена ------------");
            } else {
                consumer.accept("------------------------------------");
                consumer.accept("Заявка с id " + id + " не найдена.");
                consumer.accept("------------------------------------");
            }
        }
    }
}