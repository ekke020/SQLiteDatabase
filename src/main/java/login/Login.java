package login;

import app.App;
import model.User;

import java.util.Scanner;

public class Login {

    private static final Scanner SCANNER = new Scanner(System.in);

    public static User Login() {
        String username = enterUsername();
        String password = enterPassword();
        return App.DATASOURCE.queryLogin(username, password);
    }

    private static String enterUsername() {
        System.out.println("Enter username:");
        return SCANNER.nextLine();
    }

    private static String enterPassword() {
        System.out.println("Enter password:");
        return SCANNER.nextLine();
    }

}
