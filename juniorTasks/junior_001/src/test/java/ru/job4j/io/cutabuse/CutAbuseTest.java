package ru.job4j.io.cutabuse;

import org.junit.Test;

import java.io.*;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class CutAbuseTest {

    @Test
    public void whenRemoveWordsFromStreamThenDeleted() throws IOException {
        String inputText = "one two three four five";
        String result = " two three  five";
        String[] abuses = {"one", "four"};
        try (
                OutputStream out = new ByteArrayOutputStream();
                InputStream in = new ByteArrayInputStream(inputText.getBytes())
        ) {
            CutAbuse.abuseOff(in, out, abuses);
            assertThat(out.toString(), is(result));
        }
    }
}