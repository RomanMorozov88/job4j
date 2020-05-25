package ru.job4j.additionaltasks.games;

import ru.job4j.additionaltasks.games.tictac.TicTacGameLogic;
import ru.job4j.additionaltasks.games.tictac.MenuAction;
import ru.job4j.additionaltasks.games.tictac.players.AlivePlayer;
import ru.job4j.additionaltasks.games.tictac.players.Player;
import ru.job4j.additionaltasks.games.tictac.players.SillyRobotPlayer;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Consumer;

/**
 * Тут содержится меню со всеми необходимыми действиями.
 */
public class TicTacGameMenu {

    public Scanner scanner = new Scanner(System.in);
    private Consumer consumer = System.out::println;
    private Map<Integer, MenuAction> settings = new HashMap<>();
    private TicTacGameLogic game;
    private boolean exit = false;

    public TicTacGameMenu() {
        this.game = new TicTacGameLogic(this.scanner);
    }

    public TicTacGameMenu(Consumer consumer) {
        this.consumer = consumer;
        this.game = new TicTacGameLogic(this.scanner);
    }

    public TicTacGameMenu(Consumer consumer, Scanner scanner) {
        this.consumer = consumer;
        this.scanner = scanner;
        this.game = new TicTacGameLogic(this.scanner);
    }

    /**
     * Заполняем меню.
     */
    public void fillMenuCard() {
        this.settings.put(0, new StartGameAction("Начать игру"));
        this.settings.put(1, new SetSizeAction("Размер поля"));
        this.settings.put(2, new SetVictoryConditionAction("Условие победы"));
        this.settings.put(3, new SetPlayerOneAction("Настройка первого игрока"));
        this.settings.put(4, new SetPlayerTwoAction("Настройка второго игрока"));
        this.settings.put(5, new SetExitAction("Выход"));
    }

    /**
     * Показываем меню.
     */
    public void showGameMenu() {
        this.consumer.accept("Меню:");
        for (Integer i : this.settings.keySet()) {
            this.consumer.accept(i + ". " + this.settings.get(i).getName());
        }
    }

    /**
     * Проверяем входящие данные из сканера.
     *
     * @return
     */
    public int convertInputValue() {
        String input = this.scanner.nextLine();
        int result = -1;
        if (input.matches("^\\d+$")) {
            result = Integer.parseInt(input);
        }
        return result;
    }

    /**
     * Получаем действие из меню.
     *
     * @param key
     * @return
     */
    public MenuAction getAction(int key) {
        return this.settings.get(key);
    }

    public boolean isExit() {
        return exit;
    }

    /**
     * Выход.
     */
    private class SetExitAction implements MenuAction {
        private String name;

        public SetExitAction(String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return this.name;
        }

        @Override
        public void execution() {
            exit = true;
        }
    }

    /**
     * Настройка размера игрового поля.
     */
    private class SetSizeAction implements MenuAction {
        private String name;

        public SetSizeAction(String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return this.name;
        }

        @Override
        public void execution() {
            consumer.accept("Введите размер игрового поля (min 3) ");
            int fieldSize;
            boolean check;
            do {
                fieldSize = convertInputValue();
                if (fieldSize >= 3) {
                    game.setSize(fieldSize);
                    game.resetVictoryCondition();
                    check = false;
                } else {
                    consumer.accept("Некорректное значение.");
                    check = true;
                }
            } while (check);
        }
    }

    /**
     * Настройка условия победы.
     */
    private class SetVictoryConditionAction implements MenuAction {
        private String name;

        public SetVictoryConditionAction(String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return this.name;
        }

        @Override
        public void execution() {
            consumer.accept("Введите условие победы (от 3 до " + (game.getSize() - 1)
                    + " включительно.");
            boolean check;
            int value;
            do {
                value = convertInputValue();
                if (value >= 3 && value < game.getSize()) {
                    game.setVictoryCondition(value);
                    check = false;
                } else {
                    consumer.accept("Некорректное значение.");
                    check = true;
                }
            } while (check);
        }
    }


    /**
     * Общий метод для настройки игроков.
     *
     * @param playerConsumer
     * @param name
     * @param mark
     */
    private void setPlayerGeneral(Consumer<Player> playerConsumer, String name, String mark) {
        consumer.accept("1. Робот");
        consumer.accept("2. Человек");
        int key;
        boolean check;
        do {
            key = convertInputValue();
            if (key == 1) {
                playerConsumer.accept(new SillyRobotPlayer(name, mark, game.getSize() - 1));
                check = false;
            } else if (key == 2) {
                playerConsumer.accept(new AlivePlayer(name, mark, scanner));
                check = false;
            } else {
                consumer.accept("Неверно выбран пункт.");
                check = true;
            }
        } while (check);
    }

    /**
     * Настройка игрока 1.
     */
    private class SetPlayerOneAction implements MenuAction {
        private String name;

        public SetPlayerOneAction(String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return this.name;
        }

        @Override
        public void execution() {
            setPlayerGeneral(game::setPlayer01, "Player 1", "X");
        }
    }

    /**
     * Настройка игрока 2.
     */
    private class SetPlayerTwoAction implements MenuAction {
        private String name;

        public SetPlayerTwoAction(String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return this.name;
        }

        @Override
        public void execution() {
            setPlayerGeneral(game::setPlayer02, "Player 2", "O");
        }
    }

    /**
     * Если получаем ключи из String[] args
     * @param key
     */
    public void setPlayers(String key) {
        if (key.equals("bots")) {
            this.game.setPlayer01(new SillyRobotPlayer("Player 1", "X", game.getSize() - 1));
        } else if (key.equals("humans")) {
            this.game.setPlayer02(new AlivePlayer("Player 2", "O", scanner));
        }
    }

    /**
     * Запуск самой игры.
     */
    private class StartGameAction implements MenuAction {
        private String name;

        public StartGameAction(String name) {
            this.name = name;
        }

        public void showGameField() {
            StringBuilder bufferLine;
            String[][] field = game.getGameField();
            consumer.accept(System.lineSeparator() + ">>>");
            for (int i = 0; i < game.getSize(); i++) {
                bufferLine = new StringBuilder();
                for (int j = 0; j < game.getSize(); j++) {
                    bufferLine.append(field[i][j]);
                }
                consumer.accept(bufferLine);
            }
        }

        private boolean step(Player player) {
            boolean result;
            consumer.accept("Ход " + player.getName() + ":");
            if (!game.checkForEmptyCell()) {
                consumer.accept("На поле не осталось пустых ячеек.");
                return true;
            }
            boolean moveCheck;
            do {
                moveCheck = game.getMove(player);
                if (!moveCheck) {
                    consumer.accept("Неверные координаты. Ещё раз: ");
                }
            } while (!moveCheck);
            this.showGameField();
            result = game.win(player.getMark());
            if (result) {
                consumer.accept(player.getName() + " ПОБЕДИЛ!");
                result = true;
            }
            return result;
        }

        @Override
        public String getName() {
            return this.name;
        }

        @Override
        public void execution() {
            game.initGameField();
            consumer.accept("Формат ввода коориднат- X/Y где");
            consumer.accept("X- координата по вертикали, Y- координата по горизонтали.");
            this.showGameField();
            boolean check;

            do {
                check = this.step(game.getPlayer01());
                if (!check) {
                    check = this.step(game.getPlayer02());
                }
            } while (!check);
        }
    }
}
