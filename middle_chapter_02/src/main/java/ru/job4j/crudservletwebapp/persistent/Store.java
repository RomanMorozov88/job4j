package ru.job4j.crudservletwebapp.persistent;

import ru.job4j.crudservletwebapp.models.User;

import java.util.List;

public interface Store {
    boolean add(User user);
    boolean update(User user);
    boolean delete(int id);
    List<User> findAll();
    User findById(int id);
}