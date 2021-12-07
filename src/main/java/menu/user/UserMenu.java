package menu.user;

import app.App;

import java.util.Scanner;

import static menu.user.UserConstants.*;


public class UserMenu implements Runnable {

    private final Scanner scanner = new Scanner(System.in);
    private boolean running = true;


    private static final String CATEGORY_1 = "Abortions & Sins";
    private static final String CATEGORY_2 = "The classic popes";
    private static final String CATEGORY_3 = "Fun with bible";
    private static final String CATEGORY_4 = "Confessions";

    public void start() {
        new Thread(this).start();
    }

    @Override
    public void run() {
        while (running) {
            switch (getUserConstants()) {
                case MAIN_MENU -> {
                    printMainMenu();
                    mainMenuAlternatives(scanner.nextLine());
                }
                case CATEGORY_MENU -> {
                    printCategoryChoices();
                    categoryAlternatives(scanner.nextLine());
                }
                case POST_MENU -> {

                }
                case SEARCH_POST -> {
                   searchPostByTitle();
                }
                case ENTER_POST -> {

                }
                case LOGOUT -> System.out.println();
            }
        }
    }

    private void printMainMenu() {
        System.out.println("MAIN MENU");
        System.out.println("\t1: View all posts");
        System.out.println("\t2: View posts by category");
        System.out.println("\t3: Search post");
        System.out.println("\t4: Create post");
        System.out.println("\t5: View profile");
        System.out.println("\t6: Logout");
    }

    private void searchPostByTitle(){
        System.out.println("Enter word to search by:");
        App.DATASOURCE.searchPostByTitle(scanner.nextLine());
    }

    private void mainMenuAlternatives(String input) {
        switch (input) {
            case "1" -> App.DATASOURCE.queryPosts();
            case "2" -> UserConstants.setUserConstants(UserConstants.CATEGORY_MENU);
            case "3" -> UserConstants.setUserConstants(UserConstants.SEARCH_POST);
            case "4" -> UserConstants.setUserConstants(UserConstants.ENTER_POST);
            case "5" -> UserConstants.setUserConstants(UserConstants.LOGOUT);
        }
    }

    private void printCategoryChoices() {
        System.out.println("CATEGORY MENU");
        System.out.println("\t1: Abortions & Sins");
        System.out.println("\t2: The classic popes");
        System.out.println("\t3: Fun with bible");
        System.out.println("\t4: Confessions");
        System.out.println("\t5: Back");
    }

    private void categoryAlternatives(String input) {
        switch (input) {
            case "1" -> {App.DATASOURCE.queryPostsByCategory(CATEGORY_1);}
            case "2" -> {App.DATASOURCE.queryPostsByCategory(CATEGORY_2);}
            case "3" -> {App.DATASOURCE.queryPostsByCategory(CATEGORY_3);}
            case "4" -> {App.DATASOURCE.queryPostsByCategory(CATEGORY_4);}
            case "5" -> {setUserConstants(MAIN_MENU);}
        }
    }
}
