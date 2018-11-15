package ru.job4j.additionaltasks.deptsort;

import java.util.*;

public class SortingDepts {

    /**
     * Метод для получения иерархического списка всех отделов.
     *
     * @param allDepts   - список отделов.
     * @param comparator - компаратор для сортировки.
     * @return
     */
    List<String> namesAllDepts(List<Dept> allDepts, Comparator comparator) {
        List<String> result = new ArrayList<>();
        for (Dept d : allDepts) {
            result.addAll(d.subNames);
        }
        result.sort(comparator);
        return result;
    }

    /**
     * Компаратор для сортировки по возрастанию.
     */
    static Comparator<String> firstCompar = (o1, o2) -> {
        int nameValue = o1.compareTo(o2);
        int nameLength = Integer.compare(o1.length(), o2.length());
        return nameValue != 0 ? nameValue : nameLength;
    };

    /**
     * Компаратор для сортировки по убыванию.
     * Взял за основу сортировку строк из задания 3. Компаратор для строк.[#73282]
     */
    static Comparator<String> secondCompar = (o1, o2) -> {
        int result = 0;
        int oOneSize = o1.length();
        int oTwoSize = o2.length();

        int lowerSize = oOneSize > oTwoSize ? oTwoSize : oOneSize;
        for (int i = 0; i < lowerSize; i++) {
            result = Integer.compare(o2.charAt(i), o1.charAt(i));
            if (result != 0) {
                break;
            }
        }
        if (result == 0 && oOneSize != oTwoSize) {
            result = Integer.compare(oOneSize, oTwoSize);
        }
        return result;
    };

    /**
     * Компаратор для сортировки по убыванию главных отделов.
     * Управляемые отделы сортируются по возрастанию.
     */
    static Comparator<String> thirdCompar = (o1, o2) -> {
        int firstDeptName = 0;
        //С помощью нижележащих трёх переменных мы вычисляем,
        //до какого символа нам надо сравнивать имена главных отделов.
        int oOneTill = !o1.contains("\\") ? o1.length() : o1.indexOf("\\");
        int oTwoTill = !o2.contains("\\") ? o2.length() : o2.indexOf("\\");
        int indexLimit = oOneTill > oTwoTill ? oTwoTill : oOneTill;
        for (int i = 0; i < indexLimit; i++) {
            firstDeptName = Integer.compare(o2.charAt(i), o1.charAt(i));
            if (firstDeptName != 0) {
                break;
            }
        }
        int nameValue = o1.compareTo(o2);
        int nameLength = Integer.compare(o1.length(), o2.length());
        int valueAndLegth = Integer.compare(nameValue, nameLength);
        return firstDeptName != 0 ? firstDeptName : valueAndLegth;
    };
}