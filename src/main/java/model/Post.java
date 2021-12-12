package model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class Post {

    private int postId;
    private int userId;
    private String category;
    private String title;
    private String userName;
    private String text;
    private final List<Comment> comments = new ArrayList<>();
    private int borderSize;
    private String middlePostText;

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
        middlePostText = "    (posted by user " + userName + " in category " + category + ")";
        buildBorder();
        String border = calculateBorder(borderSize);
        System.out.println(Colors.BLUE + border);
        System.out.println("| " + title.toUpperCase(Locale.ROOT));
        System.out.println("| " + middlePostText);
        System.out.println("| " + text);
        System.out.println(border + Colors.RESET);
        comments.forEach(System.out::println);
    }

    private void buildBorder() {
        text = "    " + text;
        borderSize = calculateBorderLength(title, middlePostText, text);
        title += addEmptySpace(title, borderSize);
        middlePostText += addEmptySpace(middlePostText, borderSize);
        text += addEmptySpace(text, borderSize);
    }

    private int calculateBorderLength(String top, String middle, String bottom) {
        int size = Math.max(top.length(), middle.length());
        size = Math.max(bottom.length(), size);
        return size;
    }

    private String calculateBorder(int size) {
        return "|" + "-".repeat(Math.max(0, size)) + "--|";
    }

    private String addEmptySpace(String line, int size) {
        int length = size - line.length();
        return " ".repeat(length) + " |";
    }

    @Override
    public String toString() {
        return "ID: " + postId + "\n" +
                "\tUser ID: " + userId + "\n" +
                "\tCategory: " + category + "\n" +
                "\tTitle: " + title + "\n";
    }

}
