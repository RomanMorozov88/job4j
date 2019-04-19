package ru.job4j.sql.optimisation;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Рвбота с базой данных.
 */
public class StoreSQL implements AutoCloseable {
    private final Config config;
    private Connection connect;

    public StoreSQL(Config config) {
        this.config = config;
        this.config.init();
    }

    public void initConnection() throws ClassNotFoundException {
        try {
            Class.forName(this.config.get("driver"));
            this.connect = DriverManager.getConnection(
                    this.config.get("url"),
                    this.config.get("username"),
                    this.config.get("password"));
            this.tableExistCheck();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void generate(int size) {
        this.deleteAll();
        try {
            PreparedStatement statement = this.connect.prepareStatement(
                    "INSERT INTO entry (field) VALUES (?)");
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
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    private void deleteAll() {
        try (Statement statement = this.connect.createStatement()) {
            statement.execute("DELETE FROM entry;");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Entry> load() {
        List<Entry> result = new ArrayList<>();
        try {
            Statement statement = this.connect.createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT * FROM entry;");
            while (resultSet.next()) {
                result.add(new Entry(resultSet.getInt("field")));
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
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