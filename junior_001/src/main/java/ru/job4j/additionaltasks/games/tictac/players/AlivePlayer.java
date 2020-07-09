package ru.job4j.additionaltasks.games.tictac.players;

import java.util.Scanner;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Игрок-человек.
 */
public class AlivePlayer implements Player {

    private final Pattern values = Pattern.compile("^(?<FIRST>\\d+)/(?<SECOND>\\d+)$");
    private String name;
    private Scanner scanner;
    private String playerMark;

    public AlivePlayer(String name, String mark, Scanner scanner) {
        this.name = name;
        this.scanner = scanner;
        this.playerMark = " " + mark + " ";
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getMark() {
        return this.playerMark;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Integer[] move() {
        Integer[] result = new Integer[]{null, null};
        String buffer = this.scanner.nextLine();
        Matcher matcher = values.matcher(buffer);
        if (matcher.find()) {
            result[0] = Integer.parseInt(matcher.group("FIRST"));
            result[1] = Integer.parseInt(matcher.group("SECOND"));
        }
        return result;
    }
}
