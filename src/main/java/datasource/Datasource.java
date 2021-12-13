package datasource;

import java.sql.*;

import static menu.SqlConstants.*;


public class Datasource {

    private static final String CONNECTION_STRING = "jdbc:mysql://localhost:3306/forum_database";
    private static final String CONNECTION_STRING_BEFORE_DATABASE_CREATION = "jdbc:mysql://localhost:3306";

    protected static Connection conn;

    public static void openConnection() {
        try {
            conn = DriverManager.getConnection(CONNECTION_STRING_BEFORE_DATABASE_CREATION, "root", "root");
            createDatabase();
            conn = DriverManager.getConnection(CONNECTION_STRING, "root", "root");
            createTables();
        } catch (SQLException e) {
            System.out.println("Couldn't connect to the database: " + e.getMessage());
            System.exit(0);
            e.printStackTrace();
        }
    }

    private static void createDatabase() throws SQLException {
        try (Statement statement = conn.createStatement()) {
            statement.execute(CREATE_DATABASE_STATEMENT);
        }
    }

    private static void createTables() throws SQLException {
        try (Statement statement = conn.createStatement()) {
            statement.execute(CREATE_POST_TABLE);
            statement.execute(CREATE_USER_TABLE);
            statement.execute(CREATE_COMMENTS_TABLE);
        }
    }

}
