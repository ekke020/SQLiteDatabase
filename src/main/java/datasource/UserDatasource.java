package datasource;

import model.Comment;
import model.Post;
import model.TextFormatter;
import model.User;

import java.sql.*;
import java.time.LocalDate;

import static menu.SqlConstants.*;
import static menu.SqlConstants.CREATE_COMMENT;

public class UserDatasource implements DatabaseConnection{

    private PreparedStatement queryPostsByCategory;
    private PreparedStatement createPost;
    private PreparedStatement queryPost;
    private PreparedStatement createComment;
    private PreparedStatement queryCommentsFromPost;

    @Override
    public void initializePreparedStatement() {
        try {
            queryCommentsFromPost = Connection.getInstance().conn.prepareStatement(QUERY_COMMENTS_FROM_POST);
            queryPostsByCategory = Connection.getInstance().conn.prepareStatement(QUERY_POSTS_BY_CATEGORY);
            createPost = Connection.getInstance().conn.prepareStatement(CREATE_POST);
            queryPost = Connection.getInstance().conn.prepareStatement(QUERY_POST);
            createComment = Connection.getInstance().conn.prepareStatement(CREATE_COMMENT);
        } catch (SQLException e) {
            System.out.println("Couldn't connect to the database: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void closePreparedStatement() {
        try {
            if (queryCommentsFromPost != null) {
                queryCommentsFromPost.close();
            }
            if (queryPostsByCategory != null) {
                queryPostsByCategory.close();
            }
            if (createPost != null) {
                createPost.close();
            }
            if (queryPost != null) {
                queryPost.close();
            }
            if (createComment != null) {
                createComment.close();
            }
        } catch (SQLException e) {
            System.out.println("Couldn't close connection: " + e.getMessage());
        }
    }

    public boolean searchPostByTitle(String value) {
        try (Statement statement = Connection.getInstance().conn.createStatement();
             ResultSet results = statement.executeQuery("SELECT * FROM " + TABLE_POSTS +" WHERE " +
                     COLUMN_TITLE + " LIKE '" + value + "%'")){
            if (!results.next())
                return false;
            do {
                System.out.println("Post id: " + results.getString(INDEX_POST_ID));
                System.out.println("\tTitle: " + results.getString(INDEX_TITLE));
                System.out.println("\tCategory: " + results.getString(INDEX_CATEGORY) + "\n");
            } while(results.next());
            return true;
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            return false;
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

    public void queryPosts() {
        try (Statement statement = Connection.getInstance().conn.createStatement();
             ResultSet results = statement.executeQuery("SELECT * FROM " + TABLE_POSTS)){
            while (results.next()) {
                System.out.println("Post id: " + results.getInt(1));
                System.out.println("\tCategory: " + results.getString(3));
                System.out.println(TextFormatter.formatText("Title: " + results.getString(4)));
                System.out.println();
            }
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
        }
    }

    public void createPost(User user, String category, String title, String text) {
        try {
            createPost.setInt(1, user.getUserId());
            createPost.setString(2, category);
            createPost.setString(3, title);
            createPost.setString(4,text);
            createPost.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Insert failed: " + e.getMessage());
        }
    }

    public Post queryPost(int postId) {
        Post post = new Post();
        try {
            queryPost.setInt(1, postId);
            ResultSet result = queryPost.executeQuery();
            result.next();
            post.setPostId(result.getInt(INDEX_POST_ID));
            post.setUserId(result.getInt(INDEX_POSTER_ID));
            post.setUserName(result.getString(INDEX_POSTER_NAME));
            post.setCategory(result.getString(INDEX_CATEGORY));
            post.setTitle(result.getString(INDEX_TITLE));
            post.setText(result.getString(INDEX_POST_TEXT));
            queryCommentsFromPost(post);
            return post;
        } catch(SQLException e) {
            System.out.println("Input does not match any post ...");
            return null;
        }
    }

    private void queryCommentsFromPost(Post post) {
        try {
            queryCommentsFromPost.setInt(1, post.getPostId());
            ResultSet results = queryCommentsFromPost.executeQuery();
            while (results.next()) {
                String text = results.getString(1); // text
                int index = results.getInt(2); // index
                String date = results.getString(3); // datum
                String posterName = results.getString(4); // poster name
                post.addComment(new Comment(text, posterName, date, index));
            }
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
        }
    }

    public void postComment(String text, int postId, int posterId){
        try {
            createComment.setInt(1, postId);
            createComment.setInt(2, posterId);
            createComment.setInt(3, 1);
            createComment.setString(4,text);
            createComment.setString(5, LocalDate.now().toString());
            createComment.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Insert failed: " + e.getMessage());
        }

    }

}
