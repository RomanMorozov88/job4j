package ru.job4j.tracker;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;


public class StartUITest {

    @Test
    public void whenUserAddItemThenTrackerHasNewItemWithSameName() {
        // создаём Tracker
        Tracker tracker = new Tracker();
        //создаём StubInput с последовательностью действий
        Input input = new StubInput(new String[]{"0", "test name", "desc", "6"});
        //   создаём StartUI и вызываем метод init()
        new StartUI(input, tracker).init();
        // проверяем, что нулевой элемент массива в трекере содержит имя, введённое при эмуляции.
        assertThat(tracker.findAll()[0].getName(), is("test name"));
    }

    @Test
    public void whenUpdateThenTrackerHasUpdatedValue() {
        // создаём Tracker
        Tracker tracker = new Tracker();
        //Напрямую добавляем заявку
        Item item = tracker.add(new Item("test name", "desc"));
        //создаём StubInput с последовательностью действий(производим замену заявки)
        Input input = new StubInput(new String[]{"2", item.getId(), "test replace", "заменили заявку", "6"});
        // создаём StartUI и вызываем метод init()
        new StartUI(input, tracker).init();
        // проверяем, что нулевой элемент массива в трекере содержит имя, введённое при эмуляции.
        assertThat(tracker.findById(item.getId()).getName(), is("test replace"));
    }

    @Test
    public void whenDeleteThenTrackerHasNoItem() {
        // создаём Tracker
        Tracker tracker = new Tracker();
        //Напрямую добавляем заявку
        Item item = tracker.add(new Item("test name", "desc"));
        //создаём StubInput с последовательностью действий(производим удаление заявки)
        Input input = new StubInput(new String[]{"3", item.getId(), "6"});
        // создаём StartUI и вызываем метод init()
        new StartUI(input, tracker).init();
        // проверяем, что при вызове метода findAll длина возвращаемого массива будет равна нулю.
        assertThat(tracker.findAll().length, is(0));
    }

    @Test
    public void whenFindID() {
        // создаём Tracker
        Tracker tracker = new Tracker();
        //Напрямую добавляем заявку
        Item item = tracker.add(new Item("test name", "desc"));
        //создаём StubInput с последовательностью действий(производим поиск заявки по id)
        Input input = new StubInput(new String[]{"4", item.getId(), "6"});
        // создаём StartUI и вызываем метод init()
        new StartUI(input, tracker).init();
        assertThat(tracker.findById(item.getId()).getName(), is("test name"));
    }

    @Test
    public void whenFindName() {
        // создаём Tracker
        Tracker tracker = new Tracker();
        //Напрямую добавляем заявку
        Item item1 = tracker.add(new Item("test name", "desc01"));
        Item item2 = tracker.add(new Item("test name 1", "desc02"));
        Item item3 = tracker.add(new Item("test name", "desc03"));
        //создаём StubInput с последовательностью действий(производим поиск заявки по имени)
        Input input = new StubInput(new String[]{"5", "test name", "6"});
        // создаём StartUI и вызываем метод init()
        new StartUI(input, tracker).init();
        Item[] expect = new Item[]{item1, item3};
        assertThat(tracker.findByName("test name"), is(expect));
    }

    @Test
    public void whenFindAll() {
        // создаём Tracker
        Tracker tracker = new Tracker();
        //Напрямую добавляем заявку
        Item item1 = tracker.add(new Item("test name 1", "desc01"));
        Item item2 = tracker.add(new Item("test name 2", "desc02"));
        Item item3 = tracker.add(new Item("test name 3", "desc03"));
        //создаём StubInput с последовательностью действий(производим вывод имён всех существующих заявок)
        Input input = new StubInput(new String[]{"1", "6"});
        // создаём StartUI и вызываем метод init()
        new StartUI(input, tracker).init();
        Item[] expect = new Item[]{item1, item2, item3};
        assertThat(tracker.findAll(), is(expect));
    }
}