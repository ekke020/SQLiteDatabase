package app;

import menu.system.SystemMenu;
import menu.user.UserMenu;
import model.Datasource;

public class App {
    public static final Datasource DATASOURCE = new Datasource();

    public static void main(String[] args) {
        DATASOURCE.open();
        //SystemMenu systemMenu = new SystemMenu();
       //systemMenu.start();
        UserMenu userMenu = new UserMenu();
        userMenu.start();
    }

}
