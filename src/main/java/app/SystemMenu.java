package app;

import model.Datasource;

import java.util.Scanner;

import static app.MenuConstants.*;

public class SystemMenu implements Runnable {

    private final Scanner scanner = new Scanner(System.in);
    private boolean running = true;

    public void start() {
        new Thread(this).start();
    }

    @Override
    public void run() {
        App.DATASOURCE.open();
        printMainMenu();
        mainMenuAlternatives(scanner.nextLine());
        while (running) {
            switch (getMenuConstants()) {
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
            case "1" -> setMenuConstants(POST_TABLE_MENU);
            case "2" -> setMenuConstants(USER_TABLE_MENU);
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
                App.DATASOURCE.queryTable(Datasource.TABLE_USERS);
                promptEnterKey();
            }
            case "2" -> chooseFieldToEdit();
            case "3" -> chooseFieldToSearchBy(Datasource.TABLE_USERS);
            case "4" -> generateNewUsers();
            case "5" -> setMenuConstants(MAIN_MENU);
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
                App.DATASOURCE.queryTable(Datasource.TABLE_POSTS);
                promptEnterKey();
            }
            case "2" -> chooseFieldToSearchBy(Datasource.TABLE_POSTS);
            case "3" -> setMenuConstants(MAIN_MENU);
        }
    }

    private void chooseFieldToEdit() {
        System.out.println("Enter field to edit:");
        printUserFields();
        while (!scanner.hasNextInt()) {
            System.out.println("Enter a number ...");
            scanner.nextLine();
        }
        int number = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter id: ");
        String id = scanner.nextLine();
        updateColumn(Datasource.getUserColumn(number), id);
        promptEnterKey();
    }

    private void updateColumn(String column, String id) {
        System.out.println("Enter new value: ");
        String value = scanner.nextLine();
        App.DATASOURCE.updateUserColumn(column, value, id);
    }

    private void chooseFieldToSearchBy(String table) {
        System.out.println("Enter field to search by:");
        switch (table) {
            case Datasource.TABLE_USERS -> printUserFields();
            case Datasource.TABLE_POSTS -> printPostFields();
        }
        while (!scanner.hasNextInt()) {
            System.out.println("Enter a number ...");
            scanner.nextLine();
        }
        int number = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter search value");
        switch (table) {
            case Datasource.TABLE_USERS ->
                    App.DATASOURCE.searchTable(
                        Datasource.TABLE_USERS, Datasource.getUserColumn(number), scanner.nextLine());
            case Datasource.TABLE_POSTS ->
                    App.DATASOURCE.searchTable(
                            Datasource.TABLE_POSTS, Datasource.getPostColumn(number), scanner.nextLine());
        }
        promptEnterKey();
    }

    private void printUserFields() {
        System.out.println("\t" + Datasource.INDEX_USER_ID + " : " + Datasource.COLUMN_USER_ID);
        System.out.println("\t" + Datasource.INDEX_USER_NAME + " : " + Datasource.COLUMN_USER_NAME);
        System.out.println("\t" + Datasource.INDEX_USER_EMAIL + " : " + Datasource.COLUMN_USER_EMAIL);
        System.out.println("\t" + Datasource.INDEX_USER_PASSWORD + " : " + Datasource.COLUMN_USER_PASSWORD);
    }

    private void printPostFields() {
        System.out.println("\t" + Datasource.INDEX_POST_ID + " : " + Datasource.COLUMN_POST_ID);
        System.out.println("\t" + Datasource.INDEX_POSTER_ID + " : " + Datasource.COLUMN_POSTER_ID);
        System.out.println("\t" + Datasource.INDEX_CATEGORY + " : " + Datasource.COLUMN_CATEGORY);
    }

    private void generateNewUsers() {
        System.out.println("Enter amount of users to be generated");
        while (!scanner.hasNextInt()) {
            System.out.println("Enter a number ...");
            scanner.nextLine();
        }
        int amount = Integer.parseInt(scanner.nextLine());
        System.out.println("STARTING CREATE USERS");
        App.DATASOURCE.createUsers(amount);
        System.out.println("FINISHED CREATE USERS");

    }

    private void promptEnterKey() {
        System.out.println("Press \"ENTER\" to continue...");
        scanner.nextLine();
    }

    private void closeDatabase() {
        App.DATASOURCE.close();
        running = false;
    }

}
