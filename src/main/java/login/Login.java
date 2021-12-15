package login;

import datasource.LoginDatasource;
import menu.system.AdminMenu;
import menu.user.UserMenu;
import model.User;

import java.util.Scanner;

public class Login {

    private final Scanner scanner = new Scanner(System.in);
    private static LoginDatasource datasource;
    private static Thread applicationMainThread;
    private boolean notLoggedIn = true;
    private final String standard = "standard";
    private final String admin = "admin";

    public void initializeDatasource() {
        datasource = new LoginDatasource();
        datasource.initializePreparedStatement();
        logInProcess();
    }

    private void logInProcess() {
        while(notLoggedIn) {
            printLoginMenu();
            loginMenuAlternatives(scanner.nextLine());
        }
    }

    private void printLoginMenu() {
        System.out.println("\t1: Login");
        System.out.println("\t2: Create account");
        System.out.println("\t3: Exit");
    }

    private void loginMenuAlternatives(String input) {
        switch (input) {
            case "1" -> login();
            case "2" -> createAccount();
            case "3" -> System.exit(0);
        }
    }

    private void login() {
        User user = takeCredentials();
        if (user != null) {
            notLoggedIn = false;
            datasource.closePreparedStatement();
            startApplicationThread(user);
        } else {
            System.out.println("The username or password is incorrect.");
        }
    }

    private void startApplicationThread(User user) {
        if (user.getLevel().equals(admin)) {
            AdminMenu systemMenu = new AdminMenu(user);
            applicationMainThread = new Thread(systemMenu);
        } else if (user.getLevel().equals(standard)) {
            UserMenu userMenu = new UserMenu(user);
            applicationMainThread = new Thread(userMenu);
        }
        System.out.println("Welcome: " + user.getUserName() + "!");
        applicationMainThread.start();
    }

    private void createAccount(){
        System.out.println("Enter username:");
        String username = scanner.nextLine();
        while(datasource.isUserNameTaken(username)){
            System.out.println("Username is taken, try another username:");
            username= scanner.nextLine();
        }
        System.out.println("Enter email:");
        String email = scanner.nextLine();
        while(datasource.isEmailTaken(email)){
            System.out.println("There is already an account with this email");
            email = scanner.nextLine();
        }
        System.out.println("Enter password:");
        String password = scanner.nextLine();
        if(datasource.createAccount(username,email,password)){
            System.out.println("Account successfully created!");
        }
    }


    private User takeCredentials() {
        String username = enterUsername();
        String password = enterPassword();
        return datasource.queryLogin(username, password);
    }

    private String enterUsername() {
        System.out.println("Enter username:");
        return scanner.nextLine();
    }

    private String enterPassword() {
        System.out.println("Enter password:");
        return scanner.nextLine();
    }

    public void logout(User user) {
        System.out.println("Bye: " + user.getUserName() + "!");
        applicationMainThread.interrupt();
        initializeDatasource();
    }

}
