package ru.job4j.sql.optimisation;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.util.List;

/**
 * Маршалинг Entries.
 */
public class StoreXML {

    private File target;

    public StoreXML(File target) {
        this.target = target;
    }

    /**
     * сохраняет данные из list в файл target.
     *
     * @param list
     * @throws JAXBException
     */
    public void save(List<Entry> list) throws JAXBException {
        Entries result = new Entries(list);
        JAXBContext context = JAXBContext.newInstance(Entries.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(result, this.target);
    }
}