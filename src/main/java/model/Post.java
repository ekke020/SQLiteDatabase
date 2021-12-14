package model;

import java.util.*;

public class Post {

    private int postId;
    private int userId;
    private String category;
    private String title;
    private String userName;
    private String text;
    private final List<Comment> comments = new ArrayList<>();
    private final int borderSize = 60;
    private String postInfo;

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
        postInfo = "(posted by " + userName + " category: " + category + ")";
        buildBorder();
        String border = printBorder();
        System.out.println(Colors.BLUE + border);
        System.out.print(title.toUpperCase(Locale.ROOT));
        System.out.println(printPostInfo());
        System.out.println(border);
        System.out.print(text);
        System.out.println(border + Colors.RESET);
        comments.forEach(System.out::println);
    }

    private void buildBorder() {
        title = TextFormatter.formatText(title);
        title = addEmptySpace(title);
        postInfo = TextFormatter.formatText(postInfo);
        postInfo = addEmptySpace(postInfo);
        text = TextFormatter.formatText(text);
        text = addEmptySpace(text);
    }

    private String printBorder() {
        return "|" + "-".repeat(Math.max(0, borderSize)) + "--|";
    }

    private String addEmptySpace(String line) {
        String[] rows = line.split("\n");
        for (int i = 0; i < rows.length; i++) {
            int length = borderSize - rows[i].length();
            rows[i] = "|" + rows[i] + " ".repeat(length) + "|\n";
        }
        return String.join("", Arrays.asList(rows));
    }

    private String printPostInfo() {
        postInfo = postInfo.replace("\n", "");
        return "|" + Colors.CYAN_CURSIVE +
                postInfo.replace("|", "") +
                Colors.RESET + Colors.BLUE +  "|";
    }

    @Override
    public String toString() {
        return "ID: " + postId + "\n" +
                "\tUser ID: " + userId + "\n" +
                "\tCategory: " + category + "\n" +
                "\tTitle: " + title + "\n";
    }

}
