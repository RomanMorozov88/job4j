package ru.job4j.javaxjson.persistent;

import ru.job4j.javaxjson.models.UserForJson;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Простое хранилище.
 */
public class StoreInMap {

    private final static StoreInMap INSTANCE = new StoreInMap();
    private final ConcurrentHashMap<String, UserForJson> innerMap = new ConcurrentHashMap<>();

    /**
     * Сразу добавляем пару объектов для наглядности.
     */
    private StoreInMap() {
        UserForJson user = new UserForJson("FIRST", "SECOND", "male",
                "TestCountry", "TestCity");
        innerMap.put(user.getFirstName(), user);
        user = new UserForJson("FIRST02", "SECOND02", "male",
                "AnotherTestCountry", "AnotherTestCity");
        user.setDescription("DESC");
        innerMap.put(user.getFirstName(), user);
    }

    public static StoreInMap getInstance() {
        return INSTANCE;
    }

    public void add(UserForJson user) {
        this.innerMap.put(user.getFirstName(), user);
    }

    public UserForJson getById(String id) {
        return innerMap.getOrDefault(id, null);
    }

    public List<UserForJson> getAllUsers() {
        List<UserForJson> result = new ArrayList<>();
        result.addAll(this.innerMap.values());
        return result;
    }

}