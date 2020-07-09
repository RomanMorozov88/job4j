package ru.job4j.socket.oraclebot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *  Создать бот - мудрый Оракл.
 * Сервер должен отвечать на простые вопросы. Если Оралку сказали пока. То приложение выключается.
 */
public class OracleBotServer {

    private Socket socket;

    //Тут можно поместить объект класса\методы, ответственные за ответы Оракула.
    //Например, простое хранилище AnswerVault с методом выдачи ответа.
    private AnswersVault aV;

    public OracleBotServer(Socket socket, AnswersVault answersVault) {
        this.socket = socket;
        this.aV = answersVault;
    }

    public void startServer() throws IOException {
        try (PrintWriter out = new PrintWriter(this.socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))
        ) {

            String ask = null;

            do {
                System.out.println("wait command ...");
                ask = in.readLine();
                System.out.println(ask);
                if ("Hello, oracle".equals(ask)) {
                    out.println("Hello, dear friend, I'm a oracle.");
                    out.println();
                    out.flush();
                } else if ("exit".equals(ask)) {
                    out.println("Adios!");
                    out.println();
                    out.flush();
                } else {
                    out.println(this.aV.getAnswer());
                    out.println();
                    out.flush();
                }
            } while (!ask.equals("exit"));
        }
    }

    public static void main(String[] args) throws IOException {
        try (Socket socket = new ServerSocket(10000).accept()) {
            OracleBotServer oracleBotServer = new OracleBotServer(socket, new AnswersVault());
            oracleBotServer.startServer();
        }
    }
}