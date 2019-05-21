package ru.job4j.ood.menu;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Узел, содержащий в себе соответствующий Action, имя и список дочерних пунктов.
 */
public class MenuNode implements Comparable<MenuNode> {

    private String name;
    private Action action;
    private Set<MenuNode> subMenu = new TreeSet<>();

    public MenuNode(String name, Action action) {
        this.name = name;
        this.action = action;
    }

    public MenuNode(String name, Action action, List<MenuNode> subMenu) {
        this.name = name;
        this.action = action;
        this.subMenu = new TreeSet<>(subMenu);
    }

    public void setInSubMenu(MenuNode node) {
        this.subMenu.add(node);
    }

    public String getMenuName() {
        return this.name;
    }

    public Set<MenuNode> getSubMenu() {
        return this.subMenu;
    }

    public Action getAction() {
        return this.action;
    }

    /**
     * Сравниваем тоько по имени.
     * @param o
     * @return
     */
    @Override
    public int compareTo(MenuNode o) {
        return this.getMenuName().compareTo(o.getMenuName());
    }
}