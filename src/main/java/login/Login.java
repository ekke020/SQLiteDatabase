package login;

import datasource.LoginDatasource;
import menu.system.SystemMenu;
import menu.user.UserMenu;
import model.User;

import java.util.Scanner;

public class Login {

    private static final Scanner SCANNER = new Scanner(System.in);
    private static LoginDatasource datasource;
    private static Thread applicationMainThread;

    public static void login() {
        datasource = new LoginDatasource();
        datasource.initializePreparedStatement();
        while (true) {
            User user = takeCredentials();
            if (user != null) {
                datasource.closePreparedStatement();
//                UserMenu userMenu = new UserMenu(user);
                SystemMenu systemMenu = new SystemMenu(user);
                System.out.println("Welcome: " + user.getUserName() + "!");
                applicationMainThread = new Thread(systemMenu);
                applicationMainThread.start();
                break;
            } else {
                System.out.println("The username or password is incorrect.");
            }
        }
    }


    private static User takeCredentials() {
        String username = enterUsername();
        String password = enterPassword();
        return datasource.queryLogin(username, password);
    }

    private static String enterUsername() {
        System.out.println("Enter username:");
        return SCANNER.nextLine();
    }

    private static String enterPassword() {
        System.out.println("Enter password:");
        return SCANNER.nextLine();
    }

    public static void logout(User user) {
        System.out.println("Bye: " + user.getUserName() + "!");
        applicationMainThread.interrupt();
        login();
    }

}
