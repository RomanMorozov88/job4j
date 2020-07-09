package ru.job4j.additionaltasks.games;

import org.junit.Test;

import java.io.*;
import java.util.Scanner;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class TicTacTest {

    private final ByteArrayOutputStream out = new ByteArrayOutputStream();
    private final PrintStream printStream = new PrintStream(out);

    private final String separator = System.lineSeparator();
    private final String menu = new StringBuilder()
            .append("Меню:").append(separator)
            .append("0. Начать игру").append(separator)
            .append("1. Размер поля").append(separator)
            .append("2. Условие победы").append(separator)
            .append("3. Настройка первого игрока").append(separator)
            .append("4. Настройка второго игрока").append(separator)
            .append("5. Выход").append(separator)
            .toString();

    @Test
    public void whenJustExit() {
        String value = "5";
        Scanner scanner = new Scanner(new ByteArrayInputStream(value.getBytes()));
        TicTac testGame = new TicTac(printStream::println, scanner);
        testGame.gameStart();
        assertThat(
                new String(this.out.toByteArray()),
                is(
                        new StringBuilder()
                                .append(menu)
                                .toString()
                )
        );
    }

    @Test
    public void whenSimpleGame() {
        String value = new StringBuilder()
                .append("4").append(separator)
                .append("2").append(separator)
                .append("0").append(separator)
                .append("1/1").append(separator)
                .append("1/3").append(separator)
                .append("2/2").append(separator)
                .append("3/1").append(separator)
                .append("3/3").append(separator)
                .append("5").append(separator)
                .toString();
        Scanner scanner = new Scanner(new ByteArrayInputStream(value.getBytes()));
        TicTac testGame = new TicTac(printStream::println, scanner);
        testGame.gameStart();
        assertThat(
                new String(this.out.toByteArray()),
                is(
                        new StringBuilder()
                                .append(menu)
                                .append("1. Робот").append(separator)
                                .append("2. Человек").append(separator)
                                .append(menu)
                                .append("Формат ввода коориднат- X/Y где").append(separator)
                                .append("X- координата по вертикали, Y- координата по горизонтали.").append(separator)
                                .append(separator)
                                .append(">>>").append(separator)
                                .append(" 0  1  2  3 ").append(separator)
                                .append(" 1  *  *  * ").append(separator)
                                .append(" 2  *  *  * ").append(separator)
                                .append(" 3  *  *  * ").append(separator)
                                .append("Ход Player 1:").append(separator)
                                .append(separator)
                                .append(">>>").append(separator)
                                .append(" 0  1  2  3 ").append(separator)
                                .append(" 1  X  *  * ").append(separator)
                                .append(" 2  *  *  * ").append(separator)
                                .append(" 3  *  *  * ").append(separator)
                                .append("Ход Player 2:").append(separator)
                                .append(separator)
                                .append(">>>").append(separator)
                                .append(" 0  1  2  3 ").append(separator)
                                .append(" 1  X  *  O ").append(separator)
                                .append(" 2  *  *  * ").append(separator)
                                .append(" 3  *  *  * ").append(separator)
                                .append("Ход Player 1:").append(separator)
                                .append(separator)
                                .append(">>>").append(separator)
                                .append(" 0  1  2  3 ").append(separator)
                                .append(" 1  X  *  O ").append(separator)
                                .append(" 2  *  X  * ").append(separator)
                                .append(" 3  *  *  * ").append(separator)
                                .append("Ход Player 2:").append(separator)
                                .append(separator)
                                .append(">>>").append(separator)
                                .append(" 0  1  2  3 ").append(separator)
                                .append(" 1  X  *  O ").append(separator)
                                .append(" 2  *  X  * ").append(separator)
                                .append(" 3  O  *  * ").append(separator)
                                .append("Ход Player 1:").append(separator)
                                .append(separator)
                                .append(">>>").append(separator)
                                .append(" 0  1  2  3 ").append(separator)
                                .append(" 1  X  *  O ").append(separator)
                                .append(" 2  *  X  * ").append(separator)
                                .append(" 3  O  *  X ").append(separator)
                                .append("Player 1 ПОБЕДИЛ!").append(separator)
                                .append(menu)
                                .toString()
                )
        );
    }
}