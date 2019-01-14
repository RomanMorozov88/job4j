package ru.job4j.iterator.iteratorconvert;

import java.util.*;

/**
 * 5.1.4. Создать convert(Iterator<Iterator>)
 * Реализовать класс с методом Iterator<Integer> convert(Iterator<Iterator<Integer>> it).
 */
public class Converter {
    Iterator<Integer> convert(Iterator<Iterator<Integer>> it) {

        return new Iterator<Integer>() {

            Iterator<Integer> innerIterator = it.next();

            @Override
            public boolean hasNext() {
                //Если в текущем итераторе нет следующего элемента(он закончился или пуст)
                //то перебираем итератор итераторов в поиске следующего не пустого(если есть).
                if (!innerIterator.hasNext()) {
                    while (it.hasNext()) {
                        innerIterator = it.next();
                        //Если находим, то возвращаем true.
                        if (innerIterator.hasNext()) {
                            return true;
                        }
                    }
                }
                //Если элементы есть- true.
                //Если итератор путс(закончился) и он последний в списке- false.
                return innerIterator.hasNext();
            }

            @Override
            public Integer next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return innerIterator.next();
            }
        };
    }
}
