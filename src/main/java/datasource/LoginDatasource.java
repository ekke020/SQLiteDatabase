package datasource;

import model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static menu.SqlConstants.*;

public class LoginDatasource implements DatabaseConnection {

    private PreparedStatement queryLogin;
    private PreparedStatement queryUserName;
    private PreparedStatement queryEmail;
    private PreparedStatement createUserAccount;

    @Override
    public void initializePreparedStatement() {
        try {
            queryLogin = Connection.getInstance().conn.prepareStatement(QUERY_LOGIN);
            queryUserName = Connection.getInstance().conn.prepareStatement(QUERY_USERNAME);
            queryEmail = Connection.getInstance().conn.prepareStatement(QUERY_EMAIL);
            createUserAccount = Connection.getInstance().conn.prepareStatement(CREATE_USER_ACCOUNT);
        } catch (SQLException e) {
            System.out.println("Couldn't connect to the database: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void closePreparedStatement() {
        try {
            if (queryLogin != null) {
                queryLogin.close();
            }
            if (queryUserName != null) {
                queryUserName.close();
            }
            if (queryEmail != null) {
                queryEmail.close();
            }
            if (createUserAccount != null) {
                createUserAccount.close();
            }
        } catch (SQLException e) {
            System.out.println("Couldn't close connection: " + e.getMessage());
        }
    }

    public boolean isUserNameTaken(String userName) {
        try {
            queryUserName.setString(1, userName);
            ResultSet results = queryUserName.executeQuery();
            return results.next();
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            return true;
        }
    }

    public boolean isEmailTaken(String email) {
        try {
            queryEmail.setString(1, email);
            ResultSet results = queryEmail.executeQuery();
            return results.next();
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            return true;
        }
    }

    public boolean createAccount(String userName, String email, String password) {
        try {
            createUserAccount.setString(1, userName);
            createUserAccount.setString(2,email);
            createUserAccount.setString(3,password);
            createUserAccount.execute();
        } catch (SQLException e) {
            System.out.println("Insertion failed " + e.getMessage());
            return false;
        }
        return true;
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
            user.setLevel(results.getString(3));
            return user;
        } catch(SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            return null;
        }
    }

}
