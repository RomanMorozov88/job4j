package ru.job4j.ood.simplegenerator;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class SimpleGeneratorTest {

    @Test
    public void whenGenerateTextOne() {
        String input = "I am a ${name}, Who are ${subject}?";
        Map<String, String> values = new HashMap<>();
        values.put("name", "Petr");
        values.put("subject", "you");
        SimpleGenerator sg = new SimpleGenerator();
        String result = sg.generator(input, values);
        assertThat(result, is("I am a Petr, Who are you?"));
    }

    @Test
    public void whenGenerateTextTwo() {
        String input = "Help, ${sos}, ${sos}, ${sos}";
        Map<String, String> values = new HashMap<>();
        values.put("sos", "Aaa");
        SimpleGenerator sg = new SimpleGenerator();
        String result = sg.generator(input, values);
        assertThat(result, is("Help, Aaa, Aaa, Aaa"));
    }

    @Test(expected = ImbalanceException.class)
    public void whenHaveMoreKeyThanNeed() {
        String input = "Some text for @{test}";
        Map<String, String> values = new HashMap<>();
        values.put("test", "TEST");
        values.put("needless", "needless");
        SimpleGenerator sg = new SimpleGenerator();
        sg.generator(input, values);
    }

    @Test(expected = ImbalanceException.class)
    public void whenHaveNotKey() {
        String input = "Some text for @{test}";
        Map<String, String> values = new HashMap<>();
        values.put("needless", "needless");
        SimpleGenerator sg = new SimpleGenerator();
        sg.generator(input, values);
    }

}