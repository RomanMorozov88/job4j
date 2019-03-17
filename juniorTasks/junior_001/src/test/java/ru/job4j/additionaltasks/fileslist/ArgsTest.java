package ru.job4j.additionaltasks.fileslist;

import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class ArgsTest {

    String path = System.getProperty("java.io.tmpdir");

    Args testArgs;
    String testInput;
    String[] testInputArray;
    File targetDir;
    String targetDirName;

    @Before
    public void setUp() {
        targetDirName = path + "\\targetTestDir";
        targetDir = new File(targetDirName);
        targetDir.mkdir();
        testInput = "-d " + targetDirName + " -n *.txt -m -o log.txt";
        testInputArray = testInput.split(" ");
        testArgs = new Args(testInputArray, System.out::println);
    }

    @Test
    public void getFromArgsArray() {
        String result = testArgs.getFromArgsArray(0);
        assertThat(result, is("-d"));
    }

    @Test
    public void whenStringArrayIsCorrect() {
        boolean result = testArgs.checkSettings();
        assertThat(result, is(false));
    }

    @Test
    public void whenStringArrayIsUnCorrect() {
        testInput = "-d " + targetDirName + " -n *.txt -e -o log.txt";
        testInputArray = testInput.split(" ");
        testArgs = new Args(testInputArray, System.out::println);
        boolean result = testArgs.checkSettings();
        assertThat(result, is(true));
    }

    @Test
    public void whenDirIsNotexist() {
        targetDir.delete();
        boolean result = testArgs.checkSettings();
        assertThat(result, is(true));
    }
}