package ru.job4j.generic.store;

/**
 * Хранилище для объектов класса User.
 * Наследуется от AbstractStore и расширяет Store.
 * Ограничиваем классом User.
 */
public class UserStore extends AbstractStore<User> {

    /**
     * Конструктор.
     * @param size - размер массива в объекте SimpleArray.
     */
    public UserStore(int size) {
        super(size);
    }
}