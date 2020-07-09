package ru.job4j.sql.optimisation;

import java.util.Scanner;

/**
 * Запуск.
 */
public class AppMain {

    public static void main(String[] args) throws Exception {
        ParserForOptimisation pfo = new ParserForOptimisation();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите количество записей для добавления в базу данных:");
        int buffer = scanner.nextInt();
        buffer = pfo.start(buffer);
        System.out.println(buffer);
    }
}