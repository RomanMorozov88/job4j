package ru.job4j.additionaltasks.deptsort;

import java.util.*;

public class NameListSet {

    /**
     * Метод для работы с входящим списком депортаментов.
     *
     * @param inputNames - входящий список.
     * @param comparator - правила сортировки.
     * @return - на выходе отсортированный иерархический список со всеми депортаментами.
     */
    public static List<String> nameListSet(List<String> inputNames, Comparator comparator) {
        //Используем этот Set для того, что бы избежать повторения.
        Set<String> namesSet = new HashSet<>();
        //Лист, который мы вернём.
        List<String> result = new ArrayList<>();

        inputNames
                .stream()
                .forEach(x -> {
                    namesSet.add(x);
                    //Подсчитываем, сколько раз в строке встретится символ '\'.
                    long count = x.chars().filter(num -> num == '\\').count();
                    //Индекс, необходимый для обрезки строки имён.
                    int i;
                    //При каждом проходе убираем имя последнего отдела и обновляем.
                    while (count != 0) {
                        i = x.lastIndexOf("\\");
                        x = x.substring(0, i);
                        namesSet.add(x);
                        count--;
                    }
                });
        //Перекидываем из вспомогательного Set`а в окончательный лист.
        result.addAll(namesSet);
        //сортируем.
        result.sort(comparator);
        return result;
    }
}
