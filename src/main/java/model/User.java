package model;

public class User {

    private int userId;
    private String userName;
    private String email;
    private String password;

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "ID: " + userId + "\n" +
                "\tUsername: " + userName + "\n" +
                "\tEmail: " + email + "\n" +
                "\tPassword: " + password + "\n";
    }
}
