package datasource;

import model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static menu.SqlConstants.*;

public class LoginDatasource extends Datasource implements DatabaseConnection {

    private PreparedStatement queryLogin;

    @Override
    public void initializePreparedStatement() {
        try {
            queryLogin = conn.prepareStatement(QUERY_LOGIN);
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
        } catch (SQLException e) {
            System.out.println("Couldn't close connection: " + e.getMessage());
        }
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
            return user;
        } catch(SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            return null;
        }
    }

}
