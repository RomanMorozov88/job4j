package ru.job4j.io.chat;

import java.io.*;
import java.util.*;
import java.util.function.Consumer;

public class Chat {

    private String stopWord = "стоп";
    private String continueWord = "продолжить";
    private String exitWord = "закончить";
    private boolean switchValue = true;
    private Consumer textingOut = System.out::println;

    private File answerFile =
            new File("juniorTasks\\junior_001\\src\\main\\java\\ru\\job4j\\io\\chat\\answers.txt");
    private File log =
            new File("juniorTasks\\junior_001\\src\\main\\java\\ru\\job4j\\io\\chat\\log.txt");

    public Chat() {
    }

    /**
     * Коснтруктор для теста.
     */
    public Chat(File newLog, File newAnswers) {
        this.answerFile = newAnswers;
        this.log = newLog;
    }

    /**
     * Метод, в котором находится цикл с чатом, запись вопросов-ответов,
     * проверка условий молчания\выхода.
     *
     * @param
     * @throws IOException
     */
    public void mainMethod(InputStream in) throws IOException {

        try (FileWriter logWriter = new FileWriter(this.log, true);
             RandomAccessFile raf = new RandomAccessFile(this.answerFile, "r")
        ) {
            Scanner scanner = new Scanner(in);
            List<Integer> positionsOfDots = this.setIndexOfDots();
            String inputString = scanner.nextLine();

            while (!inputString.equals(this.exitWord)) {
                this.switcher(inputString);
                logWriter.write("-INPUTPHRASE: " + inputString + System.lineSeparator());
                if (this.switchValue) {
                    raf.seek(this.randomPoint(positionsOfDots));
                    String answer = raf.readLine();
                    answer = answer.substring(0, answer.indexOf('.') + 1);
                    logWriter.write("-ANSWERPHRASE: " + answer + System.lineSeparator());
                    this.textingOut.accept(answer);
                }
                inputString = scanner.nextLine();
            }
            logWriter.write("-ERXITPHRASE: " + inputString + System.lineSeparator());
        }
    }

    /**
     * Проверка входящего сообщения словам stopWord или continueWord.
     * Меняем switchValue.
     *
     * @param string
     */
    private void switcher(String string) {
        if (string.equals(this.stopWord)) {
            this.switchValue = false;
        } else if (string.equals(this.continueWord)) {
            this.switchValue = true;
        }
    }

    /**
     * Метод для получения случайного индекса для метода .seek
     *
     * @return
     * @throws IOException
     */
    private int randomPoint(List<Integer> dots) {
        int max = dots.size();
        int index = (int) (Math.random() * max);
        return dots.get(index);
    }

    /**
     * Смотрим, на каких позициях находятся точки и записываем позиции первых букв слов,
     * находящихся после точек.
     * Так же добавляем нулевую позицию.
     * Выкидываем позицию самой последней точки.
     * Не учитываем пунктуационные изыски вроде многоточия, восклицательного, вопросительного знаков.
     * Потому возможны ответы в виде пустой строки.
     *
     * @return
     * @throws IOException
     */
    private List<Integer> setIndexOfDots() throws IOException {
        List<Integer> indexs = new ArrayList<>();
        FileInputStream inAnswer = new FileInputStream(this.answerFile);
        byte[] array = new byte[inAnswer.available()];
        inAnswer.read(array);
        for (int i = 0; i < array.length; i++) {
            if (array[i] == '.') {
                indexs.add(i + 2);
            }
        }
        if (indexs.size() > 1) {
            indexs.remove(indexs.size() - 1);
        }
        indexs.add(0);
        return indexs;
    }
}