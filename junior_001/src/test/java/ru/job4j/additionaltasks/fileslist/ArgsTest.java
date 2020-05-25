package ru.job4j.additionaltasks.fileslist;

import org.junit.Test;

import java.io.File;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class ArgsTest {

    /**
     * @param s - строка, которую хотим поместить в массив.
     * @param i - её позиция(мы знаем, что она должна быть не больше 6-ти,
     *          поэтому не проверяем).
     * @return
     */
    private Args testArgsInit(String s, int i) {
        String path = System.getProperty("java.io.tmpdir");
        File targetDir = new File(path + "\\targetTestDir");
        targetDir.mkdir();
        String[] testInputArray = new String[]{"-d", targetDir.getPath(), "-n", "*.txt", "-m", "-o", "logTest.txt"};
        testInputArray[i] = s;
        return new Args(testInputArray, System.out::println);
    }

    @Test
    public void getFromArgsArray() {
        String result = testArgsInit("-d", 0).getFromArgsArray(0);
        assertThat(result, is("-d"));
    }

    @Test
    public void whenStringArrayIsCorrect() {
        boolean result = testArgsInit("-f", 4).checkSettings();
        assertThat(result, is(false));
    }

    @Test
    public void whenStringArrayIsCorrectWithR() {
        boolean result = testArgsInit("-r", 4).checkSettings();
        assertThat(result, is(false));
    }

    @Test
    public void whenStringArrayIsUnCorrectOnFourPosition() {
        boolean result = testArgsInit("u", 4).checkSettings();
        assertThat(result, is(true));
    }

    @Test
    public void whenStringArrayIsUnCorrectOnFirstPosition() {
        boolean result = testArgsInit("u", 0).checkSettings();
        assertThat(result, is(true));
    }

    @Test
    public void whenDirIsNotexist() {
        String path = System.getProperty("java.io.tmpdir");
        File targetDir = new File(path + "\\targetTestDir");
        targetDir.delete();
        String[] testInputArray = new String[]{"-d", targetDir.getPath(), "-n", "*.txt", "-m", "-o", "logTest.txt"};
        Args testArgs = new Args(testInputArray, System.out::println);
        boolean result = testArgs.checkSettings();
        assertThat(result, is(true));
    }
}