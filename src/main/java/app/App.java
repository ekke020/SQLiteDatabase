package app;

import login.Login;
import model.Datasource;


public class App {

    public static final Datasource DATASOURCE = new Datasource();

    public static void main(String[] args) {
        DATASOURCE.open();
        Login.login();
    }

}
