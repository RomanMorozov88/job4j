package ru.job4j.ood.menu;

import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class MenuVaultTest {

    private final ByteArrayOutputStream out = new ByteArrayOutputStream();
    private final String separator = System.lineSeparator();
    private final PrintStream printStream = new PrintStream(out);

    public MenuVault setUp() {
        MenuVault mt = new MenuVault(printStream::println);

        Action simple = new SimpleAction();
        Action difficult = new DifficultAction();

        MenuNode mn06 = new MenuNode("Task006", difficult);
        MenuNode mn03 = new MenuNode("Task003", simple);
        MenuNode mn02 = new MenuNode("Task002", simple, Arrays.asList(mn03));
        MenuNode mn05 = new MenuNode("Task005", simple);
        MenuNode mn04 = new MenuNode("Task004", simple, Arrays.asList(mn05));
        MenuNode mn01 = new MenuNode("Task001", simple, Arrays.asList(mn02, mn04));


        mt.setPoints(mn01);
        mt.setPoints(mn06);
        return mt;
    }

    @Test
    public void justShowMenu() {
        MenuVault testMenu = setUp();
        testMenu.showMenu();
        Assert.assertThat(
                new String(this.out.toByteArray()),
                Is.is(
                        new StringBuilder()
                                .append(">Task001").append(separator)
                                .append("--->Task002").append(separator)
                                .append("------>Task003").append(separator)
                                .append("--->Task004").append(separator)
                                .append("------>Task005").append(separator)
                                .append(">Task006").append(separator)
                                .toString()
                )
        );
    }

    @Test
    public void whenGetActionByMenuName() {
        MenuVault testMenu = setUp();
        String result = testMenu.getActionByName("Task003");
        assertThat(result, is("Some simple action of Task003"));
        result = testMenu.getActionByName("Task006");
        assertThat(result, is("Some difficult action of Task006"));
    }

    @Test
    public void run() {
        MenuVault mt = new MenuVault(System.out::println);

        Action simple = new SimpleAction();
        Action difficult = new DifficultAction();

        MenuNode mn08 = new MenuNode("Task 1.2.1", simple);
        MenuNode mn07 = new MenuNode("Task 1.2.2", simple);
        MenuNode mn06 = new MenuNode("Task 2", difficult);
        MenuNode mn03 = new MenuNode("Task 1.1", simple);
        MenuNode mn05 = new MenuNode("Task 1.3.1", simple);
        MenuNode mn04 = new MenuNode("Task 1.3", simple);
        MenuNode mn02 = new MenuNode("Task 1.2", simple, Arrays.asList(mn08, mn07));
        MenuNode mn01 = new MenuNode("Task 1", simple, Arrays.asList(mn02, mn04, mn03));

        mt.setPoints(mn06);
        mt.setPoints(mn01);
        mt.setPoints(mn05, "Task 1.3");
        mt.showMenu();
    }

}