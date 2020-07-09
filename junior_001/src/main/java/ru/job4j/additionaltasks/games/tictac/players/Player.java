package ru.job4j.additionaltasks.games.tictac.players;

/**
 * Игрок.
 */
public interface Player {
    Integer[] move();
    void setName(String name);
    String getMark();
    String getName();
}