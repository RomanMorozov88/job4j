package ru.job4j.garbagecollector.simplecache;

import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class SimpleCacheTest {

    String path = System.getProperty("java.io.tmpdir");

    public static void writeInFile(String in, File file) {
        try (FileWriter writer = new FileWriter(file, false)) {
            writer.write(in);
            writer.flush();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void setUp() throws IOException {
        File testfile1 = new File(path + "/Names.txt");
        File testfile2 = new File(path + "/Address.txt");
        testfile1.createNewFile();
        testfile2.createNewFile();
        writeInFile("Some text for NAMES.", testfile1);
        writeInFile("Some text for ADDRESS.", testfile2);
    }

    @Test
    public void whenJustRead() throws IOException {
        List<String> setKeys = new ArrayList<>(Arrays.asList("Names.txt", "Address.txt"));
        BaseCache testCache = new SimpleCache(setKeys);
        testCache.setSourceDirectory(path);
        this.setUp();
        String result = testCache.getText("Names.txt");
        assertThat(result, is("Some text for NAMES."));
        result = testCache.getText("Address.txt");
        assertThat(result, is("Some text for ADDRESS."));
    }
}