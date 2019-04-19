package ru.job4j.sql.optimisation;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.util.List;
import java.util.Scanner;

/**
 * Запуск.
 */
public class AppMain {

    public static void main(String[] args) throws Exception {
        Config config = new Config();
        config.init();

        File source = new File(config.get("source"));
        File dest = new File(config.get("dest"));
        File scheme = new File(config.get("scheme"));

        StoreSQL storeSQL = new StoreSQL(config);
        storeSQL.initConnection();

        StoreXML storeXML = new StoreXML(source);
        ConvertXSQT convertXSQT = new ConvertXSQT();

        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите количество записей для добавления в базу данных:");
        int size = scanner.nextInt();

        storeSQL.generate(size);
        List<Entry> list = storeSQL.load();
        storeSQL.close();

        storeXML.save(list);

        convertXSQT.convert(source, dest, scheme);

        ParserFoeOptimisation mainParser = new ParserFoeOptimisation();
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();
        try {
            parser.parse(dest, mainParser);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Общая сумма: " + mainParser.getSum());
        mainParser.dropSum();

    }
}