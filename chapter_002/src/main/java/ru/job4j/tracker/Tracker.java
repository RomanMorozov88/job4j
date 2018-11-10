package ru.job4j.tracker;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @version $Id$
 * @since 0.1
 */
public class Tracker {
    /**
     * Массив для хранение заявок.
     */
    private final List<Item> items = new ArrayList<>();

    /**
     * Объект RN, необходимый для генерации уникального id
     */
    private static final Random RN = new Random();

    /**
     * Метод реализаущий добавление заявки в хранилище
     *
     * @param item новая заявка
     */
    public Item add(Item item) {
        item.setId(this.generateId());
        item.setCreate(this.setTimeCreate());
        this.items.add(item);
        return item;
    }

    /**
     * Метод для удаления заявки.
     *
     * @param id id удаляемой заявки.
     */
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

    /**
     * Метод для поиска.
     *
     * @param predicate
     * @return
     */
    public List<Item> find(Predicate<Item> predicate) {
        return this.items.stream()
                .filter(x -> predicate.test(x))
                .collect(Collectors.toList());
    }

    /**
     * Метод генерирует уникальный ключ для заявки.
     * Так как у заявки нет уникальности полей, имени и описание. Для идентификации нам нужен уникальный ключ.
     *
     * @return Уникальный ключ.
     */
    private String generateId() {
        return String.valueOf(RN.nextInt(101));
    }

    /**
     * Метод для установки времени создания заявки.
     *
     * @return время создания в миллисекундах.
     */
    private long setTimeCreate() {
        return System.currentTimeMillis();
    }

    /**
     * Метод для вывода списка всех существующих заявок.
     *
     * @return список всех существующих заявок.
     */
    public List<Item> findAll() {
        return this.items;
    }
}