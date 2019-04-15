package ru.job4j.sql.tracker;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Properties;

import org.junit.After;
import org.junit.Before;
import ru.job4j.tracker.*;


public class TrackerSQLTest {

    private final PrintStream stdout = System.out;
    private final ByteArrayOutputStream out = new ByteArrayOutputStream();

    private final String separator = System.lineSeparator();
    private final String menu = new StringBuilder()
            .append("----------Меню----------").append(separator)
            .append("0. Добавить новую заявку.").append(separator)
            .append("1. Показать список всех заявок.").append(separator)
            .append("2. Редактирование заявки.").append(separator)
            .append("3. Удаление заявки.").append(separator)
            .append("4. Поиск по id.").append(separator)
            .append("5. Поиск заявки по имени.").append(separator)
            .append("6. Выход.").append(separator)
            .toString();

    @Before
    public void loadOutput() {
        System.out.println("execute before method");
        System.setOut(new PrintStream(this.out));
    }

    @After
    public void backOutput() {
        System.setOut(this.stdout);
        System.out.println("execute after method");
    }

    /**
     * Удаление ВСЕХ записей в таблице.
     */
    private void deleteALL() {
        try (InputStream in = TrackerSQL.class.getClassLoader().getResourceAsStream(
                "trackerprops/app.properties")) {
            Properties config = new Properties();
            config.load(in);
            Class.forName(config.getProperty("driver-class-name"));
            Connection connection = DriverManager.getConnection(
                    config.getProperty("url"),
                    config.getProperty("username"),
                    config.getProperty("password")
            );
            Statement statement = connection.createStatement();
            statement.execute("DELETE FROM items;");
            connection.close();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * метод для установки input и для запуска StartUI().init().
     *
     * @param items массив, содержащий последовательность ответов пользователя.
     */
    public void setInput(String[] items, ITracker tracker) {
        Input input = new StubInput(items);
        new StartUI(input, tracker).init();
    }

    @Test
    public void checkConnection() {
        TrackerSQL sql = new TrackerSQL();
        assertThat(sql.init(), is(true));
    }

    @Test
    public void whenUserAddItemThenTrackerHasNewItemWithSameName() throws Exception {
        ITracker tracker = new TrackerSQL();
        ((TrackerSQL) tracker).init();
        setInput(new String[]{"0", "test name", "desc", "6"}, tracker);
        assertThat(
                new String(this.out.toByteArray()),
                is(
                        new StringBuilder()
                                .append(menu)
                                .append("------------ Добавление новой заявки. --------------").append(separator)
                                .append("Имя: test name").append(separator)
                                .append("id: " + tracker.findAll().get(0).getId()).append(separator)
                                .append("Текст: desc").append(separator)
                                .append(menu)
                                .toString()
                )
        );
        ((TrackerSQL) tracker).close();
    }

    @Test
    public void whenUpdateThenTrackerHasUpdatedValue() throws Exception {
        ITracker tracker = new TrackerSQL();
        ((TrackerSQL) tracker).init();
        Item item = tracker.add(new Item("test name", "desc"));
        setInput(new String[]{"2", item.getId(), "test replace", "заменили заявку", "6"}, tracker);
        assertThat(
                new String(this.out.toByteArray()),
                is(
                        new StringBuilder()
                                .append(menu)
                                .append("------------ Заявка отредактирована ------------").append(separator)
                                .append(menu)
                                .toString()
                )
        );
        ((TrackerSQL) tracker).close();
    }

    @Test
    public void whenDeleteThenTrackerHasNoItem() throws Exception {
        ITracker tracker = new TrackerSQL();
        ((TrackerSQL) tracker).init();
        Item item = tracker.add(new Item("test name", "desc"));
        setInput(new String[]{"3", item.getId(), "6"}, tracker);
        assertThat(
                new String(this.out.toByteArray()),
                is(
                        new StringBuilder()
                                .append(menu)
                                .append("------------ Заявка удалена ------------").append(separator)
                                .append(menu)
                                .toString()
                )
        );
        ((TrackerSQL) tracker).close();
    }

    @Test
    public void whenFindID() throws Exception {
        ITracker tracker = new TrackerSQL();
        ((TrackerSQL) tracker).init();
        Item item = tracker.add(new Item("test name", "desc"));
        setInput(new String[]{"4", item.getId(), "6"}, tracker);
        assertThat(
                new String(this.out.toByteArray()),
                is(
                        new StringBuilder()
                                .append(menu)
                                .append("------------------------------------").append(separator)
                                .append("Заявка с id " + item.getId() + ":").append(separator)
                                .append("Имя: " + item.getName()).append(separator)
                                .append("id: " + item.getId()).append(separator)
                                .append("Текст: " + item.getDescription()).append(separator)
                                .append("Время создания: " + item.getCreate()).append(separator)
                                .append(menu)
                                .toString()
                )
        );
        ((TrackerSQL) tracker).close();
    }

    @Test
    public void whenFindName() throws Exception {
        ITracker tracker = new TrackerSQL();
        ((TrackerSQL) tracker).init();
        Item item1 = tracker.add(new Item("test name 8", "desc01"));
        Item item2 = tracker.add(new Item("test name 1", "desc02"));
        Item item3 = tracker.add(new Item("test name 8", "desc03"));
        setInput(new String[]{"5", "test name 8", "6"}, tracker);
        assertThat(
                new String(this.out.toByteArray()),
                is(
                        new StringBuilder()
                                .append(menu)
                                .append("------------------------------------").append(separator)
                                .append("Список заявок с именем test name 8:").append(separator)
                                .append("Имя: " + item1.getName()).append(separator)
                                .append("id: " + item1.getId()).append(separator)
                                .append("Имя: " + item3.getName()).append(separator)
                                .append("id: " + item3.getId()).append(separator)
                                .append(menu)
                                .toString()
                )
        );
        ((TrackerSQL) tracker).close();
    }

    @Test
    public void whenFindAll() throws Exception {
        ITracker tracker = new TrackerSQL();
        ((TrackerSQL) tracker).init();
        Item item1 = tracker.add(new Item("test name 1", "desc01"));
        Item item2 = tracker.add(new Item("test name 2", "desc02"));
        Item item3 = tracker.add(new Item("test name 3", "desc03"));
        setInput(new String[]{"1", "6"}, tracker);
        assertThat(
                new String(this.out.toByteArray()),
                is(
                        new StringBuilder()
                                .append(menu)
                                .append("------------ Список имён всех заявок. --------------").append(separator)
                                .append("Имя: " + item1.getName()).append(separator)
                                .append("id: " + item1.getId()).append(separator)
                                .append("Имя: " + item2.getName()).append(separator)
                                .append("id: " + item2.getId()).append(separator)
                                .append("Имя: " + item3.getName()).append(separator)
                                .append("id: " + item3.getId()).append(separator)
                                .append(menu)
                                .toString()
                )
        );
        ((TrackerSQL) tracker).close();
    }
}