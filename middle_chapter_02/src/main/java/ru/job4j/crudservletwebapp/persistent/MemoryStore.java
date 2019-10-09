package ru.job4j.crudservletwebapp.persistent;

import ru.job4j.crudservletwebapp.models.User;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Всех юзеров храним в потокобезопасной карте.
 */
public class MemoryStore implements Store {

    private final static MemoryStore INSTANCE = new MemoryStore();
    private final ConcurrentHashMap<Integer, User> innerMap = new ConcurrentHashMap<>();

    private MemoryStore() {
    }

    public static MemoryStore getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean add(User user) {
        boolean result = false;
        if (this.innerMap.get(user.getId()) == null) {
            this.innerMap.put(user.getId(), user);
            result = true;
        }
        return result;
    }

    @Override
    public boolean update(User user) {
        boolean result = false;
        User buffer = this.innerMap.getOrDefault(user.getId(), null);
        if (buffer != null) {
            String workString = user.getName();
            if (workString != null) {
                buffer.setName(workString);
            }
            workString = user.getLogin();
            if (workString != null) {
                buffer.setLogin(workString);
            }
            workString = user.getEmail();
            if (workString != null) {
                buffer.setEmail(workString);
            }
            result = true;
        }
        return result;
    }

    @Override
    public boolean delete(int id) {
        return (this.innerMap.remove(id) != null);
    }

    @Override
    public boolean uploadImg(User model) {
        boolean result = false;
        User buffer = this.innerMap.getOrDefault(model.getId(), null);
        if (buffer != null) {
            buffer.setPhotoId(model.getPhotoId());
            result = true;
        }
        return result;
    }

    @Override
    public List<User> findAll() {
        List<User> result = new ArrayList<>();
        result.addAll(this.innerMap.values());
        return result;
    }

    @Override
    public User findById(int id) {
        return this.innerMap.getOrDefault(id, null);
    }
}
