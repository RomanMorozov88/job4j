package ru.job4j.sql.optimisation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.*;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Парсер для суммирования значений поля field объектов Entry,
 * которые вытаскиваются из xml.
 */
public class ParserForOptimisation {

    private static final Logger LOGGER = LogManager.getLogger(ParserForOptimisation.class.getName());
    private Config config;
    private File source;
    private File dest;
    private File scheme;
    private StoreSQL storeSQL;
    private StoreXML storeXML;
    private ConvertXSQT convertXSQT = new ConvertXSQT();

    public ParserForOptimisation() {
        this.config = new Config();
        this.config.init();
        this.source = new File(config.get("source"));
        this.dest = new File(config.get("dest"));
        this.scheme = new File(config.get("scheme"));
        this.storeSQL = new StoreSQL(config);
        this.storeXML = new StoreXML(source);
    }

    /**
     * Основной метод, в котором происходит чтение\запись xml
     * и обработка ошибок.
     * @param size
     * @return
     */
    public int start(int size) {
        int result;
        try {
            storeSQL.initConnection();
        } catch (ClassNotFoundException e) {
            LOGGER.error(e.getMessage(), e);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }

        storeSQL.generate(size);
        List<Entry> list = null;
        try {
            list = storeSQL.load();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
        try {
            storeSQL.close();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        try {
            storeXML.save(list);
        } catch (JAXBException e) {
            LOGGER.error(e.getMessage(), e);
        }
        convertXSQT.convert(source, dest, scheme);

        ParserForOptimisation.Handler mainHandler = new Handler();
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = null;
        try {
            parser = factory.newSAXParser();
        } catch (ParserConfigurationException e) {
            LOGGER.error(e.getMessage(), e);
        } catch (SAXException e) {
            LOGGER.error(e.getMessage(), e);
        }
        try {
            parser.parse(dest, mainHandler);
        } catch (SAXException e) {
            LOGGER.error(e.getMessage(), e);
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
        result = mainHandler.getSum();
        mainHandler.dropSum();
        return result;
    }

    /**
     * Внутренний класс, отвечающий за получение итоговой суммы.
     */
    class Handler extends DefaultHandler {
        int sum = 0;

        public Handler() {
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
}