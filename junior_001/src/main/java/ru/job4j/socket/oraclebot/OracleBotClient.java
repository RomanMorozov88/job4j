package ru.job4j.socket.oraclebot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

/**
 *  Создать бот - мудрый Оракл.
 *  Клиент.
 */
public class OracleBotClient {

    private Socket socket;

    public OracleBotClient(Socket socket) {
        this.socket = socket;
    }

    public void startClient() throws IOException {
        try (
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                Scanner console = new Scanner(System.in)
        ) {

            String str = in.readLine();
            boolean interrupt = false;

            out.println("Hello, oracle");
            do {
                while (!str.isEmpty()) {
                    if ("Adios!".equals(str)) {
                        interrupt = true;
                    }
                    System.out.println(str);
                    str = in.readLine();
                }
                if (interrupt) {
                    break;
                }
                str = console.nextLine();
                out.println(str);
                out.flush();
            } while (true);
        }
    }

    public static void main(String[] args) throws IOException {
        try (Socket socket = new Socket(InetAddress.getByName("127.0.0.1"), 10000)) {
            OracleBotClient oracleBotClient = new OracleBotClient(socket);
            oracleBotClient.startClient();
        }
    }
}