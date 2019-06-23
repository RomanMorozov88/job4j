package ru.job4j.garbagecollector.simplecache;

import java.io.IOException;

public interface BaseCache {
    /**
     * Получаем текст по указаному ключу.
     * @param key
     * @return
     * @throws IOException
     */
    String getText(String key) throws IOException;

    /**
     * Устанавливаем адрес директории, откуда будут браться файлы для чтения.
     * @param path
     */
    void setSourceDirectory(String path);
}