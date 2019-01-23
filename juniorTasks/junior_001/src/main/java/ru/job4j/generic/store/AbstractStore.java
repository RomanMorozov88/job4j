package ru.job4j.generic.store;

import ru.job4j.generic.simplearray.SimpleArray;

import java.util.Iterator;

/**
 * В этом абстрактном классе разместим конструктор,
 * поле класса SimpleArray и метод search- элементы, общие для User и Role.
 *
 * Так как и в UserStore и в RoleStore одинаковый функционал- то реализуем Store
 * именно в AbstractStore что бы избавиться от дублирующего кода.
 * @param <T>
 */
public abstract class AbstractStore<T extends Base> implements Store<T> {

    /**
     * Хранилище SimpleArray.
     */
    protected SimpleArray<T> warehouse;

    /**
     * Конструктор, к которому будут обращаться конструкторы User и Role.
     *
     * @param size
     */
    public AbstractStore(int size) {
        this.warehouse = new SimpleArray<>(size);
    }

    /**
     * Метод для поиска индекса хранящегося в SimpleArray элемента c нужным id,
     * если есть. Если нет- возвращаем null- что бы не прописывать этот же код
     * при поиске не индекса а самого объекта(в методе .findById)
     * (потому используем Integer а не int)
     *
     * @param id
     * @return
     */
    protected Integer search(String id) {
        Integer index = 0;
        Iterator<T> bufferIterator = this.warehouse.iterator();
        while (bufferIterator.hasNext()) {
            T bufferT = bufferIterator.next();
            if (bufferT.getId().equals(id)) {
                return index;
            }
            index++;
        }
        return null;
    }

    @Override
    public void add(T model) {
        this.warehouse.add(model);
    }

    @Override
    public boolean replace(String id, T model) {
        Integer index = this.search(id);
        if (index == null) {
            return false;
        }
        this.warehouse.set(index, model);
        return true;
    }

    @Override
    public boolean delete(String id) {
        Integer index = this.search(id);
        if (index == null) {
            return false;
        }
        this.warehouse.remove(index);
        return true;
    }

    @Override
    public T findById(String id) {
        Integer index = this.search(id);
        if (index == null) {
            return null;
        }
        return this.warehouse.get(index);
    }
}