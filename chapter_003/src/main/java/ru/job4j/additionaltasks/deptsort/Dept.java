package ru.job4j.additionaltasks.deptsort;

import java.util.*;

public class Dept {

    /**
     * Имя отдела.
     */
    public String nameDept;

    /**
     * Список имён отдела с указанием вышестоящего отдела(отделов).
     */
    public List<String> subNames = new ArrayList<>();

    /**
     * Конструктор, вызываемый в случае, когда вышестоящих отделов нет.
     *
     * @param nameDept
     */
    public Dept(String nameDept) {
        this.nameDept = nameDept;
        this.subNames.add(nameDept);
    }

    public Dept(String nameDept, List<Dept> upperDepts) {
        this.nameDept = nameDept;
        this.subNamesSet(upperDepts);
    }

    /**
     * Метод для добавления главного отдела в список subName,
     * если возникнет необходимость.
     * @param newUpperDept
     */
    public void upperDeptSet(Dept newUpperDept) {
        newUpperDept.subNames
                .stream()
                .forEach(x -> this.subNames.add(x + "\\" + this.nameDept));
    }

    /**
     * Метод, позволяющий получить имя отдела с вышестоящими отделами(если есть) через знак \.
     * Например: K1\SK1.
     *
     * @return
     */
    private void subNamesSet(List<Dept> inputList) {
        for (Dept d : inputList) {
            d.subNames
                    .stream()
                    .forEach(x -> this.subNames.add(x + "\\" + this.nameDept));
        }
    }
}