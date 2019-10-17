package ru.job4j.crudservletwebapp.logic;

import ru.job4j.crudservletwebapp.models.User;

import java.util.List;

public interface Validate {
    /**
     * Добавляет пользователя в хранилище.
     *
     * @param user
     * @return
     */
    boolean add(User user);

    /**
     * Редактирование пользователя.
     *
     * @param user
     * @return
     */
    boolean update(User user);

    /**
     * Удаление пользователя.
     *
     * @param id
     * @return
     */
    boolean delete(int id);

    /**
     * Загрузка изобрадения.
     *
     * @param user
     * @return
     */
    boolean uploadImg(User user);

    /**
     * Возвращает всех существующих
     * в хранилище пользователей.
     *
     * @return
     */
    List<User> findAll();

    /**
     * Возвращает пользователя с нужным id.
     *
     * @param id
     * @return
     */
    User findById(int id);

    /**
     * Проверяет- существует ли пользователь с данными
     * логином и паролем. Если существует- возвращает
     * его id.
     *
     * @param login
     * @param password
     * @return
     */
    User isCredentional(String login, String password);
}