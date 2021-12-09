package model;

import com.github.javafaker.Faker;
import loading.ProgressBar;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static menu.SqlConstants.*;


public class Datasource {

    private static final String CONNECTION_STRING = "jdbc:mysql://localhost:3306/forum_database";
    private static final String CONNECTION_STRING_BEFORE_DATABASE_CREATION = "jdbc:mysql://localhost:3306";

    private Connection conn;
    private PreparedStatement queryCommentsFromPost;
    private PreparedStatement queryPostsByCategory;
    private PreparedStatement queryLogin;
    private PreparedStatement createPost;

    public void createDatabase() throws SQLException {
        try (Statement statement = conn.createStatement()) {
            statement.execute(CREATE_DATABASE_STATEMENT);
        }
    }

    public boolean open() {
        try {
            conn = DriverManager.getConnection(CONNECTION_STRING_BEFORE_DATABASE_CREATION, "root", "root");
            createDatabase();
            conn = DriverManager.getConnection(CONNECTION_STRING, "root", "root");
            createTables();
            queryCommentsFromPost = conn.prepareStatement(QUERY_COMMENTS_FROM_POST);
            queryPostsByCategory = conn.prepareStatement(QUERY_POSTS_BY_CATEGORY);
            queryLogin = conn.prepareStatement(QUERY_LOGIN);
            createPost = conn.prepareStatement(CREATE_POST);
            return true;
        } catch (SQLException e) {
            System.out.println("Couldn't connect to the database: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    private void createTables() throws SQLException {
        try (Statement statement = conn.createStatement()) {
            statement.execute(CREATE_POST_TABLE);
            statement.execute(CREATE_USER_TABLE);
            statement.execute(CREATE_COMMENTS_TABLE);
        }
    }

    public User queryLogin(String userName, String password){
        try {
            queryLogin.setString(1, userName);
            queryLogin.setString(2, password);
            ResultSet results = queryLogin.executeQuery();
            if(!results.next()){
                return null;
            }
            User user = new User();
            user.setUserId(results.getInt(1));
            user.setUserName(results.getString(2));
            return user;
        } catch(SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            return null;
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
                        + COLUMN_USER_NAME + ", "
                        + COLUMN_USER_EMAIL + ", "
                        + COLUMN_USER_PASSWORD + ") "
                        + "VALUES ('"
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

    public void createPost(User user, String category, String title) {
        try {
            createPost.setInt(1, user.getUserId());
            createPost.setString(2, category);
            createPost.setString(3, title);
            createPost.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Insert failed: " + e.getMessage());
        }
    }

    public void queryPosts() {
        try (Statement statement = conn.createStatement();
             ResultSet results = statement.executeQuery("SELECT * FROM " + TABLE_POSTS)){
            while (results.next()) {
                System.out.println("Post id: " + results.getInt(1));
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

    public void searchPostByTitle(String value) {
        try (Statement statement = conn.createStatement();
             ResultSet results = statement.executeQuery("SELECT * FROM " + TABLE_POSTS +" WHERE " +
                     COLUMN_TITLE + " LIKE '" + value + "%'")){
            while(results.next()){
                System.out.println("Post id: " + results.getString(INDEX_POST_ID));
                System.out.println("\tTitle: " + results.getString(INDEX_TITLE));
                System.out.println("\tCategory: " + results.getString(INDEX_CATEGORY) + "\n");
            }
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
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
            post.setPostId(results.getString(INDEX_POST_ID));
            post.setUserId(results.getString(INDEX_POSTER_ID));
            post.setCategory(results.getString(INDEX_CATEGORY));
            post.setTitle(results.getString(INDEX_TITLE));
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
