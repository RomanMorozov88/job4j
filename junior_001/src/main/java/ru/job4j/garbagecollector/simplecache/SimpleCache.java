package ru.job4j.garbagecollector.simplecache;

import java.io.*;
import java.lang.ref.SoftReference;
import java.util.*;

public class SimpleCache implements BaseCache {

    private Map<String, SoftReference<String>> cacheMap = new HashMap<>();
    private File sourceDirectory;


    public SimpleCache(List<String> keys) {
        for (String k : keys) {
            this.cacheMap.put(k, new SoftReference<>(null));
        }
    }

    /**
     * Получаем текст из файла.
     *
     * @param file
     * @return
     * @throws IOException
     */
    private String textFromFile(File file) throws IOException {
        try (
                BufferedReader reader = new BufferedReader(new FileReader(file))
        ) {
            String result = reader.readLine();
            String check = reader.readLine();
            while (check != null) {
                check = reader.readLine();
                result += check;
            }
            return result;
        }
    }

    @Override
    public String getText(String key) throws IOException {
        String result = this.cacheMap.get(key).get();
        if (result == null) {
            File[] files = this.sourceDirectory.listFiles();
            String fileName;
            for (int i = 0; i < files.length; i++) {
                fileName = files[i].getName();
                if (fileName.equals(key)) {
                    result = this.textFromFile(files[i]);
                    this.cacheMap.replace(key, new SoftReference<>(result));
                }
            }
        }
        return result;
    }

    @Override
    public void setSourceDirectory(String path) {
        this.sourceDirectory = new File(path);
    }
}