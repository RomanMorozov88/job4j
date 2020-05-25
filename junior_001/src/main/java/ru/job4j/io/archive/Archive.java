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
     * Внутри метода используем вызов метода из класса Search.
     *
     * @param startFile директория, с которой начинаем архивирование.
     * @param outZip    выходящий ZipOutputStream.
     * @throws IOException
     */
    public void pack(File startFile,
                     ZipOutputStream outZip) throws IOException {
        List<File> result = Search.files(startFile.getPath(), this.exts);
        String cutName = startFile.getParent();
        for (File f : result) {
            String nameForArchive = f.getPath().substring(cutName.length() + 1);
            addInArchive(f, outZip, nameForArchive);
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
}