package ru.job4j.additionaltasks.fileslist;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class SearchFilesTest {

    String path = System.getProperty("java.io.tmpdir");

    SearchFiles searchTest;
    String testRequest;
    File testFile;

    @Before
    public void setUp() throws IOException {
        searchTest = new SearchFiles();
        testFile = new File(path + "\\Searching.txt");
        testFile.createNewFile();
    }

    @After
    public void setOut() {
        testFile.delete();
    }

    @Test
    public void WhenUseSearchByNamePredicateGetTrue() {
        testRequest = "Searching";
        boolean result = searchTest.searchByName.test(testFile, testRequest);
        assertThat(result, is(true));
    }

    @Test
    public void WhenUseSearchByNamePredicateGetFalse() {
        testRequest = "Somethingelse";
        boolean result = searchTest.searchByName.test(testFile, testRequest);
        assertThat(result, is(false));
    }

    @Test
    public void WhenUseSearchByMaskPredicateGetTrue() {
        testRequest = "*.txt";
        boolean result = searchTest.searchByMask.test(testFile, testRequest);
        assertThat(result, is(true));
    }

    @Test
    public void WhenUseSearchByMaskPredicateGetFalse() {
        testRequest = "*.html";
        boolean result = searchTest.searchByMask.test(testFile, testRequest);
        assertThat(result, is(false));
    }

    @Test
    public void WhenUseSearchByRegexPredicateGetTrue() {
        testRequest = ".+ching.+";
        boolean result = searchTest.searchByRegex.test(testFile, testRequest);
        assertThat(result, is(true));
    }

    @Test
    public void WhenUseSearchByRegexPredicateGetFalse() {
        testRequest = ".+wrong[a-z]+";
        boolean result = searchTest.searchByRegex.test(testFile, testRequest);
        assertThat(result, is(false));
    }

    @Test
    public void WhenRunMainMethod() throws IOException {
        searchTest.setNewLog(path + "\\logTest.txt");

        String zeroDir = path + "\\zeroDir";

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
        searchTest.mainSearchMethod(startDir, "*.txt", searchTest.searchByMask);

        BufferedReader logReader = new BufferedReader(new FileReader(path + "\\logTest.txt"));
        String logResult = "";
        String buffer;
        while ((buffer = logReader.readLine()) != null) {
            logResult = logResult + buffer + System.lineSeparator();
        }
        String logExpected = "testOne.txt" + System.lineSeparator();
        assertThat(logResult, is(logExpected));
        startDir.deleteOnExit();
    }
}