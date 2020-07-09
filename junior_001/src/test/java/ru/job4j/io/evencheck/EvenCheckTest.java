package ru.job4j.io.evencheck;

import org.junit.Test;

import java.io.*;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class EvenCheckTest {

    @Test
    public void whenStreamHaveNotOnlyEven() throws IOException {
        //Проверяем 467.
        Integer intValue = 467;
        //Преобразуем в String, что бы затем преобразовать в byte[].
        String convertedIntValue = intValue.toString();
        byte[] inputByte = convertedIntValue.getBytes();

        try (ByteArrayInputStream inputTest = new ByteArrayInputStream(inputByte)) {
            boolean result = EvenCheck.isNumber(inputTest);
            assertThat(result, is(false));
        }
    }

    @Test
    public void whenStreamHaveOnlyEven() throws IOException {
        //Проверяем 4622.
        Integer intValue = 4622;
        //Преобразуем в String, что бы затем преобразовать в byte[].
        String convertedIntValue = intValue.toString();
        byte[] inputByte = convertedIntValue.getBytes();

        try (ByteArrayInputStream inputTest = new ByteArrayInputStream(inputByte)) {
            boolean result = EvenCheck.isNumber(inputTest);
            assertThat(result, is(true));
        }
    }

    @Test
    public void whenTryToPutSomethingElse() throws IOException {
        //Проверяем, что при попытке передать текст результатом будет false.
        String value = "is not a integer.";
        byte[] inputByte = value.getBytes();

        try (ByteArrayInputStream inputTest = new ByteArrayInputStream(inputByte)) {
            boolean result = EvenCheck.isNumber(inputTest);
            assertThat(result, is(false));
        }
    }
}