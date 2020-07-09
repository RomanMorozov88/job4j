package ru.job4j.additionaltasks.propertiestask;


import java.io.*;
import java.util.Properties;

/**
 *
 * У нас есть файл *.properties.
 * ключ-значение
 * key=value
 * Нужно написать для него класс Config.
 * с методами
 * String get(key)
 * void put(key, value);
 * Метод put Должен добавлять или изменять значение в файле.
 * Например.
 * name=Petr Arsentev
 * put("name", "Ban")
 * name=Ban
 * если ключа нет. то значение должно вставиться.
 */
public class Config {

    private Properties property = new Properties();
    private String propertyPath = "ConfigProperty.properties";

    public Config() {
    }

    public Config(String propertyPath) {
        this.propertyPath = propertyPath;
    }

    public String get(String key) throws IOException {
        try (FileInputStream in = new FileInputStream(this.propertyPath)) {
            property.load(in);
            String result = property.getProperty(key, "Not found.");
            return result;
        }
    }

    public void put(String key, String value) throws IOException {
        try (FileInputStream in = new FileInputStream(this.propertyPath);
             FileOutputStream out = new FileOutputStream(this.propertyPath)
        ) {
            property.load(in);
            if (property.containsKey(key)) {
                property.replace(key, value);
            } else {
                property.setProperty(key, value);
            }
            property.store(out, null);
        }
    }
}