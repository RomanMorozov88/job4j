package ru.job4j.additionaltasks.games;

import ru.job4j.additionaltasks.games.tictac.MenuAction;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Consumer;

/**
 * Запуск меню крестиков-ноликов.
 */
public class TicTac implements GameInterface {

    private TicTacGameMenu ttMenu;

    public TicTac() {
        this.ttMenu = new TicTacGameMenu();
    }

    public TicTac(Consumer consumer) {
        this.ttMenu = new TicTacGameMenu(consumer);
    }

    public TicTac(Consumer consumer, Scanner scanner) {
        this.ttMenu = new TicTacGameMenu(consumer, scanner);
    }

    public void setPlayersInMenu(String key) {
        this.ttMenu.setPlayers(key);
    }

    @Override
    public void gameStart() {
        ttMenu.fillMenuCard();

        do {
            ttMenu.showGameMenu();
            int key = ttMenu.convertInputValue();
            MenuAction action = ttMenu.getAction(key);
            if (action != null) {
                action.execution();
            }
        } while (!ttMenu.isExit());
    }

    public static void main(String[] args) {
        Map<String, Consumer> outMap = new HashMap<>();
        outMap.put("console", System.out::println);
        TicTac game;
        if (args.length == 2) {
            game = new TicTac(outMap.getOrDefault(args[0], System.out::println));
            game.setPlayersInMenu(args[1]);
        } else {
            System.out.println("Неверные аргументы. Игра будет создана с настройками по умолчанию");
            game = new TicTac();
        }
        game.gameStart();
    }
}