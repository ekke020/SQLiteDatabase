package model;

import java.util.ArrayList;
import java.util.List;

public class Post {

    private int postId;
    private String userId;
    private String category;
    private String title;
    private final List<Comment> comments = new ArrayList<>();

    public void addComment(Comment comment) {
        comments.add(comment);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "ID: " + postId + "\n" +
                "\tUser ID: " + userId + "\n" +
                "\tCategory: " + category + "\n" +
                "\tTitle: " + title + "\n";
    }

}
