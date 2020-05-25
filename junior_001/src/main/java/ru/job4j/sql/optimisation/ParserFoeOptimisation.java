package ru.job4j.sql.optimisation;

import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.*;

/**
 * Парсер для суммирования значений поля field объектов Entry,
 * которые вытаскиваются из xml.
 */
public class ParserFoeOptimisation extends DefaultHandler {

    int sum = 0;

    public ParserFoeOptimisation() {
    }

    public int getSum() {
        return this.sum;
    }

    public void dropSum() {
        this.sum = 0;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        if (qName.equals("entry")) {
            String num = attributes.getValue("href");
            sum += Integer.parseInt(num);
        }
    }
}