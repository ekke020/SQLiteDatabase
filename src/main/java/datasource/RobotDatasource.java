package datasource;

import lorem.LoremGenerator;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;

import static menu.SqlConstants.*;

public class RobotDatasource implements DatabaseConnection {

    private PreparedStatement queryRandomPost;
    private PreparedStatement createComment;
    private PreparedStatement createPost;

    private static final String CATEGORY_1 = "Love & Latin";
    private static final String CATEGORY_2 = "The classic popes";
    private static final String CATEGORY_3 = "Fun with bible";
    private static final String CATEGORY_4 = "Confessions";
    private final List<String> categories = List.of(CATEGORY_1, CATEGORY_2, CATEGORY_3, CATEGORY_4);
    private final Random rand = new Random();
    @Override
    public void initializePreparedStatement() {
        try {
            createPost = Connection.getInstance().conn.prepareStatement(CREATE_POST);
            queryRandomPost = Connection.getInstance().conn.prepareStatement(QUERY_RANDOM_POST);
            createComment = Connection.getInstance().conn.prepareStatement(CREATE_COMMENT);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void closePreparedStatement() {
        try {
            if (queryRandomPost != null) {
                queryRandomPost.close();
            }
            if (createComment != null) {
                createComment.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createPost(int id) {
        try {
            createPost.setInt(1, id);
            createPost.setString(2, getRandomCategory());
            createPost.setString(3, LoremGenerator.getLorem(5, 10));
            createPost.setString(4,LoremGenerator.getLorem(5, 45));
            createPost.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Insert failed: " + e.getMessage());
        }
    }

    private String getRandomCategory() {
        return categories.get(rand.nextInt(categories.size()));
    }

    public void postComment(int postId, int posterId){
        try {
            createComment.setInt(1, postId);
            createComment.setInt(2, posterId);
            createComment.setInt(3, 1);
            createComment.setString(4, LoremGenerator.getLorem(5, 45));
            createComment.setString(5, LocalDate.now().toString());
            createComment.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Insert failed: " + e.getMessage());
        }

    }

    public int getRandomPostId() {
        try {
           ResultSet result = queryRandomPost.executeQuery();
           result.next();
           return result.getInt(INDEX_POST_ID);
        } catch (SQLException e) {
            System.out.println("No posts available ... ");
            return -1;
        }
    }
}
