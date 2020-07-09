package ru.job4j.generic.store;

/**
 * Хранилище для объектов класса Role.
 * Наследуется от AbstractStore и расширяет Store.
 * Ограничиваем классом Role.
 */
public class RoleStore extends AbstractStore<Role> {

    /**
     * Конструктор.
     * @param size - размер массива в объекте SimpleArray.
     */
    public RoleStore(int size) {
        super(size);
    }
}