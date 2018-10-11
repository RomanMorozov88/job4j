package ru.job4j.additionaltasks;

public class Segments {
    /**
     * @param a точка первого отрезка.
     * @param b точка первого отрезка
     * @param c точка второго отрезка.
     * @param d точка второго отрезка.
     * @return result true - если отрезки пересекаются.
     * result false - если отрезки не пересекаются.
     */
    public boolean predict(int a, int b, int c, int d) {
        boolean result = false;
        if (a < b && c < d) {
            if (a <= d && c <= b) {
                result = true;
            }
        }
        return result;
    }
}