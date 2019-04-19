package ru.job4j.sql.optimisation;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Хранилище Entry объектов, необходимое для маршалинга в xml.
 */
@XmlRootElement
public class Entries {

    private List<Entry> values;

    public Entries() {
    }

    public Entries(List<Entry> values) {
        this.values = values;
    }

    public void setValues(List<Entry> values) {
        this.values = values;
    }

    @XmlElement(name = "entry")
    public List<Entry> getValues() {
        return values;
    }
}