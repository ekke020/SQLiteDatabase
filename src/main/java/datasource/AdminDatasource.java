package datasource;

import com.github.javafaker.Faker;
import loading.ProgressBar;
import model.Post;
import model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static menu.SqlConstants.*;

public class AdminDatasource implements DatabaseConnection {

    private PreparedStatement queryUserTable;
    private PreparedStatement queryPostTable;
    private PreparedStatement createUsers;
    private PreparedStatement updateUserColumnName;
    private PreparedStatement updateUserColumnEmail;
    private PreparedStatement updateUserColumnPassword;
    private PreparedStatement searchTable;

    @Override
    public void initializePreparedStatement() {
        try {
            queryUserTable = Connection.getInstance().conn.prepareStatement(QUERY_USER_TABLE);
            queryPostTable = Connection.getInstance().conn.prepareStatement(QUERY_POST_TABLE);
            createUsers = Connection.getInstance().conn.prepareStatement(CREATE_USER_ACCOUNT);
            updateUserColumnName = Connection.getInstance().conn.prepareStatement(UPDATE_USER_COLUMN_NAME);
            updateUserColumnEmail = Connection.getInstance().conn.prepareStatement(UPDATE_USER_COLUMN_EMAIL);
            updateUserColumnPassword = Connection.getInstance().conn.prepareStatement(UPDATE_USER_COLUMN_PASSWORD);
            searchTable = Connection.getInstance().conn.prepareStatement(SEARCH_TABLE);
        } catch (SQLException e) {
            System.out.println("Couldn't connect to the database: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void closePreparedStatement() {
        try {
            if (Connection.getInstance().conn != null) {
                Connection.getInstance().conn.close();
            }
        } catch (SQLException e) {
            System.out.println("Couldn't close connection: " + e.getMessage());
        }
    }


    public void queryTable(String table) {
        try {
            ResultSet results = null;
            if (table.equals(TABLE_USERS))
                results = queryUserTable.executeQuery();
            else if (table.equals(TABLE_POSTS))
                results = queryPostTable.executeQuery();
            switch (table) {
                case TABLE_USERS -> generateUserList(results).forEach(System.out::println);
                case TABLE_POSTS -> generatePostList(results).forEach(System.out::println);
            }
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
        }
    }

    public void createUsers(int amount) {
        Faker faker;
        try {
            ProgressBar progressBar = new  ProgressBar(amount);
            new Thread(progressBar).start();
            for (int i = 0; i < amount; i++) {
                faker = new Faker();
                createUsers.setString(1, faker.name().username());
                createUsers.setString(2, faker.internet().emailAddress());
                createUsers.setString(3, faker.internet().password(true));
                createUsers.addBatch();
                progressBar.incrementProgress();
            }
            createUsers.executeBatch();
            System.out.println("Generated: " + amount + " new users.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateUserColumn(int column, String value, String id) {
        int success = 0;
        try {
            switch (column) {
                case 1 -> {
                    updateUserColumnName.setString(1, value);
                    updateUserColumnName.setString(2, id);
                    success = updateUserColumnName.executeUpdate();
                }
                case 2 -> {
                    updateUserColumnEmail.setString(1, value);
                    updateUserColumnEmail.setString(2, id);
                    success = updateUserColumnEmail.executeUpdate();
                }
                case 3 -> {
                    updateUserColumnPassword.setString(1, value);
                    updateUserColumnPassword.setString(2, id);
                    success = updateUserColumnPassword.executeUpdate();
                }
            }
            if (success == 1) {
                System.out.format("Row with id: %s successfully updated.\n", id);
            } else {
                System.out.format("Failed to update row, no match with %s\n", id);
            }
        } catch (SQLException e) {
            System.out.println("Failed to update column: " + e.getMessage());
        }
    }

    public List<User> queryUsers() {
        List<User> users = new ArrayList<>();
        try (Statement statement = Connection.getInstance().conn.createStatement();
             ResultSet results = statement.executeQuery(QUERY_USER_TABLE))   {
            while (results.next()) {
                User user = new User();
                user.setUserId(results.getInt(INDEX_USER_ID));
                user.setUserName(results.getString(INDEX_USER_NAME));
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean searchTable(String table, String column, String value) {
        try (Statement statement = Connection.getInstance().conn.createStatement();
             ResultSet results = statement.executeQuery("SELECT * FROM " + table +" WHERE " +
                     column + " LIKE '" + value + "%'")){
            switch (table) {
                case TABLE_USERS -> {
                    generateUserList(results).forEach(System.out::println);
                    return true;
                }
                case TABLE_POSTS -> {
                    generatePostList(results).forEach(System.out::println);
                    return true;
                }
            }
            return false;
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            return false;
        }
    }

    private List<User> generateUserList(ResultSet results) throws SQLException {
        List<User> users = new ArrayList<>();
        while (results.next()) {
            User user = new User();
            user.setUserId(results.getInt(INDEX_USER_ID));
            user.setUserName(results.getString(INDEX_USER_NAME));
            user.setEmail(results.getString(INDEX_USER_EMAIL));
            user.setPassword(results.getString(INDEX_USER_PASSWORD));
            users.add(user);
        }
        return users;
    }

    private List<Post> generatePostList(ResultSet results) throws SQLException {
        List<Post> posts = new ArrayList<>();
        while (results.next()) {
            Post post = new Post();
            post.setPostId(results.getInt(INDEX_POST_ID));
            post.setUserId(results.getInt(INDEX_POSTER_ID));
            post.setCategory(results.getString(INDEX_CATEGORY));
            post.setTitle(results.getString(INDEX_TITLE));
            posts.add(post);
        }
        return posts;
    }
}
