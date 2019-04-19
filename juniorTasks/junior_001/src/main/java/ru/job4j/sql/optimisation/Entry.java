package ru.job4j.sql.optimisation;

import java.util.Objects;

/**
 * Класс, с которым работаем.
 */
public class Entry {

    private int field;

    public Entry() {
    }

    public Entry(int value) {
        this.field = value;
    }

    public void setField(int value) {
        this.field = value;
    }

    public int getField() {
        return this.field;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Entry entry = (Entry) o;
        return field == entry.field;
    }

    @Override
    public int hashCode() {
        return Objects.hash(field);
    }
}
