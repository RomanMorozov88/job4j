package ru.job4j.io.archive;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;
import java.util.zip.ZipOutputStream;

/**
 * Здесь содержится main метод.
 */
public class MainArchive {

    /**
     * Создаём объект класса Archive и Args,
     * так же производим необходимые манипуляции навроде создания ZipOutputStream и т.д.
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {

        Consumer consumer = System.out::println;

        Args checkInit = new Args(args);
        if (!checkInit.argsCheck()) {
            consumer.accept("Неверное переданы аргументы командной строке.");
            System.exit(35);
        }

        List<String> exts = checkInit.excule();

        File startFile = new File(checkInit.directory());
        String cutName = startFile.getParent();

        if (!startFile.exists()) {
            consumer.accept("Такого фаила\\директории не найдено.");
            System.exit(34);
        }

        String outputZip = checkInit.output();
        if (outputZip == null) {
            consumer.accept("Неверно указано имя архива.");
            System.exit(33);
        }

        Archive archive = new Archive(exts);

        try (ZipOutputStream out = new ZipOutputStream(
                new FileOutputStream(checkInit.output()))) {
            if (startFile.isFile()) {
                if (archive.checkExt(startFile, exts)) {
                    archive.addInArchive(startFile, out,
                            startFile.getPath().substring(cutName.length() + 1));
                }
            } else {
                archive.pack(startFile, cutName, out);
            }
        }
        consumer.accept("Архив создан.");
    }
}
