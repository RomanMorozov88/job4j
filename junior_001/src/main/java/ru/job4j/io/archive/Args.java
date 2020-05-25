package ru.job4j.io.archive;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс для проверки аргументов командной строки.
 * Принимаем за обязательный порядок ввода- нулевым элементом является '-d'
 * за которым(вторым элементом) идёт адрес фаила, с которого начинаем архивацию.
 * вторым элементом идёт '-e' - за ним может идти перечисление расширений.
 * на позиции [args.length - 1] находится '-o'. На последней позиции-
 * имя выходящего архива с указанием .zip
 */
public class Args {

    String[] workArgs;
    int argsLength;

    public Args(String[] args) {
        this.workArgs = args;
        this.argsLength = args.length;
    }

    /**
     * -d - directory - которую мы ходим архивировать.
     *
     * @return
     */
    public String directory() {
        return this.workArgs[1];
    }

    /**
     * -e - exclude - расширения, фаилы с которыми не должны попасть в архив.
     *
     * @return
     */
    public List<String> excule() {
        List<String> result = new ArrayList<>();
        for (int i = 3; i < argsLength - 2; i++) {
            result.add(this.workArgs[i]);
        }
        return result;
    }

    /**
     * -o - output - во что мы архивируем.
     *
     * @return
     */
    public String output() {
        String result = this.workArgs[argsLength - 1];
        int indexOfDot = result.lastIndexOf('.');
        if (indexOfDot != -1) {
            String buffer = result.substring(indexOfDot);
            if (!buffer.equals(".zip")) {
                result = null;
            }
        }
        return result;
    }

    /**
     * Проверяем соответствие входящего args необходимым критериям.
     * Строго:
     * Нулевым: -d,
     * Вторым: -e,
     * Пред-последним: -o.
     */
    public boolean argsCheck() {
        boolean result = true;
        if (!this.workArgs[0].equals("-d")
                || !this.workArgs[2].equals("-e")
                || !this.workArgs[argsLength - 2].equals("-o")) {
            result = false;
        }
        return result;
    }
}