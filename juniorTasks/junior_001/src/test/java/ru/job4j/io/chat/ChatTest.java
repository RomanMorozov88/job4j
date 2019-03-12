package ru.job4j.io.chat;

import org.junit.Before;
import org.junit.Test;

import java.io.*;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class ChatTest {

    String path = System.getProperty("java.io.tmpdir");

    Chat chat;
    FileWriter testAnswerWriter;
    BufferedReader logReader;
    String ansPath;
    String logPath;
    File testAnsw;
    File testLog;

    /**
     * Метод для очистки testLog.
     * @param logFile
     * @throws IOException
     */
    private void clearLog(File logFile) throws IOException {
        if (logFile.exists()) {
            logFile.delete();
            logFile.createNewFile();
        }
    }

    @Before
    public void setUp() throws IOException {
        ansPath = path + "\\testAnswers";
        logPath = path + "\\testLog";

        testAnsw = new File(ansPath);
        testAnsw.createNewFile();
        testLog = new File(logPath);
        clearLog(testLog);

        testAnswerWriter = new FileWriter(testAnsw);
        testAnswerWriter.write("Test. Test. Test. Test.");
        testAnswerWriter.close();

        logReader = new BufferedReader(new FileReader(testLog));
        chat = new Chat(testLog, testAnsw);
    }

    @Test
    public void chatRun() throws IOException {
        String inputString = "старт" + System.lineSeparator()
                + "раз" + System.lineSeparator()
                + "стоп" + System.lineSeparator()
                + "два" + System.lineSeparator()
                + "продолжить" + System.lineSeparator()
                + "три" + System.lineSeparator()
                + "закончить";
        ByteArrayInputStream bIn = new ByteArrayInputStream(inputString.getBytes());
        chat.mainMethod(bIn);

        String logResult = "";
        String buffer;
        while ((buffer = logReader.readLine()) != null) {
            logResult += buffer + System.lineSeparator();
        }
        String expected = "-INPUTPHRASE: старт" + System.lineSeparator()
                + "-ANSWERPHRASE: Test." + System.lineSeparator()
                + "-INPUTPHRASE: раз" + System.lineSeparator()
                + "-ANSWERPHRASE: Test." + System.lineSeparator()
                + "-INPUTPHRASE: стоп" + System.lineSeparator()
                + "-INPUTPHRASE: два" + System.lineSeparator()
                + "-INPUTPHRASE: продолжить" + System.lineSeparator()
                + "-ANSWERPHRASE: Test." + System.lineSeparator()
                + "-INPUTPHRASE: три" + System.lineSeparator()
                + "-ANSWERPHRASE: Test." + System.lineSeparator()
                + "-ERXITPHRASE: закончить" + System.lineSeparator();
        assertThat(logResult, is(expected));
    }
}