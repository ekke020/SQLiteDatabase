package datasource;

import java.sql.*;

import static menu.SqlConstants.*;


public class Connection {

    private static final String CONNECTION_STRING = "jdbc:mysql://localhost:3306/forum_database";
    private static final String CONNECTION_STRING_BEFORE_DATABASE_CREATION = "jdbc:mysql://localhost:3306";

    public java.sql.Connection conn = null;
    private static Connection connection = null;

    private Connection() {
        openConnection();
    }

    private void openConnection() {
        try {
            conn = DriverManager.getConnection(CONNECTION_STRING_BEFORE_DATABASE_CREATION, "root", "root");
            createDatabaseIfNotExists();
            conn = DriverManager.getConnection(CONNECTION_STRING, "root", "root");
            createTablesIfNotExist();
        } catch (SQLException e) {
            System.out.println("Couldn't connect to the database: " + e.getMessage());
            System.exit(0);
            e.printStackTrace();
        }
    }

    public static Connection getInstance() {
        if(connection == null) {
            connection = new Connection();
        }
        return connection;
    }

    private void createDatabaseIfNotExists() throws SQLException {
        try (Statement statement = conn.createStatement()) {
            statement.execute(CREATE_DATABASE_STATEMENT);
        }
    }

    private void createTablesIfNotExist() throws SQLException {
        try (Statement statement = conn.createStatement()) {
            statement.execute(CREATE_POST_TABLE);
            statement.execute(CREATE_USER_TABLE);
            statement.execute(CREATE_COMMENTS_TABLE);
        }
    }

}
