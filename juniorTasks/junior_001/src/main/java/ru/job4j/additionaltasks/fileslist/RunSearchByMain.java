package ru.job4j.additionaltasks.fileslist;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
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

        SearchFiles runSearch = new SearchFiles(checkInit.getFromArgsArray(6));
        runSearch.clearingLog();

        File startDir = new File(checkInit.getFromArgsArray(1));

        Map<String, BiPredicate<File, String>> predicateChoiceMap = new HashMap<>();
        predicateChoiceMap.put("-f", SearchFiles::searchByName);
        predicateChoiceMap.put("-m", SearchFiles::searchByMask);
        predicateChoiceMap.put("-r", SearchFiles::searchByRegex);

        runSearch.mainSearchMethod(startDir, checkInit.getFromArgsArray(3),
                predicateChoiceMap.getOrDefault(
                        checkInit.getFromArgsArray(4),
                        (f, s) -> {
                            throw new UnsupportedOperationException("Ошибка с выбором метода поиска.");
                        }
                ));

        consumer.accept("Done");
    }
}