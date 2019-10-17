package ru.job4j.crudservletwebapp.models;

/**
 * Простейшая модель роли.
 */
public class Role {

    private String roleName;
    private boolean access;

    public Role(String name, boolean access) {
        this.roleName = name;
        this.access = access;
    }

    public String getRoleName() {
        return roleName;
    }

    public boolean getAccess() {
        return this.access;
    }
}