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
    private final Item[] items = new Item[100];

    /**
     * Указатель ячейки для новой заявки.
     */
    private int position = 0;

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
        this.items[this.position++] = item;
        return item;
    }

    /**
     * Метод для удаления заявки.
     * @param id id удаляемой заявки.
     */
    public void delete(String id) {
        for (int i = 0; i < position; i++) {
            if (items[i] != null && items[i].getId().equals(id)) {
                items[i] = null;
                position--;
                break;
            }
        }
    }

    /**
     * Метод реализует поиск по id.
     * @param id id искомой заявки.
     * @return Найденный item
     */
    public Item findById(String id) {
        Item result = null;
        for (Item item : items) {
            if (item != null && item.getId().equals(id)) {
                result = item;
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
    public Item[] findByName(String key) {
        int indx1 = 0;
        for(Item item : items) {
            if (item != null && item.getName().equals(key)) {
                indx1++;
            }
        }
        Item[] result = new Item[indx1];
        int indx2 = 0;
        for(int i =0; i < this.items.length; i++) {
            if (items[i] != null && items[i].getName().equals(key)) {
                result[indx2++] = items[i];
            }
        }
        return result;
    }

    /**
     *
     * @param id id заявки для редактирования.
     * @param item изменения, вносимые в заявку.
     */
    public void replace(String id, Item item) {
        item.setId(id);
        for (int i = 0; i < position; i++) {
            if (items[i] != null && items[i].getId().equals(id)) {
                items[i] = item;
                break;
            }
        }
    }

    /**
     * Метод генерирует уникальный ключ для заявки.
     * Так как у заявки нет уникальности полей, имени и описание. Для идентификации нам нужен уникальный ключ.
     * @return Уникальный ключ.
     */
    private String generateId() {
        return String.valueOf(System.currentTimeMillis() + RN.nextInt());
    }

    /**
     * Метод для вывода списка всех существующих заявок.
     * @return список всех существующих заявок.
     */
    public Item[] findAll() {
        Item[] result = new Item[this.position];
            for(int j = 0; j < this.position;) {
                for (Item item : items) {
                    if (item != null) {
                        result[j] = item;
                        j++;
                    }
                }
            }
        return result;
    }
}