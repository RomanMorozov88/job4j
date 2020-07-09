package ru.job4j.map;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Реализовать собственную структуру данных - HashMap.
 * @param <K>
 * @param <V>
 */
public class SimpleHashMap<K, V> implements Iterable<SimpleHashMap.Node> {

    //Размер внутреннего массива.
    private int capacity = 16;
    //Счётчик, необходимый для метода resize().
    private int indexCounter = 0;
    //Счётчик изменений.
    private int modCount = 0;
    //Внутренний массив.
    private Node[] innerTable = new Node[capacity];

    /**
     * Конструктор по умолчанию.
     */
    public SimpleHashMap() {
    }

    /**
     * Конструктор для создания объекта с конкретной
     * начальной ёмкостью внутреннего массива.
     *
     * @param capacity
     */
    public SimpleHashMap(int capacity) {
        this.capacity = capacity;
        this.innerTable = new Node[capacity];
    }

    /**
     * Метод для добавления.
     *
     * @param key
     * @param value
     * @return false - если уже имелся объект с ключом key.
     */
    public boolean insert(K key, V value) {
        //Проверяем- не переполнен ли массив?
        this.resize();
        //Вычисляем индекс (Возможна коллизия- здесь её обработки нет,
        //поэтому элемент, получивший уже занятый индекс обработается, словно у него
        //такой же ключ.)
        int index = indexOf(key);
        if (this.innerTable[index] == null) {
            this.innerTable[index] = new Node(key, value);
            indexCounter++;
            modCount++;
            return true;
        }
        //Если такой ключ уже есть(или произошла коллизия)- просто перезаписывается значение.
        this.innerTable[index].value = value;
        return false;
    }

    /**
     * Метод для получения значения по ключу.
     *
     * @param key
     * @return
     */
    public V get(K key) {
        int index = indexOf(key);
        return this.innerTable[index] != null && this.innerTable[index].getKey().equals(key)
                ? (V) this.innerTable[index].getValue() : null;
    }

    /**
     * Метод для удаления.
     * @param key
     * @return
     */
    public boolean delete(K key) {
        int index = indexOf(key);
        if (this.innerTable[index] != null && this.innerTable[index].getKey().equals(key)) {
            this.innerTable[index] = null;
            indexCounter--;
            modCount++;
            return true;
        }
        return false;
    }

    /**
     * Метод для нахождения индекса, по окоторому поместим входящие данные.
     *
     * @param key
     * @return
     */
    private int indexOf(K key) {
        //return key.hashCode() & (this.capacity - 1);
        int h = key.hashCode();
        h ^= (h >> h);
        return h % this.capacity;
    }

    /**
     * Метод для расширения внутреннего массива innerTable.
     */
    private void resize() {
        int oldCapacity = this.capacity;
        if (this.indexCounter == oldCapacity) {
            if (this.capacity > 0) {
                this.capacity *= 2;
            } else {
                //На случай, если будет создан массив с нулевой длиной.
                this.capacity += 16;
            }
            //Буферный массив с новой длиной.
            Node[] innerTableBuffer = new Node[this.capacity];

            for (int i = 0; i < oldCapacity; i++) {
                Node<K, V> nodeBuffer = this.innerTable[i];
                if (nodeBuffer != null) {
                    //Перекидываем элемент из старого массива в новый,
                    //обновляем индекс(с учётом новой длины массива.)
                    innerTableBuffer[indexOf(nodeBuffer.getKey())] = nodeBuffer;
                }
            }
            //Обновляем workArray.
            this.innerTable = innerTableBuffer;
        }
    }

    /**
     * Итератор.
     *
     * @return
     */
    @Override
    public Iterator<Node> iterator() {
        return new Iterator<Node>() {

            int indexIterator = 0;
            int expectedModCount = modCount;

            @Override
            public boolean hasNext() {
                //Т.к. элементы в массиве распологаются не по порядку- а в разброс- вводим цикл.
                while (indexIterator < capacity) {
                    if (innerTable[indexIterator] != null) {
                        return true;
                    }
                    indexIterator++;
                }
                return false;
            }

            @Override
            public Node next() {
                this.checkForComodification();
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return innerTable[indexIterator++];
            }

            /**
             * Метод, проверяющий, что во время итерации не произошло изменений хранилища.
             */
            final void checkForComodification() {
                if (modCount != expectedModCount) {
                    throw new ConcurrentModificationException();
                }
            }
        };
    }

    /**
     * Класс для хранения пары Ключ-Значение.
     *
     * @param <K> - ключ.
     * @param <V> - значение.
     */
    static class Node<K, V> {
        K key;
        V value;

        Node(K key, V value) {
            this.key = key;
            this.value = value;
        }

        K getKey() {
            return this.key;
        }

        V getValue() {
            return this.value;
        }

        @Override
        public String toString() {
            return "Node{"
                    + "key= "
                    + key + ", value= " + value
                    + '}';
        }
    }
}