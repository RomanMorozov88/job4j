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

public class OracleBotClientTest {

    private String input;
    private String excepted;

    private final String LS = System.lineSeparator();

    private void testClient(String input, String excepted) throws IOException {
        Socket socket = mock(Socket.class);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());

        when(socket.getInputStream()).thenReturn(in);
        when(socket.getOutputStream()).thenReturn(out);

        OracleBotClient client = new OracleBotClient(socket);

        client.startClient();

        assertThat(out.toString(), is(excepted));
    }

    //Проверяем только случай, когда клиент получает закрывающее программу слово.
    //Клиент успевает отправить одну фразу - её наличие в исходящем потоке и подтверждаем.
    @Test
    public void whenGotAdios() throws IOException {
        input = Joiner.on(LS).join(
                "Adios!",
                "",
                ""
        );
        excepted = Joiner.on(LS).join(
                "Hello, oracle",
                ""
        );
        testClient(input, excepted);
    }
}