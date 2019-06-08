package ru.job4j.garbagecollector.workgc;

class User {
    public String name;

    public User() {
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("finalize");
    }
}

public class MemoryUsage {

    public static void info() {
        int mb = 1024 * 1024;
        Runtime runtime = Runtime.getRuntime();
        System.out.println(" # # #");
        System.out.println("Total memory: " + runtime.totalMemory() / mb);
        System.out.println("Free memory: " + runtime.freeMemory() / mb);
        System.out.println("Used memory: " + (runtime.totalMemory() - runtime.freeMemory()) / mb);
        System.out.println("Max memory: " + runtime.maxMemory() / mb);
    }

    public static void main(String[] args) {
        info();
        User user;
        for (int i = 0; i < 8200; i++) {
            user = new User();
        }
        info();
    }
}