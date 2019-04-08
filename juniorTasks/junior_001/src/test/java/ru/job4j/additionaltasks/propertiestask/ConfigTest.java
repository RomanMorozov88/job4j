package ru.job4j.additionaltasks.propertiestask;

import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class ConfigTest {

    public File setUp() throws IOException {
        String path = System.getProperty("java.io.tmpdir");
        File propertyTest = new File(path + "\\propertyTest.properties");
        propertyTest.createNewFile();
        return propertyTest;
    }

    @Test
    public void WhenPutAndEditThenGetValue() throws IOException {
        File testFile = setUp();
        Config config = new Config(testFile.getPath());
        config.put("One", "First Value");
        String result = config.get("One");
        assertThat(result, is("First Value"));
        config.put("One", "New Value");
        result = config.get("One");
        assertThat(result, is("New Value"));
        testFile.deleteOnExit();
    }

    @Test
    public void WhenPutAndGetDefaultValue() throws IOException {
        File testFile = setUp();
        Config config = new Config(testFile.getPath());
        String result = config.get("Two");
        assertThat(result, is("Not found."));
        testFile.deleteOnExit();
    }
}