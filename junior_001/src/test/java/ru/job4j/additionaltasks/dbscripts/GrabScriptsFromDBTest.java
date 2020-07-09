package ru.job4j.additionaltasks.dbscripts;

import org.junit.Test;
import ru.job4j.sql.tracker.ConnectionRollback;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class GrabScriptsFromDBTest {

    public Connection init() {
        try (InputStream in = GrabScriptsFromDB.class.getClassLoader()
                .getResourceAsStream("dbscripts/app.properties")) {
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
            try (GrabScriptsFromDB testGrab = new GrabScriptsFromDB(ConnectionRollback.create(this.init()))) {

                testGrab.tableExistCheck();

                testGrab.addInTable(123, "Root");
                testGrab.addInTable(1, 123, "Root/Ch01");
                testGrab.addInTable(124, 1, "Root/Ch01/Ch01");
                testGrab.addInTable(2, 1, "Root/Ch01/Ch02");
                testGrab.addInTable(200, 123, "Root/Ch02");
                testGrab.addInTable(50, 2, "Root/Ch01/Ch02/Ch01");

                testGrab.grabber(123);
                String result = testGrab.getScript();
                assertThat(result, is("Root/Ch01/Ch02/Ch01"));
                result = testGrab.getScript();
                assertThat(result, is("Root/Ch01/Ch02"));
                result = testGrab.getScript();
                assertThat(result, is("Root/Ch01/Ch01"));
                result = testGrab.getScript();
                assertThat(result, is("Root/Ch02"));
                result = testGrab.getScript();
                assertThat(result, is("Root/Ch01"));
                result = testGrab.getScript();
                assertThat(result, is("Root"));
                boolean controlResult = testGrab.checkWorkStack();
                assertThat(controlResult, is(true));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}