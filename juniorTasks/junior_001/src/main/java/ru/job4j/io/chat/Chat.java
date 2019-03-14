package ru.job4j.io.chat;

import com.sun.scenario.Settings;

import java.io.*;
import java.util.*;
import java.util.function.Consumer;

public class Chat {

    private final String STOPWORD = "стоп";
    private final String CONTINUEWORD = "продолжить";
    private final String EXITWORD = "закончить";
    private boolean switchValue = true;
    private Consumer textingOut = System.out::println;

    private File answerFile = new File(this.getPathFromPropertise("Answers_Path"));
    private File log = new File(this.getPathFromPropertise("Log_Path"));

    public Chat() throws IOException {
    }

    /**
     * Коснтруктор для теста.
     */
    public Chat(File newLog, File newAnswers) throws IOException {
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
            String messageFromUser = scanner.nextLine();

            while (!messageFromUser.equals(this.EXITWORD)) {
                this.switcher(messageFromUser);
                logWriter.write("-INPUTPHRASE: " + messageFromUser + System.lineSeparator());
                if (this.switchValue) {
                    raf.seek(this.randomPoint(positionsOfDots));
                    String answer = raf.readLine();
                    answer = answer.substring(0, answer.indexOf('.') + 1);
                    logWriter.write("-ANSWERPHRASE: " + answer + System.lineSeparator());
                    this.textingOut.accept(answer);
                }
                messageFromUser = scanner.nextLine();
            }
            logWriter.write("-ERXITPHRASE: " + messageFromUser + System.lineSeparator());
        }
    }

    /**
     * Проверка входящего сообщения словам stopWord или continueWord.
     * Меняем switchValue.
     *
     * @param string
     */
    private void switcher(String string) {
        if (this.STOPWORD.equals(string)) {
            this.switchValue = false;
        } else if (this.CONTINUEWORD.equals(string)) {
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

    /**
     * Получаем пути для файлов ответов и лога
     * из consoleChat.properties.
     * @param key
     * @return
     * @throws IOException
     */
    private String getPathFromPropertise(String key) throws IOException {
        Properties prs = new Properties();
        ClassLoader loader = Settings.class.getClassLoader();
        try (InputStream in = loader.getResourceAsStream("chatSettings/consoleChat.properties")) {
            prs.load(in);
        }
        return prs.getProperty(key);
    }
}