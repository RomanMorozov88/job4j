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
        //Создаём фаил с путём parent.
        File file = new File(parent);
        //Рабочая очередь.
        Queue<File> listForSearch = new LinkedList<>(Arrays.asList(file));

        ArrayList<File> result = new ArrayList<>();

        while (!listForSearch.isEmpty()) {
            File buffer = listForSearch.poll();
            //Проверяем- является ли текущее значение buffer директорией.
            if (buffer.isDirectory()) {
                //Если да- закидываем содержимое директории buffer в рабочую очередь.
                listForSearch.addAll(Arrays.asList(buffer.listFiles()));
            } else {
                //Если нет- получаем имя расширения и проверяем,
                // есть ли оно в списке необходимых.
                String fileExt = Search.getFileExt(buffer);
                for (String need : exts) {
                    if (fileExt.equals(need)) {
                        //Если есть- добавляем в result.
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
    private static String getFileExt(File file) {
        //Получаем имя файла с расширением.
        String fileName = file.getName();
        String result = "";
        //Получаем позицию (последней) точки в имени).
        int indexOfDot = fileName.lastIndexOf(".");
        //Проверяем, что она существует.
        if (indexOfDot != -1) {
            //Получаем имя расширения (без точки).
            result = fileName.substring(indexOfDot);
        }
        return result;
    }
}