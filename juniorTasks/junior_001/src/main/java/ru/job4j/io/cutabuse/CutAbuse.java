package ru.job4j.io.cutabuse;

import java.io.*;

/**
 * 2. Удаление запрещенных слов.
 * Реализовать сервис:
 * <p>
 * void dropAbuses(InputStream in, OutputStream out, String[] abuse)
 * <p>
 * Задан входной символьный поток и выходной символьный поток.
 * Надо удалить все слова, входящие в массив abuse. Важно,
 * все преобразования нужно делать в потоке. Нельзя зачитать весь поток в память,
 * удалить слова и потом записать. Нужно все делать в потоке.
 */
public class CutAbuse {

    /**
     * @param in    входящий поток.
     * @param out   выходящий поток.
     * @param abuse массив запрещённых слов.
     * @throws IOException
     */
    public static void abuseOff(InputStream in, OutputStream out, String[] abuse) throws IOException {
        try (
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                OutputStreamWriter writer = new OutputStreamWriter(out)
        ) {
            String buffer = reader.readLine();

            while (buffer != null) {
                //Проверяем, есть ли в текущем значении buffer какое-либо слово из abuse.
                for (String forDelete : abuse) {
                    //Если есть- заменяем это слово на пустую строку.
                    buffer = buffer.replaceAll(forDelete, "");
                }
                //Записываем в выходящий поток.
                writer.write(buffer);
                //Обновляем buffer.
                buffer = reader.readLine();
            }
        }
    }
}
