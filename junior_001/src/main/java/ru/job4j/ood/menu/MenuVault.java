package ru.job4j.ood.menu;

import java.util.*;
import java.util.function.Consumer;

/**
 * В этом классе храним пункты\подпункты меню.
 */
public class MenuVault {

    private static final String CASCADE = "---";
    private static final String SIGN = ">";

    /**
     * Тут храним корневые пункты меню, не имеющие родителей.
     */
    private Set<MenuNode> rootNodes = new TreeSet<>();
    /**
     * Тут храним все пункты\подпункты меню-
     * необходимо для доступа к Action и для добавления новых подпунктов
     * к нужному пункту.
     */
    private Map<String, MenuNode> points = new HashMap<>();

    private Consumer consumer;

    public MenuVault(Consumer consumer) {
        this.consumer = consumer;
    }

    /**
     * Помещаем пункты в карту
     * и в лист корневых пунктов.
     *
     * @param newPoint
     */
    public void setPoints(MenuNode newPoint) {
        this.rootNodes.add(newPoint);
        this.runOverSubMenu(newPoint);
    }

    /**
     * Помещаем подпункты в карту
     * и в список дочерних пунктов указаного
     * родительского пункта с именем parentName.
     *
     * @param newPoint
     */
    public void setPoints(MenuNode newPoint, String parentName) {
        this.points.get(parentName).setInSubMenu(newPoint);
        this.runOverSubMenu(newPoint);
    }

    /**
     * Рекурсией пробегаемся по пункты\подпункты
     *
     * @param newPoint
     */
    public void runOverSubMenu(MenuNode newPoint) {
        this.points.put(newPoint.getMenuName(), newPoint);
        for (MenuNode m : newPoint.getSubMenu()) {
            this.runOverSubMenu(m);
        }
    }

    /**
     * Выводим меню.
     */
    public void showMenu() {
        for (MenuNode m : this.rootNodes) {
            this.runForShow(m, SIGN);
        }
    }

    /**
     * Рекурсия для вывода имён подпунктов.
     * Нужно для того, что бы подпункты чётко шли в связке
     * с родительскими пунктами меню.
     *
     * @param mn
     * @param sign
     */
    public void runForShow(MenuNode mn, String sign) {
        this.consumer.accept(sign + mn.getMenuName());
        for (MenuNode m : mn.getSubMenu()) {
            this.runForShow(m, CASCADE + sign);
        }
    }

    /**
     * Получаем некое действие, соответствующее имени пункта меню.
     *
     * @param key
     * @return
     */
    public String getActionByName(String key) {
        return this.points.get(key).getAction().someMethod(key);
    }
}