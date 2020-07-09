package ru.job4j.additionaltasks.fileslist;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.function.BiPredicate;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class SearchFilesTest {

    private boolean testSearchFiles(String s, BiPredicate<File, String> predicate) throws IOException {
        String path = System.getProperty("java.io.tmpdir");
        String testRequest = s;
        File testFile = new File(path + "\\Searching.txt");
        testFile.createNewFile();
        return predicate.test(testFile, testRequest);
    }

    @Test
    public void whenUseSearchByNamePredicateGetTrue() throws IOException {
        boolean result = testSearchFiles("Searching", SearchFiles::searchByName);
        assertThat(result, is(true));
    }

    @Test
    public void whenUseSearchByNamePredicateGetFalse() throws IOException {
        boolean result = testSearchFiles("Somethingelse", SearchFiles::searchByName);
        assertThat(result, is(false));
    }

    @Test
    public void whenUseSearchByMaskPredicateGetTrue() throws IOException {
        boolean result = testSearchFiles("*.txt", SearchFiles::searchByMask);
        assertThat(result, is(true));
    }

    @Test
    public void whenUseSearchByMaskPredicateGetFalse() throws IOException {
        boolean result = testSearchFiles("*.html", SearchFiles::searchByMask);
        assertThat(result, is(false));
    }

    @Test
    public void whenUseSearchByRegexPredicateGetTrue() throws IOException {
        boolean result = testSearchFiles(".+ching.+", SearchFiles::searchByRegex);
        assertThat(result, is(true));
    }

    @Test
    public void whenUseSearchByRegexPredicateGetFalse() throws IOException {
        boolean result = testSearchFiles(".+wrong[a-z]+", SearchFiles::searchByRegex);
        assertThat(result, is(false));
    }

    @Test
    public void whenRunMainMethod() throws IOException {

        String path = System.getProperty("java.io.tmpdir");
        String zeroDir = path + "\\zeroDir";

        SearchFiles searchTest = new SearchFiles(path + "\\logTest.txt");

        File startDir = new File(zeroDir);
        startDir.mkdir();
        new File(zeroDir + "\\firstDir").mkdirs();
        new File(zeroDir + "\\secondDir").mkdirs();
        new File(zeroDir + "\\firstDir\\thirdDir").mkdirs();
        File fileOne = new File(zeroDir + "\\testOne.txt");
        fileOne.createNewFile();
        File fileTwo = new File(zeroDir + "\\firstDir\\thirdDir\\testTwo.html");
        fileTwo.createNewFile();

        searchTest.clearingLog();
        searchTest.mainSearchMethod(startDir, "*.txt", SearchFiles::searchByMask);

        BufferedReader logReader = new BufferedReader(new FileReader(path + "\\logTest.txt"));
        StringBuilder logResult = new StringBuilder();
        String buffer;
        while ((buffer = logReader.readLine()) != null) {
            logResult.append(buffer).append(System.lineSeparator());
        }
        String logExpected = "testOne.txt" + System.lineSeparator();
        assertThat(logResult.toString(), is(logExpected));
        startDir.deleteOnExit();
    }
}