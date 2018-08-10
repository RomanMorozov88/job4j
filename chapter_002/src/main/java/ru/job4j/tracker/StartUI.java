package ru.job4j.tracker;

import java.util.*;

/**
 * @version $Id$
 * @since 0.1
 */
public class StartUI {
    /**
     * Константа меню для добавления новой заявки.
     */
    private static final String ADD = "0";

    /**
     * Константа для вывода всех заявок.
     */
    private static final String FINDALL = "1";

    /**
     * Константа для редактирования.
     */
    private static final String REPLACE = "2";

    /**
     * Константа для удаления.
     */
    private static final String DELETE = "3";

    /**
     * Константа для поиска по id.
     */
    private static final String FINDID = "4";

    /**
     * Константа для поиска по имени.
     */
    private static final String FINDNAME = "5";

    /**
     * Константа для выхода из цикла.
     */
    private static final String EXIT = "6";
    /**
     * Получение данных от пользователя.
     */
    private final Input input;

    /**
     * Хранилище заявок.
     */
    private final Tracker tracker;

    /**
     * Конструтор инициализирующий поля.
     * @param input ввод данных.
     * @param tracker хранилище заявок.
     */
    public StartUI(Input input, Tracker tracker) {
        this.input = input;
        this.tracker = tracker;
    }

    /**
     * Основой цикл программы.
     */
    public void init() {
        MenuTracker menu = new MenuTracker(this.input, this.tracker);
        List<Integer> range = new ArrayList<>();
        menu.fillActions();
        for (int i = 0; i < menu.getActionsLentgh(); i++) {
            range.add(i);
        }
        do {
            menu.show();
            menu.select(Integer.valueOf(input.ask("Выберите нужный пункт меню: ")));
        } while (!"y".equals(this.input.ask("Выйти?(y): ")));
    }

    /**
     * Метод реализует добавление новой заявки в хранилище.
     */
    private void createItem() {
        System.out.println("------------ Добавление новой заявки --------------");
        String name = this.input.ask("Введите имя заявки :");
        String desc = this.input.ask("Введите описание заявки :");
        Item item = new Item(name, desc);
        this.tracker.add(item);
        System.out.println("------------ Новая заявка с getId : " + item.getId() + "-----------");
    }

    private void findAllItem() {
        System.out.println("------------ Список имён уже существующих заявок --------------");
        Item[] result = this.tracker.findAll();
        if (result.length == 0) {
            System.out.println("------------ В хранилище нет ни одной заявки ------------");
        } else {
            for (int i = 0; i < result.length; i++) {
                System.out.println("1." + result[i].getName());
            }
        }
    }

    private void replaceItem() {
        System.out.println("------------ Редактирование заявки --------------");
        String id = this.input.ask("Введите id заявки :");
            String name = this.input.ask("Введите новое имя заявки :");
            String desc = this.input.ask("Введите новое описание заявки :");
            Item item = new Item(name, desc);
            if (this.tracker.replace(id, item)) {
            System.out.println("------------ Заявка отредактирована ------------");
        } else {
                System.out.println("Заявка для редактирования с id " + id + " не найдена.");
            }
    }

    private void findByIdItem() {
        System.out.println("------------ Поиск заявки по id ------------");
        String id = this.input.ask("Введите id заявки :");
            Item result = this.tracker.findById(id);
            if (result != null) {
                System.out.println(result.getName());
                System.out.println(result.getDescription());
                System.out.println(result.getCreate());
                System.out.println("--------------------------------------------");
            } else {
                System.out.println("Заявка с id " + id + " не найдена.");
            }
        }

    private void findByNameItem() {
        System.out.println("------------ Поиск заявки по имени ------------");
        String name = this.input.ask("Введите имя заявки :");
        Item[] result = this.tracker.findByName(name);
        if (result.length > 0) {
            for (int i = 0; i < result.length; i++) {
                System.out.println(result[i].getName());
                System.out.println(result[i].getDescription());
                System.out.println(result[i].getCreate());
                System.out.println("--------------------------------------------");
            }
        } else {
            System.out.println("Заявок с таким именем не найдено.");
        }
    }

    private void deleteItem() {
        System.out.println("------------ Удаление заявки ------------");
        String id = this.input.ask("Введите id заявки :");
         if (this.tracker.delete(id)) {
             System.out.println("------------ Заявка удалена ------------");
         } else {
             System.out.println("Заявка с id " + id + " не найдена.");
         }
    }

    private void showMenu() {
        System.out.println("Меню.");
        System.out.println("0. Добавление новой заявки.");
        System.out.println("1. Показать все существующие заявки.");
        System.out.println("2. Редактирование заявки.");
        System.out.println("3. Удаление заявки.");
        System.out.println("4. Поиск заявки по id.");
        System.out.println("5. Поиск заявки по имени.");
        System.out.println("6. Выход.");

    }

    /**
     * Запускт программы.
     * @param args
     */
    public static void main(String[] args) {
        new StartUI(new ConsoleInput(), new Tracker()).init();
    }
}