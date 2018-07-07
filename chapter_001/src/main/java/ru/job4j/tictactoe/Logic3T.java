package ru.job4j.tictactoe;

public class Logic3T {
    private final Figure3T[][] table;

    public Logic3T(Figure3T[][] table) {
        this.table = table;
    }

    public boolean isWinnerX() {
        boolean result = false;

        //Проверка по горизонтали.
        for (int i = 0; i < table.length; i++) {
                if (table[i][0].hasMarkX() && table[i][1].hasMarkX() && table[i][2].hasMarkX()) {
                    result = true;
                }
            }
            if (result) {
            return result;
            }
        // Проверка по вертикали.
        for (int j = 0; j < table.length; j++) {
            if (table[0][j].hasMarkX() && table[1][j].hasMarkX() && table[2][j].hasMarkX()) {
                result = true;
            }
        }
        if (result) {
            return result;
        }
        // Проверка по диагоналям.
        if (table[0][0].hasMarkX() && table[1][1].hasMarkX() && table[2][2].hasMarkX()) {
            result = true;
        } else if (table[0][2].hasMarkX() && table[1][1].hasMarkX() && table[2][0].hasMarkX()) {
            result = true;
        }
        return result;
    }

    public boolean isWinnerO() {
        boolean result = false;

        //Проверка по горизонтали.
        for (int i = 0; i < table.length; i++) {
            if (table[i][0].hasMarkO() && table[i][1].hasMarkO() && table[i][2].hasMarkO()) {
                result = true;
            }
        }
        if (result) {
            return result;
        }
        // Проверка по вертикали.
        for (int j = 0; j < table.length; j++) {
            if (table[0][j].hasMarkO() && table[1][j].hasMarkO() && table[2][j].hasMarkO()) {
                result = true;
            }
        }
        if (result) {
            return result;
        }
        // Проверка по диагоналям.
        if (table[0][0].hasMarkO() && table[1][1].hasMarkO() && table[2][2].hasMarkO()) {
            result = true;
        } else if (table[0][2].hasMarkO() && table[1][1].hasMarkO() && table[2][0].hasMarkO()) {
            result = true;
        }
        return result;
    }

    public boolean hasGap() {

        boolean result = false;

        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table[i].length; j++) {
                if (!table[i][j].hasMarkO()  && !table[i][j].hasMarkX()) {
                    result = true;
                    break;
                }
            }
        }
        return result;
    }
}