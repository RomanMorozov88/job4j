package ru.job4j.additionaltasks.games.tictac.players;

/**
 * Простейший AI
 */
public class SillyRobotPlayer implements Player {

    private String name;
    private String playerMark;
    private int size;

    public SillyRobotPlayer(String name, String mark, int size) {
        this.name = name;
        this.size = size;
        this.playerMark = " " + mark + " ";
    }

    public void setSize(int size) {
        this.size = size;
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
        Integer[] result = new Integer[2];
        result[0] = (int) (Math.random() * size) + 1;
        result[1] = (int) (Math.random() * size) + 1;
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }
}