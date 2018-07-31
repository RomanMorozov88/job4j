package ru.job4j.tracker;

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
        boolean exit = false;
        while (!exit) {
            this.showMenu();
            String answer = this.input.ask("Введите пункт меню : ");
            if (ADD.equals(answer)) {
                //добавление заявки вынесено в отдельный метод.
                this.createItem();
            } else if (answer.equals("1")) {
                this.findAllItem();
            } else if (answer.equals("2")) {
                this.replaceItem();
            } else if (answer.equals("3")) {
                this.deleteItem();
            } else if (answer.equals("4")) {
                this.findByIdItem();
            } else if (answer.equals("5")) {
                this.findByNameItem();
            } else if (EXIT.equals(answer)) {
                exit = true;
            }
        }
    }

    /**
     * Метод реализует добавление новой заявки в хранилище.
     */
    private void createItem() {
        System.out.println("------------ Добавление новой заявки --------------");
        String name = this.input.ask("Введите имя заявки :");
        String desc = this.input.ask("Введите описание заявки :");
        long create = System.currentTimeMillis();
        Item item = new Item(name, desc, create);
        this.tracker.add(item);
        System.out.println("------------ Новая заявка с getId : " + item.getId() + "-----------");
    }

    private void findAllItem() {
        System.out.println("------------ Список имён уже существующих заявок --------------");
        if (this.tracker.findAll().length == 0) {
            System.out.println("------------ В хранилище нет ни одной заявки ------------");
        } else {
            for (int i = 0; i < this.tracker.findAll().length; i++) {
                System.out.println("1." + this.tracker.findAll()[i].getName());
            }
        }
    }

    private void replaceItem() {
        System.out.println("------------ Редактирование заявки --------------");
        String id = this.input.ask("Введите id заявки :");
        String name = this.input.ask("Введите новое имя заявки :");
        String desc = this.input.ask("Введите новое описание заявки :");
        Item item = new Item(name, desc);
        this.tracker.replace(id, item);
        System.out.println("------------ Заявка отредактирована ------------");
    }

    private void findByIdItem() {
        System.out.println("------------ Поиск заявки по id ------------");
        String id = this.input.ask("Введите id заявки :");
        System.out.println(this.tracker.findById(id).getName());
        System.out.println(this.tracker.findById(id).getDescription());
        System.out.println(this.tracker.findById(id).getCreate());
        System.out.println("--------------------------------------------");
    }

    private void findByNameItem() {
        System.out.println("------------ Поиск заявки по имени ------------");
        String name = this.input.ask("Введите имя заявки :");
        for (int i = 0; i < this.tracker.findByName(name).length; i++) {
            System.out.println(this.tracker.findByName(name)[i].getName());
            System.out.println(this.tracker.findByName(name)[i].getDescription());
            System.out.println(this.tracker.findByName(name)[i].getCreate());
            System.out.println("--------------------------------------------");
        }
    }

    private void deleteItem() {
        System.out.println("------------ Удаление заявки ------------");
        String id = this.input.ask("Введите id заявки :");
        this.tracker.delete(id);
        System.out.println("------------ Заявка удалена ------------");
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