package ru.job4j.additionaltasks.fileslist;

import com.google.common.base.Joiner;

import java.io.File;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Этот класс отвечает за проверку входящего массива строк.
 */
public class Args {

    private String[] workArgs;
    private Consumer consumer;

    /**
     * Подсказка, которую выведем при неудачном вводе.
     */
    private String forHelp = Joiner.on(System.lineSeparator()).join(
            "Ключи:",
            "-d-директория в которая начинать поиск.",
            "-n-имя файл,маска,либо регулярное выражение.",
            "-m -искать по маске, -f-полное совпадение имени, -r регулярное выражение.",
            "-o -результат записать в файл.",
            "Пример:",
            "java-jar find.jar -d c:/ -n *.txt -m -o log.txt",
            "Где-то закралась ошибка.",
            "Попробуйте снова:"
    );

    public Args(String[] args, Consumer consumer) {
        this.workArgs = args;
        this.consumer = consumer;
    }

    /**
     * Получаем строку из внутреннего массива строк workArgs.
     *
     * @param index
     * @return
     */
    public String getFromArgsArray(int index) {
        return this.workArgs[index];
    }

    /**
     * Выводим подсказку и даём возможность ввести команду ещё раз.
     */
    public void help() {
        consumer.accept(forHelp);
        this.newTryArgs();
    }

    /**
     * Если ввели что-то неправильно, то пробуем ещё раз.
     */
    private void newTryArgs() {
        Scanner scanner = new Scanner(System.in);
        String newArgs = scanner.nextLine();
        this.workArgs = newArgs.split(" ");
    }

    /**
     * Проверяем, что директория, которую указал пользователь как начальную,
     * это действительно директория.
     *
     * @param name
     * @return
     */
    private boolean startDirControl(String name) {
        boolean result = false;
        File file = new File(name);
        if (file.isDirectory()) {
            result = true;
        }
        return result;
    }

    /**
     * Проверяем, что верно указан ключ для типа поиска.
     *
     * @param testString
     * @return
     */
    private boolean checkKeyForSearchPredicate(String testString) {
        Pattern p = Pattern.compile("^-[mfr]$");
        Matcher m = p.matcher(testString);
        return m.matches();
    }

    /**
     * Проверяем входящий массив строк.
     * К нему унас вполне определённые
     * и жёсткие требования.
     *
     * @return
     */
    public boolean checkSettings() {
        boolean result = true;
        if (this.workArgs.length == 7
                && this.workArgs[0].equals("-d")
                && this.workArgs[2].equals("-n")
                && this.checkKeyForSearchPredicate(this.workArgs[4])
                && this.workArgs[5].equals("-o")
                && this.startDirControl(this.workArgs[1])
                ) {
            result = false;
        }
        return result;
    }
}