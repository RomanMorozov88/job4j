package ru.job4j.crudservletwebapp.persistent;

import ru.job4j.crudservletwebapp.models.Role;

import java.util.HashMap;
import java.util.Map;

public class RolesVault {

    private final static RolesVault INSTANCE = new RolesVault();
    private final Map<String, Role> vault = new HashMap<>();

    private RolesVault() {
        vault.put("admin", new Role("admin", true));
        vault.put("guest", new Role("guest", false));
    }

    public static RolesVault getInstance() {
        return INSTANCE;
    }

    public Map<String, Role> getVault() {
        return this.vault;
    }

    public boolean getRolesAccess(String key) {
        boolean result = false;
        Role buffer = vault.getOrDefault(key, null);
        if (buffer != null) {
            result = buffer.getAccess();
        }
        return result;
    }
}
