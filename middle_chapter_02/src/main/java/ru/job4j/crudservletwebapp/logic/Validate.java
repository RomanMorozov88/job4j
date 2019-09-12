package ru.job4j.crudservletwebapp.logic;

import ru.job4j.crudservletwebapp.models.User;

import java.util.List;

public interface Validate {
    boolean add(User user);
    boolean update(User user);
    boolean delete(int id);
    List<User> findAll();
    User findById(int id);
}