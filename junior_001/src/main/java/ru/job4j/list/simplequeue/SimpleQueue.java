package ru.job4j.list.simplequeue;

import ru.job4j.list.simplestack.SimpleStack;

/**
 * Реализовать очередь.
 * Внутри очереди нужно использовать стеки.
 *
 * @param <T>
 */
public class SimpleQueue<T> {

    //Размер очереди.
    private int size = 0;
    //Счётчик для запуска\игнорирования метода reverse.
    private int check = 0;

    //Внутренние стэки, необходимые для хранения\извлечения элементов очереди.
    private SimpleStack<T> innerStackOne = new SimpleStack<>();
    private SimpleStack<T> innerStackTwo = new SimpleStack<>();

    /**
     * Метод для добавления элемента в очередь.
     *
     * @param value - добавляемое значение.
     */
    public void push(T value) {
        //Помещаем новый элемент в первый внутренний стэк.
        this.innerStackOne.push(value);
        //Увеличиваем значение size.
        this.size++;
    }

    /**
     * Метод для удаления из очереди с получением значения
     * удаляемого элемента.
     *
     * @return
     */
    public T poll() {
        //Проверяем по переменной size, не пустая ли очередь. Если пустая- возвращаем null
        //и не трогаем счётчики.
        if (this.size == 0) {
            return null;
        }
        //Проверяем значение check. Если равно нулю -
        //запускаем метод reverse.
        if (this.check == 0) {
            this.reverse();
        }
        //Уменьшаем check.
        this.check--;
        //Уменьшаем size.
        this.size--;

        return this.innerStackTwo.poll();
    }

    /**
     * Метод для реализации FIFO - first input first output.
     * В этом методе перекидываем элементы из первого внутреннего стэка во второй в обратном порядке,
     * тем самым получая FIFO.
     * Так же инкриминируем check.
     */
    private void reverse() {
        for (int i = 0; i < this.size; i++) {
            T buffer = this.innerStackOne.poll();
            this.innerStackTwo.push(buffer);
            this.check++;
        }
    }
}
