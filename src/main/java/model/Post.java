package model;

public class Post {

    private String postId;
    private String userId;
    private String category;

    public void setPostId(String postId) {
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
                "\tCategory: " + category + "\n";
    }

}
