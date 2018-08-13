package ru.job4j.tracker;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.After;
import org.junit.Before;


public class StartUITest {

    private final Tracker tracker = new Tracker();
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
     * метод для установки input и для запуска StartUI().init().
     * @param items массив, содержащий последовательность ответов пользователя.
     */
    public void setInput(String[] items) {
        Input input = new StubInput(items);
        new StartUI(input, this.tracker).init();
    }

    @Test
    public void whenUserAddItemThenTrackerHasNewItemWithSameName() {
        setInput(new String[]{"0", "test name", "desc", "6"});
        // проверяем, что нулевой элемент массива в трекере содержит имя, введённое при эмуляции.
        assertThat(
                new String(this.out.toByteArray()),
                is (
                        new StringBuilder()
                                .append(menu)
                                .append("------------ Добавление новой заявки. --------------").append(separator)
                                .append("Имя: test name").append(separator)
                                .append("id: " + this.tracker.findAll()[0].getId()).append(separator)
                                .append("Текст: desc").append(separator)
                                .append(menu)
                                .toString()
                )
        );
    }

    @Test
    public void whenUpdateThenTrackerHasUpdatedValue() {
        //Напрямую добавляем заявку
        Item item = tracker.add(new Item("test name", "desc"));
        setInput(new String[]{"2", item.getId(), "test replace", "заменили заявку", "6"});
        // проверяем, что нулевой элемент массива в трекере содержит имя, введённое при эмуляции.
        assertThat(
                new String(this.out.toByteArray()),
                is (
                        new StringBuilder()
                                .append(menu)
                                .append("------------ Заявка отредактирована ------------").append(separator)
                                .append(menu)
                                .toString()
                )
        );
    }

    @Test
    public void whenDeleteThenTrackerHasNoItem() {
        //Напрямую добавляем заявку
        Item item = tracker.add(new Item("test name", "desc"));
        setInput(new String[]{"3", item.getId(), "6"});
        // проверяем, что при вызове метода findAll длина возвращаемого массива будет равна нулю.
        assertThat(
                new String(this.out.toByteArray()),
                is (
                        new StringBuilder()
                                .append(menu)
                                .append("------------ Заявка удалена ------------").append(separator)
                                .append(menu)
                                .toString()
                )
        );
    }

    @Test
    public void whenFindID() {
        //Напрямую добавляем заявку
        Item item = tracker.add(new Item("test name", "desc"));
        setInput(new String[]{"4", item.getId(), "6"});
        // проверяем, что при вызове метода findById выводится наш item.
                assertThat(
                        new String(this.out.toByteArray()),
                        is (
                                new StringBuilder()
                                        .append(menu)
                                        .append("------------------------------------").append(separator)
                                        .append("Имя: " + item.getName()).append(separator)
                                        .append("id: " + item.getId()).append(separator)
                                        .append("Текст: " + item.getDescription()).append(separator)
                                        .append("Время создания: " + item.getCreate()).append(separator)
                                        .append(menu)
                                        .toString()
                        )
                );
    }

    @Test
    public void whenFindName() {
        //Напрямую добавляем заявку
        Item item1 = tracker.add(new Item("test name", "desc01"));
        Item item2 = tracker.add(new Item("test name 1", "desc02"));
        Item item3 = tracker.add(new Item("test name", "desc03"));
        setInput(new String[]{"5", "test name", "6"});
        // проверяем, что при вызове метода findByName выводятся наши item`ы с именем test name.
        assertThat(
                new String(this.out.toByteArray()),
                is (
                        new StringBuilder()
                                .append(menu)
                                .append("------------------------------------").append(separator)
                                .append("Список заявок с именем test name:\n").append(separator)
                                .append("Имя: " + item1.getName()).append(separator)
                                .append("id: " + item1.getId()).append(separator)
                                .append("------------------------------------").append(separator)
                                .append("Имя: " + item3.getName()).append(separator)
                                .append("id: " + item3.getId()).append(separator)
                                .append("------------------------------------").append(separator)
                                .append(menu)
                                .toString()
                )
        );
    }

    @Test
    public void whenFindAll() {
        //Напрямую добавляем заявку
        Item item1 = tracker.add(new Item("test name 1", "desc01"));
        Item item2 = tracker.add(new Item("test name 2", "desc02"));
        Item item3 = tracker.add(new Item("test name 3", "desc03"));
        setInput(new String[]{"1", "6"});
        // проверяем, что при вызове метода findAll выводятся все существующие item`ы.
        assertThat(
                new String(this.out.toByteArray()),
                is (
                        new StringBuilder()
                        .append(menu)
                        .append("------------ Список имён всех заявок. --------------").append(separator)
                        .append("Имя: " + item1.getName()).append(separator)
                        .append("id: " + item1.getId()).append(separator)
                        .append("------------------------------------").append(separator)
                        .append("Имя: " + item2.getName()).append(separator)
                        .append("id: " + item2.getId()).append(separator)
                        .append("------------------------------------").append(separator)
                        .append("Имя: " + item3.getName()).append(separator)
                        .append("id: " + item3.getId()).append(separator)
                        .append("------------------------------------").append(separator)
                        .append(menu)
                        .toString()
                )
        );
    }
}