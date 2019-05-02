package ru.job4j.ood.srp;

public class Calculator {

    public static double add(double first, double second) {
        return first + second;
    }

    public static double subtract(double first, double second) {
        return first - second;
    }

    public static double div(double first, double second) {
        return first / second;
    }

    public static double multiple(double first, double second) {
        return first * second;
    }

    public static double cosinus(double first) {
        double result = Math.toRadians(first);
        result = Math.cos(result);
        return Math.round(result * 100.0) / 100.0;
    }

    public static double sinus(double first) {
        double result = Math.toRadians(first);
        result = Math.sin(result);
        return Math.round(result * 100.0) / 100.0;
    }

    public static double tangents(double first) {
        double result = Math.toRadians(first);
        result = Math.tan(result);
        return Math.round(result * 100.0) / 100.0;
    }

    public static double cotangents(double first) {
        double result = Math.toRadians(first);
        result = 1 / Math.tan(result);
        return Math.round(result * 100.0) / 100.0;
    }

    public static double squareroot(double first) {
        return Math.sqrt(first);
    }
}
