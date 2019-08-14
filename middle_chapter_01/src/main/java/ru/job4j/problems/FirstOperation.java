package ru.job4j.problems;

/**
 * Пример Data Race.
 * Несколько поток получают доступ к общему ресурсу- переменной count.
 * Результат выполнения будет непредсказуемый.
 */
public class FirstOperation {
    private static int count = 0;

    public static void plus() {
        count++;
    }

    public static int getCount() {
        return count;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    FirstOperation.plus();
                }
                System.out.println(FirstOperation.getCount());
            });
            thread.start();
        }
        System.out.println(">>>RESULT: " + FirstOperation.getCount());
    }
}
