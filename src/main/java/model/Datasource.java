package model;

import com.github.javafaker.Faker;
import loading.ProgressBar;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Datasource {

    private static final String DB_NAME = "testjava.db";
    private static final String CONNECTION_STRING = "jdbc:sqlite:" + DB_NAME;

    public static final String TABLE_POSTS = "posts";
    public static final String COLUMN_POST_ID = "post_id";
    public static final String COLUMN_POSTER_ID = "poster_id";
    public static final String COLUMN_CATEGORY = "category";
    public static final String COLUMN_TITLE = "title";
    public static final int INDEX_POST_ID = 1;
    public static final int INDEX_POSTER_ID = 2;
    public static final int INDEX_CATEGORY = 3;
    public static final int INDEX_TITLE = 4;

    public static final String TABLE_USERS = "users";
    public static final String COLUMN_USER_ID = "_id";
    public static final String COLUMN_USER_NAME = "user_name";
    public static final String COLUMN_USER_EMAIL = "email";
    public static final String COLUMN_USER_PASSWORD = "password";
    public static final int INDEX_USER_ID = 1;
    public static final int INDEX_USER_NAME = 2;
    public static final int INDEX_USER_EMAIL = 3;
    public static final int INDEX_USER_PASSWORD = 4;

    public static final String TABLE_COMMENTS = "comments";
    public static final String COLUMN_INDEX = "_index";
    public static final String COLUMN_TEXT = "text";
    public static final String COLUMN_TIME_STAMP = "time_stamp";

    public static final String QUERY_COMMENTS_FROM_POST = " SELECT " + TABLE_COMMENTS + "." + COLUMN_TEXT + ", " +
            TABLE_COMMENTS + "." + COLUMN_TIME_STAMP + ", " + TABLE_USERS + "."
            + COLUMN_USER_NAME + " FROM " + TABLE_COMMENTS + " INNER JOIN " +
            TABLE_POSTS + " ON " + TABLE_POSTS + "." + COLUMN_POST_ID + "=" + TABLE_COMMENTS + "." +
            COLUMN_POST_ID + " AND " + TABLE_COMMENTS + "." + COLUMN_POST_ID + "=?" +
            " INNER JOIN " + TABLE_USERS + " ON " + TABLE_COMMENTS + "." +
            COLUMN_POSTER_ID + "=" + TABLE_USERS + "." + COLUMN_USER_ID;

    public static final String QUERY_POSTS_BY_CATEGORY = "SELECT * FROM " + TABLE_POSTS + " WHERE " + COLUMN_CATEGORY + "=?";

    private Connection conn;
    private PreparedStatement queryCommentsFromPost;
    private PreparedStatement queryPostsByCategory;

    public boolean open() {
        try {
            conn = DriverManager.getConnection(CONNECTION_STRING);
            createTables();
            queryCommentsFromPost = conn.prepareStatement(QUERY_COMMENTS_FROM_POST);
            queryPostsByCategory = conn.prepareStatement(QUERY_POSTS_BY_CATEGORY);

            return true;
        } catch (SQLException e) {
            System.out.println("Couldn't connect to the database: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    private void createTables() throws SQLException {
        try (Statement statement = conn.createStatement()) {
            statement.execute("CREATE TABLE IF NOT EXISTS " + TABLE_POSTS +
                    "(" + COLUMN_POST_ID + " integer NOT NULL, " +
                    COLUMN_POSTER_ID + " text, " +
                    COLUMN_CATEGORY + " text, " + COLUMN_TITLE + " text," + " PRIMARY KEY (" + COLUMN_POST_ID +"))");
            statement.execute("CREATE TABLE IF NOT EXISTS " + TABLE_USERS +
                    " (" + COLUMN_USER_ID + " text, " +
                    COLUMN_USER_NAME + " text, " +
                    COLUMN_USER_EMAIL + " text, " +
                    COLUMN_USER_PASSWORD + " text" +
                    ") ");
            statement.execute("CREATE TABLE IF NOT EXISTS " + TABLE_COMMENTS +
                    " (" + COLUMN_POST_ID + " text, " +
                    COLUMN_POSTER_ID + " text, " +
                    COLUMN_INDEX + " integer, " +
                    COLUMN_TEXT + " text, " +
                    COLUMN_TIME_STAMP + " text" +
                    ") ");
        }
    }

    public void close() {
        try {
            if (conn != null) {
                conn.close();
            }
            if (queryCommentsFromPost != null) {
                queryCommentsFromPost.close();
            }
        } catch (SQLException e) {
            System.out.println("Couldn't close connection: " + e.getMessage());
        }
    }

    public void createUsers(int amount) {
        Faker faker;
        try (Statement statement = conn.createStatement()) {
            ProgressBar progressBar = new  ProgressBar(amount);
            new Thread(progressBar).start();
            for (int i = 0; i < amount; i++) {
                faker = new Faker();
                statement.execute("INSERT INTO " + TABLE_USERS + "("
                        + COLUMN_USER_ID + ", "
                        + COLUMN_USER_NAME + ", "
                        + COLUMN_USER_EMAIL + ", "
                        + COLUMN_USER_PASSWORD + ") "
                        + "VALUES ('"
                        + UUID.randomUUID() + "', '"
                        + faker.name().username() + "', '"
                        + faker.internet().emailAddress() + "', '"
                        + faker.internet().password(true)
                        + "')");
                progressBar.incrementProgress();
            }
            System.out.println("Generated: " + amount + " new users.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateUserColumn(String column, String value, String id) {
        try (Statement statement = conn.createStatement()){
            int success = statement.executeUpdate("UPDATE " +
                    TABLE_USERS + " SET " +
                    column + " = '" +
                    value + "' WHERE " +
                    COLUMN_USER_ID + " = '" + id + "'");
            if (success == 1) {
                System.out.format("Row with id: %s successfully updated.\n", id);
            } else {
                System.out.format("Failed to update row, no match with %s\n", id);
            }
        } catch (SQLException e) {
            System.out.println("Failed to update column: " + e.getMessage());
        }
    }

    public void queryTable(String table) {
        try (Statement statement = conn.createStatement();
             ResultSet results = statement.executeQuery("SELECT * FROM " + table)){
            switch (table) {
                case TABLE_USERS -> generateUserList(results).forEach(System.out::println);
                case TABLE_POSTS -> generatePostList(results).forEach(System.out::println);
            }
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
        }
    }

    public void queryPost(String postId) {
        try {
            queryCommentsFromPost.setString(1, postId);
            ResultSet results = queryCommentsFromPost.executeQuery();
            while (results.next()) {
                System.out.println(results.getString(1));
                System.out.println(results.getString(2));
                System.out.println(results.getString(3));
            }
        } catch(SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
        }
    }

    public void queryPosts() {
        try (Statement statement = conn.createStatement();
             ResultSet results = statement.executeQuery("SELECT * FROM " + TABLE_POSTS)){
            while (results.next()) {
                System.out.println("Post id: " + results.getString(1));
                System.out.println("Category: " + results.getString(3));
                System.out.println("Title: " + results.getString(4) + "\n");
            }
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
        }
    }

    public void queryPostsByCategory(String input) {
        try {
            queryPostsByCategory.setString(1, input);
            ResultSet results = queryPostsByCategory.executeQuery();
            while (results.next()) {
                System.out.println("Post id: " + results.getString(1));
                System.out.println("Category: " + results.getString(3));
                System.out.println("Title: " + results.getString(4) + "\n");
            }


        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
        }
    }

    public boolean searchTable(String table, String column, String value) {
        try (Statement statement = conn.createStatement();
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
            user.setUserId(results.getString(INDEX_USER_ID));
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
            post.setPostId(results.getString(INDEX_POST_ID));
            post.setUserId(results.getString(INDEX_POSTER_ID));
            post.setCategory(results.getString(INDEX_CATEGORY));
            posts.add(post);
        }
        return posts;
    }

    public static String getUserColumn(int num) {
        String column= "";
        switch (num) {
            case 2 -> column = COLUMN_USER_NAME;
            case 3 -> column = COLUMN_USER_EMAIL;
            case 4 -> column = COLUMN_USER_PASSWORD;
            default -> column = COLUMN_USER_ID;
        }
        return column;
    }

    public static String getPostColumn(int num) {
        String column= "";
        switch (num) {
            case 2 -> column = COLUMN_POSTER_ID;
            case 3 -> column = COLUMN_CATEGORY;
            default -> column = COLUMN_POST_ID;
        }
        return column;
    }
}
