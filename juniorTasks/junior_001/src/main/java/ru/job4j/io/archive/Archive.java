package ru.job4j.io.archive;

import ru.job4j.io.search.Search;

import java.io.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Архивировать проект.
 * 1. Задана директория проекта, например: c:\project\job4j\
 * 2. В качестве ключей передаются расширения файлов, которые должны попасть в архив.
 * 3. Архив должен сохранять структуру проекта.
 * 4. Запуск проекта java -jar pack.jar -d c:\project\job4j\ -e java.xml -o project.zip
 * <p>
 * -d - directory - которую мы ходим архивировать
 * -e - exclude - исключить файлы *.xml
 * -o - output - во что мы архивируем.
 */
public class Archive {

    List<String> exts;

    public Archive(List<String> exts) {
        this.exts = exts;
    }

    /**
     * Переборка файлов.
     * Предполагаем, что начинаем работу с директории. Иначе- отдельно пропишем в main`e
     * соответствующую конструкцию.
     *
     * @param startFile директория, с которой начинаем архивирование.
     * @param cutName   имя с сохранением иерархии в рамках стартовой директории.
     * @param outZip    выходящий ZipOutputStream.
     * @throws IOException
     */
    public void pack(File startFile, String cutName,
                     ZipOutputStream outZip) throws IOException {

        for (File f : startFile.listFiles()) {
            String nameForArchive = f.getPath().substring(cutName.length() + 1);
            if (f.isDirectory()) {
                if (f.listFiles().length > 0) {
                    pack(f, cutName, outZip);
                }
            } else {
                if (checkExt(f, this.exts)) {
                    addInArchive(f, outZip, nameForArchive);
                }
            }
        }
    }

    /**
     * Запись в архив.
     *
     * @param file фаил для архивации.
     * @param out
     * @param name имя с сохранением иерархии в рамках стартовой директории.
     * @throws IOException
     */
    public void addInArchive(File file, ZipOutputStream out, String name) throws IOException {
        out.putNextEntry(new ZipEntry(name));
        try (FileInputStream in = new FileInputStream(file)) {
            byte[] buffer = new byte[1024];
            while (in.read(buffer) != -1) {
                out.write(buffer);
            }
            out.closeEntry();
        }
    }

    /**
     * Метод для проверки расширения фаила.
     * Используем метод getFileExt() класса Search.
     * @param file входящий фаил.
     * @param exts список запрещённых для архивирования расширений.
     * @return
     */
    public boolean checkExt(File file, List<String> exts) {
        for (String ext : exts) {
            if (Search.getFileExt(file).equals(ext)) {
                return false;
            }
        }
        return true;
    }
}