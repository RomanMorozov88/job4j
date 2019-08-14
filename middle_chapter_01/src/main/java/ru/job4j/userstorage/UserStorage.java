package ru.job4j.userstorage;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Map;

/**
 * 1. Создать класс - структуру данных для хранение пользователей UserStorage.
 * 2. В заголовке класса обозначить аннотацию @ThreadSafe из библиотеки
 * 3. Хранилище должно иметь методы boolean add (User user),
 * boolean update(User user), boolean delete(User user).
 * 4. И особый метод transfer(int fromId, int toId, int amount);
 * 5. Структура данных должна быть потокобезопасная;
 * 6. В структуре User Должны быть поля int id, int amount.
 */
@ThreadSafe
public class UserStorage {

    @GuardedBy("this")
    private final Map<Integer, User> storage = new HashMap<>();

    public synchronized boolean add(User user) {
        return this.storage.putIfAbsent(user.geId(), user) == null;
    }

    public synchronized boolean update(User user) {
        return this.storage.replace(user.geId(), user) != null;
    }

    public synchronized boolean delete(User user) {
        return this.storage.remove(user.geId(), user);
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        boolean result = false;
        User from = this.storage.get(fromId);
        User to = this.storage.get(toId);
        if (from != null
                && to != null) {
            result = this.remittance(from, to, amount);
        }
        return result;
    }

    private boolean remittance(User from, User to, int amount) {
        boolean result = false;
        int buffer;
        if (amount <= from.getAmount()) {
            buffer = from.getAmount();
            from.setAmount(buffer - amount);
            buffer = to.getAmount();
            to.setAmount(buffer + amount);
            result = true;
        }
        return result;
    }
}