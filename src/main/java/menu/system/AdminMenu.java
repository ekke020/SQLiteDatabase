package menu.system;

import datasource.AdminDatasource;
import model.User;

import java.util.Scanner;

import static menu.system.AdminConstants.getAdminConstants;

import static menu.SqlConstants.*;

public class AdminMenu implements Runnable {

    private final Scanner scanner = new Scanner(System.in);
    private boolean running = true;
    private final User user;
    private final AdminDatasource adminDatasource = new AdminDatasource();

    public AdminMenu(User user) {
        adminDatasource.initializePreparedStatement();
        this.user = user;
    }

    @Override
    public void run() {
        printMainMenu();
        mainMenuAlternatives(scanner.nextLine());
        while (running) {
            switch (getAdminConstants()) {
                case MAIN_MENU -> {
                    printMainMenu();
                    mainMenuAlternatives(scanner.nextLine());
                }
                case USER_TABLE_MENU -> {
                    printUserTableMenu();
                    userTableMenuAlternatives(scanner.nextLine());
                }
                case POST_TABLE_MENU -> {
                    printPostTableMenu();
                    postTableMenuAlternatives(scanner.nextLine());
                }
            }
        }
    }

    private void printMainMenu() {
        System.out.println("MAIN MENU");
        System.out.println("\t1: View post table");
        System.out.println("\t2: View user table");
        System.out.println("\t3: Exit database");
    }

    private void mainMenuAlternatives(String input) {
        switch (input) {
            case "1" -> AdminConstants.setMenuConstants(AdminConstants.POST_TABLE_MENU);
            case "2" -> AdminConstants.setMenuConstants(AdminConstants.USER_TABLE_MENU);
            case "3" -> closeDatabase();
        }
    }

    private void printUserTableMenu() {
        System.out.println("USER TABLE MENU");
        System.out.println("\t1: List table");
        System.out.println("\t2: Edit a record");
        System.out.println("\t3: Search by field");
        System.out.println("\t4: Add users");
        System.out.println("\t5: Back");
    }

    private void userTableMenuAlternatives(String input) {
        switch (input) {
            case "1" -> {
                adminDatasource.queryTable(TABLE_USERS);
                promptEnterKey();
            }
            case "2" -> chooseFieldToEdit();
            case "3" -> chooseFieldToSearchBy(TABLE_USERS);
            case "4" -> generateNewUsers();
            case "5" -> AdminConstants.setMenuConstants(AdminConstants.MAIN_MENU);
        }
    }

    private void printPostTableMenu() {
        System.out.println("POST TABLE MENU");
        System.out.println("\t1: List table");
        System.out.println("\t2: Search by field");
        System.out.println("\t3: Back");
    }

    private void postTableMenuAlternatives(String input) {
        switch (input) {
            case "1" -> {
                adminDatasource.queryTable(TABLE_POSTS);
                promptEnterKey();
            }
            case "2" -> chooseFieldToSearchBy(TABLE_POSTS);
            case "3" -> AdminConstants.setMenuConstants(AdminConstants.MAIN_MENU);
        }
    }

    private void chooseFieldToEdit() {
        System.out.println("Enter field to edit:");
        System.out.println("\t1 : " + COLUMN_USER_NAME);
        System.out.println("\t2 : " + COLUMN_USER_EMAIL);
        System.out.println("\t3 : " + COLUMN_USER_PASSWORD);
        while (!scanner.hasNextInt()) {
            System.out.println("Enter a number ...");
            scanner.nextLine();
        }
        int number = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter id: ");
        String id = scanner.nextLine();
        updateColumn(number, id);
        promptEnterKey();
    }

    private void updateColumn(int column, String id) {
        System.out.println("Enter new value: ");
        String value = scanner.nextLine();
        adminDatasource.updateUserColumn(column, value, id);
    }

    private void chooseFieldToSearchBy(String table) {
        System.out.println("Enter field to search by:");
        switch (table) {
            case TABLE_USERS -> printUserFields();
            case TABLE_POSTS -> printPostFields();
        }
        while (!scanner.hasNextInt()) {
            System.out.println("Enter a number ...");
            scanner.nextLine();
        }
        int number = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter search value");
        switch (table) {
            case TABLE_USERS ->
                    adminDatasource.searchTable(
                        TABLE_USERS, getUserColumn(number), scanner.nextLine());
            case TABLE_POSTS ->
                    adminDatasource.searchTable(
                            TABLE_POSTS, getPostColumn(number), scanner.nextLine());
        }
        promptEnterKey();
    }

    private void printUserFields() {
        System.out.println("\t" + INDEX_USER_ID + " : " + COLUMN_USER_ID);
        System.out.println("\t" + INDEX_USER_NAME + " : " + COLUMN_USER_NAME);
        System.out.println("\t" + INDEX_USER_EMAIL + " : " + COLUMN_USER_EMAIL);
        System.out.println("\t" + INDEX_USER_PASSWORD + " : " + COLUMN_USER_PASSWORD);
    }

    private void printPostFields() {
        System.out.println("\t" + INDEX_POST_ID + " : " + COLUMN_POST_ID);
        System.out.println("\t" + INDEX_POSTER_ID + " : " + COLUMN_POSTER_ID);
        System.out.println("\t" + INDEX_CATEGORY + " : " + COLUMN_CATEGORY);
    }

    private void generateNewUsers() {
        System.out.println("Enter amount of users to be generated");
        while (!scanner.hasNextInt()) {
            System.out.println("Enter a number ...");
            scanner.nextLine();
        }
        int amount = Integer.parseInt(scanner.nextLine());
        adminDatasource.createUsers(amount);
    }

    private void promptEnterKey() {
        System.out.println("Press \"ENTER\" to continue...");
        scanner.nextLine();
    }

    private void closeDatabase() {
        adminDatasource.closePreparedStatement();
        running = false;
    }

    private String getPostColumn(int num) {
        String column= "";
        switch (num) {
            case 2 -> column = COLUMN_POSTER_ID;
            case 3 -> column = COLUMN_CATEGORY;
            default -> column = COLUMN_POST_ID;
        }
        return column;
    }
    private String getUserColumn(int num) {
        String column= "";
        switch (num) {
            case 2 -> column = COLUMN_USER_NAME;
            case 3 -> column = COLUMN_USER_EMAIL;
            case 4 -> column = COLUMN_USER_PASSWORD;
            default -> column = COLUMN_USER_ID;
        }
        return column;
    }
}
