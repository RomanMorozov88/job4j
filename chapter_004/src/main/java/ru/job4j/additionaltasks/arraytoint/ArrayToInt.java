package ru.job4j.additionaltasks.arraytoint;

import java.util.Arrays;


/**
 * Дан целочисленный массив- необходимо отфильтровать чётные числа,
 * возвести их в квадрат
 * и вернуть сумму всех полученных элементов.
 */
public class ArrayToInt {

    private ArrayToInt() {
    }

    public static Integer calculateFromArray(int[] inputArray) {
        return Arrays
                //Преобразуем массив в поток.
                .stream(inputArray)
                //Отфильтровываем чётные числа.
                .filter(x -> x % 2 == 0)
                //Возводим в квадрат.
                .map(x -> x * x)
                //Возвращаем сумму.
                .reduce((x, y) -> x + y)
                //Если в массиве не было ни одного чётного числа
                //то возвращаем 0.
                .orElse(0);
    }
}