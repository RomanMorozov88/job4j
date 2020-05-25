package ru.job4j.io.search;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.*;

public class SearchTest {

    String path = System.getProperty("java.io.tmpdir");

    @Test
    public void test() throws IOException {

        List<String> extensions = new ArrayList<>();
        extensions.add(".txt");
        extensions.add(".html");

        String zeroDir = path + "\\zeroDir";

        new File(zeroDir).mkdirs();
        new File(zeroDir + "\\firstDir").mkdirs();
        new File(zeroDir + "\\secondDir").mkdirs();
        new File(zeroDir + "\\firstDir\\thirdDir").mkdirs();

        File fileOne = new File(zeroDir + "\\testOne.txt");
        fileOne.createNewFile();
        File fileTwo = new File(zeroDir + "\\firstDir\\testTwo.html");
        fileTwo.createNewFile();

        List<File> list = Search.files(zeroDir, extensions);

        assertThat(list, containsInAnyOrder(fileOne, fileTwo));
    }
}