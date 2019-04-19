package ru.job4j.sql.optimisation;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * Рвбота с базой данных.
 */
public class StoreSQL implements AutoCloseable {
    private static final Logger LOGGER = Logger.getLogger(StoreSQL.class);
    private final Config config;
    private Connection connect;

    public StoreSQL(Config config) {
        this.config = config;
        this.config.init();
    }

    public void initConnection() throws ClassNotFoundException, SQLException {
        Class.forName(this.config.get("driver"));
        this.connect = DriverManager.getConnection(
                this.config.get("url"),
                this.config.get("username"),
                this.config.get("password"));
        this.tableExistCheck();
    }

    public void generate(int size) {
        this.deleteAll();
        try (
                PreparedStatement statement = this.connect.prepareStatement(
                        "INSERT INTO entry (field) VALUES (?)")) {
            this.connect.setAutoCommit(false);
            for (int i = 0; i < size; i++) {
                statement.setInt(1, i);
                statement.addBatch();
            }
            statement.executeBatch();
            this.connect.commit();
        } catch (SQLException e) {
            try {
                this.connect.rollback();
            } catch (SQLException e1) {
                LOGGER.error(e1.getMessage(), e1);
            }
            LOGGER.error(e.getMessage(), e);
        }
    }

    private void deleteAll() {
        try (Statement statement = this.connect.createStatement()) {
            statement.execute("DELETE FROM entry;");
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    public List<Entry> load() throws SQLException {
        List<Entry> result = new ArrayList<>();
        try (Statement statement = this.connect.createStatement();
             ResultSet resultSet = statement.executeQuery(
                     "SELECT * FROM entry;")) {
            while (resultSet.next()) {
                result.add(new Entry(resultSet.getInt("field")));
            }
        }
        return result;
    }

    @Override
    public void close() throws Exception {
        if (connect != null) {
            connect.close();
        }
    }

    /**
     * Проверяем наличие нужной для работы таблицы в базе данных.
     * Если её нет- создаём.
     *
     * @throws SQLException
     */
    private void tableExistCheck() throws SQLException {
        DatabaseMetaData metadata = this.connect.getMetaData();
        ResultSet resultSet = metadata.getTables(
                null, null, "entry", null);
        if (!resultSet.next()) {
            try (Statement statement = this.connect.createStatement()) {
                statement.execute("CREATE TABLE entry(field INTEGER);");
            }
        }
    }
}