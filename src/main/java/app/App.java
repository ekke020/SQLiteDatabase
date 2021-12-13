package app;

import datasource.Datasource;
import login.Login;



public class App {

    public static void main(String[] args) {
        Datasource.openConnection();
        Login.login();
    }

}
