package ru.job4j.additionaltasks.fileslist;

import java.io.File;
import java.io.IOException;
import java.util.function.BiPredicate;
import java.util.function.Consumer;

/**
 * main метод, создаём объекты, делаем проверки, запускаем цикл.
 */
public class RunSearchByMain {

    public static void main(String[] args) throws IOException {

        Consumer consumer = System.out::println;

        Args checkInit = new Args(args, consumer);

        while (checkInit.checkSettings()) {
            checkInit.help();
        }

        SearchFiles runSearch = new SearchFiles();

        BiPredicate workPredicate = null;

        switch (checkInit.getFromArgsArray(4)) {
            case "-m":
                workPredicate = runSearch.searchByMask;
                break;
            case "-f":
                workPredicate = runSearch.searchByName;
                break;
            case "-r":
                workPredicate = runSearch.searchByRegex;
                break;
        }

        File startDir = new File(checkInit.getFromArgsArray(1));

        runSearch.setNewLog(checkInit.getFromArgsArray(6));
        runSearch.clearingLog();

        runSearch.mainSearchMethod(startDir, checkInit.getFromArgsArray(3), workPredicate);

       consumer.accept("Done");
    }
}