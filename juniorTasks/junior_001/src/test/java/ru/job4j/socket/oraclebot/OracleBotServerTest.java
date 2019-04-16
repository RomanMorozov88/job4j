package ru.job4j.socket.oraclebot;

import com.google.common.base.Joiner;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.Socket;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OracleBotServerTest {

    private String input;
    private String excepted;

    private final String ls = System.lineSeparator();

    private void testServer(String input, String excepted, AnswersVault answersVault) throws IOException {
        Socket socket = mock(Socket.class);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());

        when(socket.getInputStream()).thenReturn(in);
        when(socket.getOutputStream()).thenReturn(out);
        OracleBotServer server = new OracleBotServer(socket, answersVault);

        server.startServer();

        assertThat(out.toString(), is(excepted));
    }

    @Test
    public void whenSayExitThenAdios() throws IOException {
        input = "exit";
        excepted = Joiner.on(ls).join("Adios!", "", "");
        testServer(input, excepted, new AnswersVault());
    }

    @Test
    public void whenSayHelloThenBackHelloFriend() throws IOException {
        input = Joiner.on(ls).join(
                "Hello, oracle",
                "exit"
        );
        excepted = Joiner.on(ls).join(
                "Hello, dear friend, I'm a oracle.",
                "",
                "Adios!",
                "",
                ""
        );
        testServer(input, excepted, new AnswersVault());
    }

    @Test
    public void whenSaySomethingThenBackYes() throws IOException {
        AnswersVault testVault = mock(AnswersVault.class);
        when(testVault.getAnswer()).thenReturn("Yes.");

        input = Joiner.on(ls).join(
                "some text",
                "exit"
        );
        excepted = Joiner.on(ls).join(
                "Yes.",
                "",
                "Adios!",
                "",
                ""
        );
        testServer(input, excepted, testVault);
    }
}