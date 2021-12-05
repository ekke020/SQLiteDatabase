package app;

import model.Datasource;

public class App {
    public static final Datasource DATASOURCE = new Datasource();

    public static void main(String[] args) {
        SystemMenu systemMenu = new SystemMenu();
        systemMenu.start();
    }

}
