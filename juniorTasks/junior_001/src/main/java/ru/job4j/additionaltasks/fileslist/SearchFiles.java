package ru.job4j.additionaltasks.fileslist;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.function.BiPredicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 1.Создать программу для поиска файла.
 * 2.Программа должна искать данные в заданном каталоге и подкаталогах.
 * 3.Имя файла может задаваться,целиком,по маске,по регулярному выражение(не обязательно).
 * 4.Программа должна собираться в jar и запускаться через java-jar find.jar-d c:/-n*.txt-m-o log.txt
 * Ключи
 * -d-директория в которая начинать поиск.
 * -n-имя файл,маска,либо регулярное выражение.
 * -m-искать по макс,либо-f-полное совпадение имени.-r регулярное выражение.
 * -o-результат записать в файл.
 * 5.Программа должна записывать результат в файл.
 * 6.В программе должна быть валидация ключей и подсказка.
 */
public class SearchFiles {

    private File outLog;

    public SearchFiles(String logPath) {
        this.outLog = new File(logPath);
    }

    /**
     * Начинаем поиск от начальной директории.
     * Проверяем оп одному из доступных предикейтов
     * и если всё подходит- закидываем имя файла в текстовый лог.
     *
     * @param predicate
     * @throws IOException
     */
    public void mainSearchMethod(File startDirectory,
                                 String searchingName,
                                 BiPredicate<File, String> predicate) throws IOException {
        try (FileWriter logWriter = new FileWriter(this.outLog, true)) {
            for (File f : startDirectory.listFiles()) {
                if (f.isDirectory()) {
                    if (f.listFiles().length > 0) {
                        this.mainSearchMethod(f, searchingName, predicate);
                    }
                } else {
                    if (!f.equals(this.outLog) && predicate.test(f, searchingName)) {
                        logWriter.write(f.getName() + System.lineSeparator());
                    }
                }
            }
        }
    }

    /**
     * Метод для удаления уже существующего лога
     * во избежание накопления лишней\дублирующейся информации.
     */
    public void clearingLog() {
        if (this.outLog.exists()) {
            outLog.delete();
        }
    }

    /**
     * Поиск по имени(полное имя или только имя
     * с указанием расширения (или без).
     */
    public static boolean searchByName(File f, String s) {
        boolean result = false;
        String fullFileName = f.getPath();
        String fileName = f.getName();
        if (fullFileName.equals(s)
                || fileName.contains(s)) {
            result = true;
        }
        return result;
    }

    /**
     * Под поиском по маске подразумеваем
     * поиск по такому шаблону: *.(некое расширение).
     * например *.txt
     */
    public static boolean searchByMask(File f, String s) {
        boolean result = false;
        String fileName = f.getName();
        s = s.substring(1);
        if (fileName.endsWith(s)) {
            result = true;
        }
        return result;
    }

    /**
     * Поиск по регулярному выражению.
     * Где входящая строка уже была задана как образец.
     */
    public static boolean searchByRegex(File f, String s) {
        Pattern pattern = Pattern.compile(s);
        Matcher matcher = pattern.matcher(f.getPath());
        return matcher.matches();
    }
}