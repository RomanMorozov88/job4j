package ru.job4j.tracker;

import java.util.*;

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
     * Метод реализует поиск по id.
     * @param id id искомой заявки.
     * @return Найденный item
     */
    public Item findById(String id) {
        Item result = null;
        for (Item i : items) {
            if (i.getId().equals(id)) {
                result = i;
                break;
            }
        }
        return result;
    }

    /**
     * Метод реализует поиск по имени.
     * @param key имя искомой заявки.
     * @return Найденный item
     */
    public List<Item> findByName(String key) {
        List<Item> result = new ArrayList<>();
        for (Item i : items) {
            if (i.getName().equals(key)) {
                result.add(i);
            }
        }
        return result;
    }

    /**
     *
     * @param id id заявки для редактирования.
     * @param item изменения, вносимые в заявку.
     */
    public boolean replace(String id, Item item) {
        boolean result = false;
        item.setId(id);
        for (Item i : items) {
            if (i.getId().equals(id)) {
                item.setCreate(i.getCreate());
                items.set(items.indexOf(i), item);
                result = true;
                break;
            }
        }
        return result;
    }

    /**
     * Метод генерирует уникальный ключ для заявки.
     * Так как у заявки нет уникальности полей, имени и описание. Для идентификации нам нужен уникальный ключ.
     * @return Уникальный ключ.
     */
    private String generateId() {
        return String.valueOf(RN.nextInt(101));
    }

    /**
     * Метод для установки времени создания заявки.
     * @return время создания в миллисекундах.
     */
    private long setTimeCreate() {
        return System.currentTimeMillis();
    }

    /**
     * Метод для вывода списка всех существующих заявок.
     * @return список всех существующих заявок.
     */
    public List<Item> findAll() {
        return this.items;
    }
}