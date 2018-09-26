package ru.job4j.tracker;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class TrackerTest {

    @Test
    public void whenAddNewItemThenTrackerHasSameItem01() {
        Tracker tracker = new Tracker();
        Item item = new Item("test1", "testDescription", 123L);
        tracker.add(item);
        assertThat(tracker.findAll().get(0), is(item));
    }

    @Test
    public void whenAddNewItems() {
        Tracker tracker = new Tracker();
        Item previous1 = new Item("test1", "testDescription", 123L);
        Item previous2 = new Item("test2", "testDescription2", 124L);
        tracker.add(previous1);
        tracker.add(previous2);
        List<Item> result = tracker.findAll();
        List<Item> expect = Arrays.asList(previous1, previous2);
        assertThat(result, is(expect));
    }

    @Test
    public void whenDeleteItem() {
        Tracker tracker = new Tracker();
        Item previous1 = new Item("test1", "testDescription", 123L);
        Item previous2 = new Item("test2", "testDescription2", 124L);
        Item previous3 = new Item("test3", "testDescription3", 125L);
        tracker.add(previous1);
        tracker.add(previous2);
        tracker.add(previous3);
        tracker.delete(previous2.getId());
        List<Item> result = tracker.findAll();
        List<Item> expect = Arrays.asList(previous1, previous3);
        assertThat(result, is(expect));
    }

    @Test
    public void whenFindById() {
        Tracker tracker = new Tracker();
        Item previous = new Item("test1", "testDescription", 123L);
        tracker.add(previous);
        assertThat(tracker.findById(previous.getId()), is(previous));
    }

    @Test
    public void whenFindByName() {
        Tracker tracker = new Tracker();
        Item previous = new Item("test1", "testDescription", 123L);
        Item previous1 = new Item("test2", "testDescription2", 124L);
        Item previous2 = new Item("test1", "testDescription1", 125L);
        tracker.add(previous);
        tracker.add(previous1);
        tracker.add(previous2);
        List<Item> expect = Arrays.asList(previous, previous2);
        assertThat(tracker.findByName(previous.getName()), is(expect));
    }

    @Test
    public void whenReplaceNameThenReturnNewName() {
        Tracker tracker = new Tracker();
        Item previous = new Item("test1", "testDescription", 123L);
        // Добавляем заявку в трекер. Теперь в объект проинициализирован id.
        tracker.add(previous);
        // Создаем новую заявку.
        Item next = new Item("test2", "testDescription2", 1234L);
        // Проставляем старый id из previous, который был сгенерирован выше.
        next.setId(previous.getId());
        // Обновляем заявку в трекере.
        tracker.replace(previous.getId(), next);
        // Проверяем, что заявка с таким id имеет новые имя test2.
        assertThat(tracker.findById(previous.getId()).getName(), is("test2"));
    }
}