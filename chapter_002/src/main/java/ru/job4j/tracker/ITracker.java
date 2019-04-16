package ru.job4j.tracker;

import java.util.List;
import java.util.Random;

public interface ITracker {

    /**
     * Метод реализаущий добавление заявки в хранилище
     *
     * @param item новая заявка
     */
    Item add(Item item);

    /**
     * Метод реализаущий редактирование заявки.
     * Возвращает true если редактирование прошло удачно.
     *
     * @param id какую заявку хотим отредактировать.
     * @param newname новое имя.
     * @param newdescription новое наполнение.
     */
    boolean replace(String id, String newname, String newdescription);

    /**
     * Метод для удаления заявки.
     * Возвращает true если удаление прошло удачно.
     *
     * @param id id удаляемой заявки.
     */
    boolean delete(String id);

    /**
     * Метод для вывода списка всех существующих заявок.
     *
     * @return список всех существующих заявок.
     */
    List<Item> findAll();

    /**
     * Метод для вывода списка заявок с неким именем.
     *
     * @param key имя по которому ищем.
     * @return список всех существующих заявок.
     */
    List<Item> findByName(String key);

    /**
     *  Метод для поиска заявки с нужным id.
     *
     * @param id искомая заявка.
     * @return искомая заявка.
     */
    Item findById(String id);

    /**
     * Метод для установки времени создания заявки.
     *
     * @return время создания в миллисекундах.
     */
    default long setTimeCreate() {
        return System.currentTimeMillis();
    }

    /**
     * Метод генерирует уникальный ключ для заявки.
     * Так как у заявки нет уникальности полей, имени и описание. Для идентификации нам нужен уникальный ключ.
     *
     * @return Уникальный ключ.
     */
    default String generateId(Random rn) {
        return "a" + String.valueOf(rn.nextInt(101));
    }
}