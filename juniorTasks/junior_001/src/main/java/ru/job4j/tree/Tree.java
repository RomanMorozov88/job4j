package ru.job4j.tree;


import java.util.*;

public class Tree<E extends Comparable<E>> implements SimpleTree<E> {

    //Корень.
    private Node<E> root;

    //Счётчик изменений.
    private int modCount = 0;

    public Tree(E value) {
        this.root = new Node<E>(value);
    }

    /**
     * Метод проверяет количество дочерних элементов в дереве.
     * Если их <= 2 - то дерево бинарное.
     *
     * @return false если количество дочерних элементов больше 2.
     */
    public boolean isBinary() {
        //Делаем те же манипуляции, что и в итераторе за исключением того,
        //что работаем не со значение узла а непосредственно с ним самим.
        Queue<Node<E>> listForIsBinary = new LinkedList<>(Arrays.asList(root));

        boolean result = true;

        while (!listForIsBinary.isEmpty()) {
            Node<E> buffer = listForIsBinary.poll();
            listForIsBinary.addAll(buffer.leaves());
            if (buffer.leaves().size() > 2) {
                result = false;
                break;
            }
        }
        return result;
    }

    @Override
    public boolean add(E parent, E child) {
        //Рабочий буфер.
        Optional<Node<E>> workNode = this.findBy(parent);
        //Проверяем- существует ли parent и нет ли в дереве уже значений равных child.
        //Если всё сходится- добавляем.
        if (workNode.isPresent() && !this.findBy(child).isPresent()) {
            workNode.get().add(new Node<E>(child));
            modCount++;
            return true;
        }
        return false;
    }

    @Override
    public Optional<Node<E>> findBy(E value) {
        Optional<Node<E>> rsl = Optional.empty();
        Queue<Node<E>> data = new LinkedList<>();
        data.offer(this.root);
        while (!data.isEmpty()) {
            Node<E> el = data.poll();
            if (el.eqValue(value)) {
                rsl = Optional.of(el);
                break;
            }
            for (Node<E> child : el.leaves()) {
                data.offer(child);
            }
        }
        return rsl;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            //По аналогии с методом findBy вводим очередь.
            //В самом начале здесь будет содержаться только один элемент root.
            Queue<Node<E>> listForIterator = new LinkedList<>(Arrays.asList(root));

            int expectedModCount = modCount;

            @Override
            public boolean hasNext() {
                return !listForIterator.isEmpty();
            }

            @Override
            public E next() {
                if (!hasNext()) {
                    this.checkForComodification();
                    throw new NoSuchElementException();
                }
                Node<E> result = this.listForIterator.poll();
                //Закидываем в очередь потомков текущего Nod`а.
                listForIterator.addAll(result.leaves());
                return result.getValue();
            }

            final void checkForComodification() {
                if (modCount != expectedModCount) {
                    throw new ConcurrentModificationException();
                }
            }
        };
    }
}
