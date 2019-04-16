package ru.job4j.tracker;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @version $Id$
 * @since 0.1
 */
public class Tracker implements ITracker {
    /**
     * Массив для хранение заявок.
     */
    private final List<Item> items = new ArrayList<>();

    /**
     * Объект RN, необходимый для генерации уникального id
     */
    private static final Random RN = new Random();

    public Item add(Item item) {
        item.setId(this.generateId(this.RN));
        item.setCreate(this.setTimeCreate());
        this.items.add(item);
        return item;
    }

    @Override
    public boolean replace(String id, String newname, String newdescription) {
        boolean result = false;
        Item buffer = this.findById(id);
        if (buffer != null) {
            buffer.setName(newname);
            buffer.setDescription(newdescription);
            result = true;
        }
        return result;
    }

    public boolean delete(String id) {
        boolean result = false;
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getId().equals(id)) {
                items.remove(i);
                result = true;
                break;
            }
        }
        return result;
    }

    public List<Item> findAll() {
        return this.items;
    }

    /**
     * Метод для поиска findByName и findById.
     *
     * @param predicate
     * @return
     */
    public List<Item> find(Predicate<Item> predicate) {
        return this.items.stream()
                .filter(x -> predicate.test(x))
                .collect(Collectors.toList());
    }
    @Override
    public List<Item> findByName(String key) {
        return this.find(x -> x.getName().equals(key));
    }
    @Override
    public Item findById(String id) {
        return this.find(x -> x.getId().equals(id)).get(0);
    }
}