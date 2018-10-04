package ru.job4j.bankaccounts.menu;

import ru.job4j.bankaccounts.UsersMap;

public interface UserAction {
    /**
     * Метод возвращает ключ опции.
     * @return ключ
     */
    int key();
    /**
     * Основной метод.
     * @param input объект типа Input
     * @param usermap объект типа UsersMap
     */
    void execute(Input input, UsersMap usermap);
    /**
     * Метод возвращает информацию о данном пункте меню.
     * @return Строка меню
     */
    String info();
}