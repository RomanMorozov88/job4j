package ru.job4j.crudservletwebapp.logic;

import ru.job4j.crudservletwebapp.models.User;
import ru.job4j.crudservletwebapp.persistent.MemoryStore;
import ru.job4j.crudservletwebapp.persistent.Store;

import java.util.List;

public class ValidateService implements Validate {

    private final static ValidateService SERVICE = new ValidateService();
    private final Store store = MemoryStore.getInstance();

    private ValidateService() {
    }

    public static ValidateService getInstance() {
        return SERVICE;
    }

    @Override
    public boolean add(User user) {
        return this.store.add(user);
    }

    @Override
    public boolean update(User user) {
        return this.store.update(user);
    }

    @Override
    public boolean delete(int id) {
        return this.store.delete(id);
    }

    @Override
    public List<User> findAll() {
        return this.store.findAll();
    }

    @Override
    public User findById(int id) {
        return this.store.findById(id);
    }
}