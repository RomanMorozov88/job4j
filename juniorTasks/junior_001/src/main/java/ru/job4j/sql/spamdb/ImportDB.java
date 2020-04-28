package ru.job4j.sql.spamdb;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * У нас появился клиент, который хочет создать базу данных для спамеров.
 * На рынке ему продали диск в котором находятся txt файлы.
 * Формат данных  dump.txt
 * name;email
 * Клиент просит перевести эти файлы в базу данных PostgreSQL.
 * Напишем каркас приложения.
 */
public class ImportDB {

    private static final Logger LOG = LogManager.getLogger(ImportDB.class.getName());

    private Properties cfg;
    private String dump;
    private String delimeter = ";";

    public ImportDB(Properties cfg, String dump) {
        this.cfg = cfg;
        this.dump = dump;
    }

    public List<User> load() throws IOException {
        List<User> users = new ArrayList<>();
        try (BufferedReader rd = new BufferedReader(new FileReader(dump))) {
            rd.lines().forEach(
                    x -> users.add(
                            this.mappingStringArray(x.split(this.delimeter))
                    )
            );
        }
        return users;
    }

    private User mappingStringArray(String[] input) {
        String name = input[0];
        String email = input[1];
        User user = new User(name, email);
        return user;
    }

    public void save(List<User> users) {
        try {
            Class.forName(cfg.getProperty("jdbc.driver"));
        } catch (ClassNotFoundException e) {
            LOG.error("Error: ", e);
        }
        try (Connection cnt = DriverManager.getConnection(
                cfg.getProperty("jdbc.url"),
                cfg.getProperty("jdbc.username"),
                cfg.getProperty("jdbc.password")
        )) {
            if (users.size() > 0) {
                for (User user : users) {
                    try (PreparedStatement ps = cnt.prepareStatement(
                            "insert into users (user_name, user_email) values (?, ?)"
                    )) {
                        ps.setString(1, user.name);
                        ps.setString(2, user.email);
                        ps.execute();
                    }
                }
            }
        } catch (SQLException e) {
            LOG.error("Error: ", e);
        }
    }

    /**
     * модель
     */
    private static class User {
        String name;
        String email;

        public User(String name, String email) {
            this.name = name;
            this.email = email;
        }
    }


    public static void main(String[] args) {
        Properties cfg = new Properties();
        try (FileInputStream in = new FileInputStream(
                "juniorTasks/junior_001/src/main/resources/spamprop/app.properties"
        )) {
            cfg.load(in);
            ImportDB db = new ImportDB(cfg, "juniorTasks/junior_001/src/main/resources/spamprop/dump.txt");
            db.save(db.load());
            LOG.info("> > > Done.");
        } catch (FileNotFoundException e) {
            LOG.error("Error: ", e);
        } catch (IOException e) {
            LOG.error("Error: ", e);
        }
    }
}