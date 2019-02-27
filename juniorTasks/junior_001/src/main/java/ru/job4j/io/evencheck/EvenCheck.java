package ru.job4j.io.evencheck;

import java.io.*;
import java.util.Scanner;

/**
 * 1. Проверить байтовый поток.
 * Реализовать сервис:
 * boolean isNumber(InputStream in);
 * Метод должен проверить, что в байтовом потоке записано четное число.
 * Все потоки должны быть обернуты через try-with-resources, даже, если это ByteArrayInputStream.
 */
public class EvenCheck {

    private EvenCheck() {
    }

    /**
     * Метод проверяет- все ли числа в потоке являются чётнымию
     *
     * @param in - входящий ьайтовый поток.
     * @return true- если все числа в потоке чётные.
     * @throws IOException
     */
    public static boolean isNumber(InputStream in) throws IOException {
        //Оборачивем в try-with-resources входящий поток in.
        try (in) {
            Scanner scanner = new Scanner(in);
            //Проверяем, что имеется int значение.
            if (scanner.hasNextInt()) {
                //Проверяем, что это значение чётное.
                if (scanner.nextInt() % 2 == 0) {
                    return true;
                }
            }
            return false;
            //Закрываем блок try.
        }
    }
}