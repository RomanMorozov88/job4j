package ru.job4j.bankaccounts;

import java.util.*;

/**
 * Класс User, содержащий информацию о пользователе.
 */
public class User {
    /**
     * Имя пользователя.
     */
    private String name;
    /**
     * Паспорт пользователя.
     */
    private String passport;

    /**
     * Конструктор пользователя.
     *
     * @param name     имя.
     * @param passport пасспорт.
     */
    public User(String name, String passport) {
        this.name = name;
        this.passport = passport;
    }

    public String getName() {
        return this.name;
    }

    public String getPassport() {
        return this.passport;
    }

    /**
     * Методы equals и hashCode переобределены так, что бы считать одинаковыми
     * объекты User с одинаковым полем passport. Поле name не учитывается.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return passport.equals(user.passport);
    }

    @Override
    public int hashCode() {
        return Objects.hash(passport);
    }
}