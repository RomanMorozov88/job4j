package ru.job4j.additionaltasks.deptsort;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class SortingDeptsTest {
    private final PrintStream stdout = System.out;
    private final ByteArrayOutputStream out = new ByteArrayOutputStream();

    private final String separator = System.lineSeparator();

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
     * Сортировка по убыванию.
     */
    @Test
    public void whenReverseSorting() {
        Dept k1 = new Dept("K1");
        Dept k2 = new Dept("K2");
        Dept sk1 = new Dept("SK1", new ArrayList<>(Arrays.asList(k1, k2)));
        Dept ssk1 = new Dept("SSK1", new ArrayList<>(Arrays.asList(sk1)));
        Dept sk2 = new Dept("SK2", new ArrayList<>(Arrays.asList(k1)));
        Dept ssk2 = new Dept("SSK2", new ArrayList<>(Arrays.asList(sk1)));

        SortingDepts sortingDepts = new SortingDepts();
        sortingDepts.namesAllDepts(new ArrayList<>(Arrays.asList(k2, ssk2, k1, sk1, ssk1, sk2)),
                SortingDepts.secondCompar)
                .forEach(System.out::println);
        assertThat(
                new String(this.out.toByteArray()),
                is(
                        "K2"
                                + separator
                                + "K2\\SK1" + separator
                                + "K2\\SK1\\SSK2" + separator
                                + "K2\\SK1\\SSK1"
                                + separator
                                + "K1" + separator
                                + "K1\\SK2" + separator
                                + "K1\\SK1" + separator
                                + "K1\\SK1\\SSK2" + separator
                                + "K1\\SK1\\SSK1" + separator
                )
        );
    }

    /**
     * Сортировка по убыванию только главных отделов.
     */
    @Test
    public void whenReverseSortingTwo() {
        Dept k1 = new Dept("K1");
        Dept k2 = new Dept("K2");
        Dept sk1 = new Dept("SK1", new ArrayList<>(Arrays.asList(k1, k2)));
        Dept ssk1 = new Dept("SSK1", new ArrayList<>(Arrays.asList(sk1)));
        Dept sk2 = new Dept("SK2", new ArrayList<>(Arrays.asList(k1)));
        Dept ssk2 = new Dept("SSK2", new ArrayList<>(Arrays.asList(sk1)));

        SortingDepts sortingDepts = new SortingDepts();
        sortingDepts.namesAllDepts(new ArrayList<>(Arrays.asList(k2, ssk2, k1, sk1, ssk1, sk2)),
                SortingDepts.thirdCompar)
                .forEach(System.out::println);
        assertThat(
                new String(this.out.toByteArray()),
                is(
                        "K2" + separator
                                + "K2\\SK1" + separator
                                + "K2\\SK1\\SSK1" + separator
                                + "K2\\SK1\\SSK2" + separator
                                + "K1" + separator
                                + "K1\\SK1" + separator
                                + "K1\\SK1\\SSK1" + separator
                                + "K1\\SK1\\SSK2" + separator
                                + "K1\\SK2" + separator
                )
        );
    }

    /**
     * Сортировка по возрастанию.
     */
    @Test
    public void whenRegularSorting() {
        Dept k1 = new Dept("K1");
        Dept k2 = new Dept("K2");
        Dept sk1 = new Dept("SK1", new ArrayList<>(Arrays.asList(k1, k2)));
        Dept ssk1 = new Dept("SSK1", new ArrayList<>(Arrays.asList(sk1)));
        Dept sk2 = new Dept("SK2", new ArrayList<>(Arrays.asList(k1)));
        Dept ssk2 = new Dept("SSK2", new ArrayList<>(Arrays.asList(sk1)));

        SortingDepts sortingDepts = new SortingDepts();
        sortingDepts.namesAllDepts(new ArrayList<>(Arrays.asList(k2, ssk2, k1, sk1, ssk1, sk2)),
                SortingDepts.firstCompar)
                .forEach(System.out::println);
        assertThat(
                new String(this.out.toByteArray()),
                is(
                        "K1" + separator
                                + "K1\\SK1" + separator
                                + "K1\\SK1\\SSK1" + separator
                                + "K1\\SK1\\SSK2" + separator
                                + "K1\\SK2" + separator
                                + "K2" + separator
                                + "K2\\SK1" + separator
                                + "K2\\SK1\\SSK1" + separator
                                + "K2\\SK1\\SSK2" + separator
                )
        );
    }

    /**
     * Сортировка по возрастанию входящего списка.
     */
    @Test
    public void whenRegularSortingOnlyList() {

        List<String> inputTest = Arrays.asList(
                "K1\\SK1", "K1\\SK2", "K1\\SK1\\SSK1", "K1\\SK1\\SSK2", "K2", "K2\\SK1\\SSK1", "K2\\SK1\\SSK2");
        NameListSet.nameListSet(inputTest,
                SortingDepts.firstCompar)
                .forEach(System.out::println);
        assertThat(
                new String(this.out.toByteArray()),
                is(
                        "K1" + separator
                                + "K1\\SK1" + separator
                                + "K1\\SK1\\SSK1" + separator
                                + "K1\\SK1\\SSK2" + separator
                                + "K1\\SK2" + separator
                                + "K2" + separator
                                + "K2\\SK1" + separator
                                + "K2\\SK1\\SSK1" + separator
                                + "K2\\SK1\\SSK2" + separator
                )
        );
    }

    /**
     * Сортировка по убыванию входящего списка.
     */
    @Test
    public void whenReverseSortingOnlyList() {
        List<String> inputTest = Arrays.asList(
                "K1\\SK1", "K1\\SK2", "K1\\SK1\\SSK1", "K1\\SK1\\SSK2", "K2", "K2\\SK1\\SSK1", "K2\\SK1\\SSK2");
        NameListSet.nameListSet(inputTest,
                SortingDepts.secondCompar)
                .forEach(System.out::println);
        assertThat(
                new String(this.out.toByteArray()),
                is(
                        "K2"
                                + separator
                                + "K2\\SK1" + separator
                                + "K2\\SK1\\SSK2" + separator
                                + "K2\\SK1\\SSK1"
                                + separator
                                + "K1" + separator
                                + "K1\\SK2" + separator
                                + "K1\\SK1" + separator
                                + "K1\\SK1\\SSK2" + separator
                                + "K1\\SK1\\SSK1" + separator
                )
        );
    }

    /**
     * Сортировка по убыванию только главных отделов входящего списка.
     */
    @Test
    public void whenReverseSortingTwoOnlyList() {
        List<String> inputTest = Arrays.asList(
                "K1\\SK1", "K1\\SK2", "K1\\SK1\\SSK1", "K1\\SK1\\SSK2", "K2", "K2\\SK1\\SSK1", "K2\\SK1\\SSK2");
        NameListSet.nameListSet(inputTest,
                SortingDepts.thirdCompar)
                .forEach(System.out::println);
        assertThat(
                new String(this.out.toByteArray()),
                is(
                        "K2" + separator
                                + "K2\\SK1" + separator
                                + "K2\\SK1\\SSK1" + separator
                                + "K2\\SK1\\SSK2" + separator
                                + "K1" + separator
                                + "K1\\SK1" + separator
                                + "K1\\SK1\\SSK1" + separator
                                + "K1\\SK1\\SSK2" + separator
                                + "K1\\SK2" + separator
                )
        );
    }
}