package login;

import app.App;
import model.User;

import java.util.Scanner;

public class Login implements Runnable {

    private boolean running = true;
    private Scanner scanner = new Scanner(System.in);
    private String username;
    private String password;

    @Override
    public void run() {
        while (running) {
            enterUsername();
            enterPassword();
            if(requestLogin()) {
                running = false;
            }
         }
    }

    private void enterUsername() {
        System.out.println("Enter username:");
        username = scanner.nextLine();
    }

    private void enterPassword() {
        System.out.println("Enter password:");
        password = scanner.nextLine();
    }

    private boolean requestLogin() {
        User user = App.DATASOURCE.queryLogin(username, password);
        if (user == null) {
            System.out.println("The username or password is incorrect.");
            return false;
        }
        return true;
    }
}
