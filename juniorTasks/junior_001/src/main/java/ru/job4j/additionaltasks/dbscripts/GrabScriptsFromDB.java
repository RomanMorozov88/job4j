package ru.job4j.additionaltasks.dbscripts;

import java.sql.*;
import java.util.Stack;


/**
 * Let's say I have a database of scripts. Each script has an arbitrary number of dependencies.
 * The dependencies are expressed as a list of scriptIds that need to be executed before a given script.
 * There are no circular dependencies.
 * I want to come up with an execution plan so that I can run all of the scripts in a sane order.
 */
public class GrabScriptsFromDB implements AutoCloseable {

    private Connection connection;
    private Stack<String> workStack = new Stack<>();
    /**
     * Рекурсивный запрос, который отправляем базе данных.
     */
    private String generalStatement = "with recursive result_table(script_id, parent_id, script_text) as (\n"
            + "select SCRPTS.script_id, SCRPTS.parent_id, SCRPTS.script_text \n"
            + "from scripts as SCRPTS where SCRPTS.script_id = ?\n"
            + "union\n"
            + "select SCRPTS.script_id, SCRPTS.parent_id, SCRPTS.script_text\n"
            + "from scripts SCRPTS, result_table RTBL\n"
            + "where RTBL.script_id = SCRPTS.parent_id\n"
            + ") select * from result_table;";

    public GrabScriptsFromDB(Connection connection) {
        this.connection = connection;
    }

    /**
     * Результат запроса помещаем в workStack.
     * Таким образом получаем нужную нам последовательность скриптов.
     *
     * @param rootID
     * @throws SQLException
     */
    public void grabber(int rootID) throws SQLException {
        try (PreparedStatement statement = this.connection.prepareStatement(
                this.generalStatement)) {
            statement.setInt(1, rootID);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                this.workStack.push(rs.getString("script_text"));
            }
            rs.close();
        }
    }

    /**
     * Добавление скрипта в базу.
     *
     * @param scriptID
     * @param parentID
     * @param scriptText
     * @throws SQLException
     */
    public void addInTable(int scriptID, int parentID, String scriptText) throws SQLException {
        try (PreparedStatement statement = this.connection.prepareStatement(
                "insert into scripts (script_id, parent_id, script_text) values (?, ?, ?)")) {
            statement.setInt(1, scriptID);
            statement.setInt(2, parentID);
            statement.setString(3, scriptText);
            statement.execute();
        }
    }

    /**
     * Если нужно добавить корневой скрипт без родителя.
     *
     * @param scriptID
     * @param scriptText
     * @throws SQLException
     */
    public void addInTable(int scriptID, String scriptText) throws SQLException {
        try (PreparedStatement statement = this.connection.prepareStatement(
                "insert into scripts (script_id, script_text) values (?, ?)")) {
            statement.setInt(1, scriptID);
            statement.setString(2, scriptText);
            statement.execute();
        }
    }

    /**
     * Проверяем, что нужная таблица существует.
     *
     * @throws SQLException
     */
    public void tableExistCheck() throws SQLException {
        DatabaseMetaData metadata = connection.getMetaData();
        ResultSet resultSet = metadata.getTables(
                null, null, "scripts", null);
        if (!resultSet.next()) {
            try (Statement statement = this.connection.createStatement()) {
                statement.execute("create table scripts(script_id int primary key, "
                        + "parent_id int references scripts(script_id), script_text CHARACTER VARYING(50));");
            }
        }
    }

    /**
     * Будем считать, что тут происходит выполнение скриптов,
     * доставаемых из workStack.
     * По факту же будем получать некую строку в тестовом методе.
     *
     * @return
     */
    public String getScript() {
        return this.workStack.pop();
    }

    /**
     * Проверяем, пуст ли workStack.
     *
     * @return
     */
    public boolean checkWorkStack() {
        return this.workStack.empty();
    }

    @Override
    public void close() throws Exception {
        if (connection != null) {
            connection.close();
        }
    }
}