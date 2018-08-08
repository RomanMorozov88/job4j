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
        assertThat(tracker.findAll()[0].getName(), is("test name"));
    }

    @Test
    public void whenUpdateThenTrackerHasUpdatedValue() {
        //Напрямую добавляем заявку
        Item item = tracker.add(new Item("test name", "desc"));
        setInput(new String[]{"2", item.getId(), "test replace", "заменили заявку", "6"});
        // проверяем, что нулевой элемент массива в трекере содержит имя, введённое при эмуляции.
        assertThat(tracker.findById(item.getId()).getName(), is("test replace"));
    }

    @Test
    public void whenDeleteThenTrackerHasNoItem() {
        //Напрямую добавляем заявку
        Item item = tracker.add(new Item("test name", "desc"));
        setInput(new String[]{"3", item.getId(), "6"});
        // проверяем, что при вызове метода findAll длина возвращаемого массива будет равна нулю.
        assertThat(tracker.findAll().length, is(0));
    }

    @Test
    public void whenFindID() {
        //Напрямую добавляем заявку
        Item item = tracker.add(new Item("test name", "desc"));
        setInput(new String[]{"4", item.getId(), "6"});
        // проверяем, что при вызове метода findById выводится наш item.
        assertThat(tracker.findById(item.getId()).getName(), is("test name"));
    }

    @Test
    public void whenFindName() {
        //Напрямую добавляем заявку
        Item item1 = tracker.add(new Item("test name", "desc01"));
        Item item2 = tracker.add(new Item("test name 1", "desc02"));
        Item item3 = tracker.add(new Item("test name", "desc03"));
        setInput(new String[]{"5", "test name", "6"});
        Item[] expect = new Item[]{item1, item3};
        // проверяем, что при вызове метода findByName выводятся наши item`ы с именем test name.
        assertThat(tracker.findByName("test name"), is(expect));
    }

    @Test
    public void whenFindAll() {
        //Напрямую добавляем заявку
        Item item1 = tracker.add(new Item("test name 1", "desc01"));
        Item item2 = tracker.add(new Item("test name 2", "desc02"));
        Item item3 = tracker.add(new Item("test name 3", "desc03"));
        setInput(new String[]{"1", "6"});
        Item[] expect = new Item[]{item1, item2, item3};
        // проверяем, что при вызове метода findAll выводятся все существующие item`ы.
        assertThat(tracker.findAll(), is(expect));
    }
}