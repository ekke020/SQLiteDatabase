package app;

import login.Login;
import menu.user.UserMenu;
import model.Datasource;
import model.User;


public class App {

    public static final Datasource DATASOURCE = new Datasource();
    private static Thread applicationMainThread;

    public static void main(String[] args) {
        DATASOURCE.open();
        login();
    }

    private static void login() {
        while (true) {
            User user = Login.Login();
            if (user != null) {
                UserMenu userMenu = new UserMenu(user);
                System.out.println("Welcome: " + user.getUserName() + "!");
                applicationMainThread = new Thread(userMenu);
                applicationMainThread.start();
                break;
            } else {
                System.out.println("The username or password is incorrect.");
            }
        }
    }

    public static void logout(User user) {
        System.out.println("Bye: " + user.getUserName() + "!");
        applicationMainThread.interrupt();
        login();
    }

}
