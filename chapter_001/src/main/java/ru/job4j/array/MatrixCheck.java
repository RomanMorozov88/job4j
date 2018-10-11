package ru.job4j.array;

public class MatrixCheck {
    public boolean mono(boolean[][] data) {
        boolean result = true;

        int j = data.length - 1;

        for (int i = 0; i < data.length - 1; i++) {
            if (data[i][i] != data[i + 1][i + 1] && data[i][j - i] != data[i + 1][j - i - 1]) {
                result = false;
            }
        }
        return result;
    }
}