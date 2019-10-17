package ru.job4j.crudservletwebapp.persistent;

import org.apache.commons.dbcp2.BasicDataSource;
import ru.job4j.crudservletwebapp.models.User;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Работа с БД при помоощи пула оединений.
 */
public class DbStore implements Store {
    private static final BasicDataSource SOURCE = new BasicDataSource();
    private static final DbStore INSTANCE = new DbStore();

    private DbStore() {
        SOURCE.setDriverClassName("org.postgresql.Driver");
        SOURCE.setUrl("jdbc:postgresql://127.0.0.1:5432/UStore");
        SOURCE.setUsername("postgres");
        SOURCE.setPassword("password");
        SOURCE.setMinIdle(5);
        SOURCE.setMaxIdle(10);
        SOURCE.setMaxOpenPreparedStatements(100);
    }

    public static DbStore getInstance() {
        try {
            INSTANCE.tableExistCheck();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return INSTANCE;
    }

    @Override
    public boolean add(User model) {
        boolean result = false;
        try (
                Connection connection = SOURCE.getConnection();
                PreparedStatement statement = connection.prepareStatement(
                        "INSERT INTO dbusers VALUES (?, ?, ?, ?, ?, ?, ?, ?);"
                )
        ) {
            statement.setInt(1, model.getId());
            statement.setString(2, model.getName());
            statement.setString(3, model.getLogin());
            statement.setString(4, model.getPassword());
            statement.setString(5, model.getEmail());
            statement.setTimestamp(6, Timestamp.valueOf(model.getCreateDate()));
            statement.setString(7, model.getPhotoId());
            statement.setString(8, model.getRolename());
            result = !statement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean update(User model) {
        boolean result = false;
        try (
                Connection connection = SOURCE.getConnection();
                PreparedStatement statement = connection.prepareStatement(
                        "UPDATE dbusers "
                                + "SET user_name = ?, user_login = ?, user_password = ?, user_email = ?, user_role = ? "
                                + "WHERE user_id = ?;"

                )
        ) {
            statement.setString(1, model.getName());
            statement.setString(2, model.getLogin());
            statement.setString(3, model.getPassword());
            statement.setString(4, model.getEmail());
            statement.setString(5, model.getRolename());
            statement.setInt(6, model.getId());
            result = !statement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean delete(int id) {
        boolean result = false;
        try (
                Connection connection = SOURCE.getConnection();
                PreparedStatement statement = connection.prepareStatement(
                        "DELETE FROM dbusers WHERE user_id = ?;"
                )
        ) {
            statement.setInt(1, id);
            result = !statement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean uploadImg(User model) {
        boolean result = false;
        try (
                Connection connection = SOURCE.getConnection();
                PreparedStatement statement = connection.prepareStatement(
                        "UPDATE dbusers SET photoId = ? WHERE user_id = ?;"

                )
        ) {
            statement.setString(1, model.getPhotoId());
            statement.setInt(2, model.getId());
            result = !statement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<User> findAll() {
        List<User> result = new ArrayList<>();
        try (
                Connection connection = SOURCE.getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(
                        "SELECT * FROM dbusers;")
        ) {
            while (resultSet.next()) {
                result.add(setUp(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public User findById(int id) {
        User result = null;
        try (
                Connection connection = SOURCE.getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(
                        String.format("SELECT * FROM dbusers WHERE user_id = %d;", id)
                )
        ) {
            while (resultSet.next()) {
                result = setUp(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Проверяем наличие нужной для работы таблицы vacancy в базе данных.
     * Если её нет- создаём.
     *
     * @throws SQLException
     */
    private void tableExistCheck() throws SQLException {
        try (
                Connection connection = SOURCE.getConnection();
                Statement statement = connection.createStatement()
        ) {
            statement.execute(
                    "CREATE TABLE IF NOT EXISTS dbusers"
                            + "(user_id INTEGER primary key,"
                            + "user_name VARCHAR, user_login VARCHAR NOT NULL UNIQUE,"
                            + "user_password VARCHAR,"
                            + "user_email VARCHAR, create_date TIMESTAMP,"
                            + "photoId VARCHAR, user_role VARCHAR);"
            );
        }
    }

    /**
     * При необходимости- с помощью этого метода получаем User
     * с данными, полученными из таблицы.
     *
     * @param resultSet
     * @return
     * @throws SQLException
     */
    private User setUp(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("user_id");
        String name = resultSet.getString("user_name");
        String login = resultSet.getString("user_login");
        String password = resultSet.getString("user_password");
        String email = resultSet.getString("user_email");
        String rolename = resultSet.getString("user_role");
        long bufferTime = resultSet.getTimestamp("create_date").getTime();
        String photoId = resultSet.getString("photoId");
        LocalDateTime createDate = new Timestamp(bufferTime).toLocalDateTime();
        User result = new User(id, name, login, password, email, createDate, rolename);
        result.setPhotoId(photoId);
        return result;
    }
}