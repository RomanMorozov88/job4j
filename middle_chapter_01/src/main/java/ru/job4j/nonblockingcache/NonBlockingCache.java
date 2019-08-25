package ru.job4j.nonblockingcache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class NonBlockingCache {

    private Map<Integer, Base> vault = new ConcurrentHashMap<>();

    public void add(Base model) {
        this.vault.put(model.getId(), model);
    }

    public void update(Base model) {
        model.changeVersion();
        this.vault.computeIfPresent(model.getId(), (id, newModel) -> {
            if (this.vault.get(id).getVersion() + 1 != model.getVersion()) {
                throw new OptimisticException("Version mismatch");
            }
            newModel = model;
            return newModel;
        });
    }

    public void delete(Base model) {
        this.vault.remove(model.getId());
    }

}