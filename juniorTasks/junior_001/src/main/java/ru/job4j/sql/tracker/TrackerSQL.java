package ru.job4j.sql.tracker;

import ru.job4j.tracker.ITracker;
import ru.job4j.tracker.Item;

import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;

/**
 * Работа с базой данных.
 */
public class TrackerSQL implements ITracker, AutoCloseable {

    /**
     * Объект RN, необходимый для генерации уникального id
     */
    private static final Random RN = new Random();

    private Connection connection;

    public TrackerSQL() {
    }

    public TrackerSQL(Connection connection) {
        this.connection = connection;
    }

    public boolean init() {
        try (InputStream in = TrackerSQL.class.getClassLoader().getResourceAsStream(
                "trackerprops/app.properties")) {
            Properties config = new Properties();
            config.load(in);
            Class.forName(config.getProperty("driver-class-name"));
            this.connection = DriverManager.getConnection(
                    config.getProperty("url"),
                    config.getProperty("username"),
                    config.getProperty("password")
            );
            this.tableExistCheck();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        return this.connection != null;
    }

    @Override
    public Item add(Item item) {
        item.setId(this.generateId(this.RN));
        item.setCreate(this.setTimeCreate());
        try (
                PreparedStatement statement = this.connection.prepareStatement(
                        "INSERT INTO items (item_id, item_name, item_description, create_date) VALUES (?, ?, ?, ?);")) {
            statement.setString(1, item.getId());
            statement.setString(2, item.getName());
            statement.setString(3, item.getDescription());
            statement.setTimestamp(4, new Timestamp(item.getCreate()));
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return item;
    }

    @Override
    public boolean replace(String id, String newname, String newdescription) {
        int check = 0;
        try (
                PreparedStatement statement = this.connection.prepareStatement(
                        "UPDATE items SET item_name = ?, item_description = ? WHERE item_id = ?;")) {
            statement.setString(1, newname);
            statement.setString(2, newdescription);
            statement.setString(3, id);
            check = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return check != 0;
    }

    @Override
    public boolean delete(String id) {
        int check = 0;
        try (
                PreparedStatement statement = this.connection.prepareStatement(
                        "DELETE FROM items WHERE item_id = ?;")) {
            statement.setString(1, id);
            check = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return check != 0;
    }

    @Override
    public List<Item> findAll() {
        List<Item> result = new ArrayList<>();
        try (
                Statement statement = this.connection.createStatement();
                ResultSet resultSet = statement.executeQuery(
                        "SELECT * FROM items;")) {
            while (resultSet.next()) {
                result.add(setUp(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<Item> findByName(String key) {
        List<Item> result = new ArrayList<>();
        try (
                PreparedStatement statement = this.connection.prepareStatement(
                        "SELECT * FROM items WHERE item_name = ?;")) {
            statement.setString(1, key);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                result.add(setUp(resultSet));
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Item findById(String id) {
        Item buffer = null;
        try (
                PreparedStatement statement = this.connection.prepareStatement(
                        "SELECT * FROM items WHERE item_id = ?;")) {
            statement.setString(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                buffer = setUp(resultSet);
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return buffer;
    }

    /**
     * Закрытие соединения.
     *
     * @throws Exception
     */
    @Override
    public void close() throws Exception {
        if (this.connection != null) {
            this.connection.close();
        }
    }

    /**
     * Проверяем наличие нужной для работы таблицы items в базе данных.
     * Если её нет- создаём.
     *
     * @throws SQLException
     */
    private void tableExistCheck() throws SQLException {
        DatabaseMetaData metadata = connection.getMetaData();
        ResultSet resultSet = metadata.getTables(
                null, null, "items", null);
        if (!resultSet.next()) {
            try (Statement statement = this.connection.createStatement()) {
                statement.execute("CREATE TABLE items(item_id CHARACTER VARYING(10), "
                        + "item_name CHARACTER VARYING(100), item_description CHARACTER VARYING(500), "
                        + "create_date TIMESTAMP );");
            }
        }
    }

    /**
     * При необходимости- с помощью этого метода получаем Item
     * с данными, полученными из таблицы.
     *
     * @param resultSet
     * @return
     * @throws SQLException
     */
    private Item setUp(ResultSet resultSet) throws SQLException {
        Item buffer = new Item();
        buffer.setId(resultSet.getString("item_id"));
        buffer.setName(resultSet.getString("item_name"));
        buffer.setDescription(resultSet.getString("item_description"));
        buffer.setCreate(resultSet.getTimestamp("create_date").getTime());
        return buffer;
    }
}