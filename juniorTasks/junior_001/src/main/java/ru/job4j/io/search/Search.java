package ru.job4j.io.search;

import java.io.File;
import java.util.*;

/**
 * В этом задании нужно написать метод, который возвращает список всех файлов с конкретным расширением.
 * Метод должен заходить во всех каталоги.
 */
public class Search {
    /**
     * @param parent путь до каталога, с которого нужно осуществлять поиск.
     * @param exts   расширения файлов, которые мы ходим получить.
     * @return
     */
    public static List<File> files(String parent, List<String> exts) {
        File file = new File(parent);
        Queue<File> listForSearch = new LinkedList<>(Arrays.asList(file));

        ArrayList<File> result = new ArrayList<>();

        while (!listForSearch.isEmpty()) {
            File buffer = listForSearch.poll();
            if (buffer.isDirectory()) {
                listForSearch.addAll(Arrays.asList(buffer.listFiles()));
            } else {
                String fileExt = Search.getFileExt(buffer);
                for (String need : exts) {
                    if (fileExt.equals(need)) {
                        result.add(buffer);
                    }
                }
            }
        }
        return result;
    }

    /**
     * Метод для получения имени расширения файла.
     *
     * @param file файл для которого нужно получить имя расширения.
     * @return
     */
    public static String getFileExt(File file) {
        String fileName = file.getName();
        String result = "";
        int indexOfDot = fileName.lastIndexOf(".");
        if (indexOfDot != -1) {
            result = fileName.substring(indexOfDot);
        }
        return result;
    }
}