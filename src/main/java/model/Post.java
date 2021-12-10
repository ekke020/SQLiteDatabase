package model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Post {

    private int postId;
    private int userId;
    private String category;
    private String title;
    private String userName;
    private String text;
    private final List<Comment> comments = new ArrayList<>();

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setText(String text) {
        this.text = text;
    }

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

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void printEntirePost(){
        comments.sort(Comparator.comparingInt(Comment::getIndex));

        System.out.println(title + "\n\t( posted by user " + userName + " in category " + category + ")\n\t" + text + "\n");
        comments.forEach(System.out::println);
    }

    @Override
    public String toString() {
        return "ID: " + postId + "\n" +
                "\tUser ID: " + userId + "\n" +
                "\tCategory: " + category + "\n" +
                "\tTitle: " + title + "\n";
    }

}
