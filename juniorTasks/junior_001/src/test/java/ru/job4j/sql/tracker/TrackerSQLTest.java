package ru.job4j.sql.tracker;

import org.junit.Test;
import ru.job4j.tracker.Item;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class TrackerSQLTest {

    public Connection init() {
        try (InputStream in = TrackerSQL.class.getClassLoader().getResourceAsStream("app.properties")) {
            Properties config = new Properties();
            config.load(in);
            Class.forName(config.getProperty("driver-class-name"));
            return DriverManager.getConnection(
                    config.getProperty("url"),
                    config.getProperty("username"),
                    config.getProperty("password")


            );
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @Test
    public void createItem() {
        try {
            try (TrackerSQL tracker = new TrackerSQL(ConnectionRollback.create(this.init()))) {
                tracker.add(new Item("name", "desc"));
                assertThat(tracker.findByName("name").size(), is(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void findByItemsID() {
        try {
            try (TrackerSQL tracker = new TrackerSQL(ConnectionRollback.create(this.init()))) {
                Item item = tracker.add(new Item("name", "desc"));
                String result = item.getId();
                assertThat(tracker.findById(result).getName(), is("name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void findByItemsName() {
        try {
            try (TrackerSQL tracker = new TrackerSQL(ConnectionRollback.create(this.init()))) {
                tracker.add(new Item("name", "desc"));
                tracker.add(new Item("nameTwo", "desc"));
                tracker.add(new Item("name", "desc"));
                assertThat(tracker.findByName("name").size(), is(2));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void findAllItems() {
        try {
            try (TrackerSQL tracker = new TrackerSQL(ConnectionRollback.create(this.init()))) {
                tracker.add(new Item("nameOne", "descOne"));
                tracker.add(new Item("nameTwo", "descTwo"));
                tracker.add(new Item("nameThree", "descThree"));
                assertThat(tracker.findAll().size(), is(3));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void whenSuccefulDelete() {
        try {
            try (TrackerSQL tracker = new TrackerSQL(ConnectionRollback.create(this.init()))) {
                Item item = tracker.add(new Item("name", "desc"));
                String result = item.getId();
                assertThat(tracker.delete(result), is(true));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void whenReplace() {
        try {
            try (TrackerSQL tracker = new TrackerSQL(ConnectionRollback.create(this.init()))) {
                Item item = tracker.add(new Item("name", "desc"));
                String id = item.getId();
                boolean result = tracker.replace(id, "newName", "newDesc");
                assertThat(result, is(true));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void whenDeleteFailed() {
        try {
            try (TrackerSQL tracker = new TrackerSQL(ConnectionRollback.create(this.init()))) {
                Item item = tracker.add(new Item("name", "desc"));
                String result = item.getId();
                assertThat(tracker.delete(result + "15"), is(false));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void checkConnection() {
        TrackerSQL sql = new TrackerSQL();
        assertThat(sql.init(), is(true));
    }
}