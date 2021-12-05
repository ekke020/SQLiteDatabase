package model;

public class User {

    private String userId;
    private String userName;
    private String email;
    private String password;

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
