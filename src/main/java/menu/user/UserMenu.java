package menu.user;

import app.App;
import model.User;

import java.util.Scanner;

import static menu.user.UserConstants.*;


public class UserMenu implements Runnable {

    private final Scanner scanner = new Scanner(System.in);
    private final User user;
    private boolean running = true;
    private UserConstants userConstants = MAIN_MENU;

    private static final String CATEGORY_1 = "Abortions & Sins";
    private static final String CATEGORY_2 = "The classic popes";
    private static final String CATEGORY_3 = "Fun with bible";
    private static final String CATEGORY_4 = "Confessions";


    public UserMenu(User user) {
        this.user = user;
    }

    @Override
    public void run() {
        while (running) {
            switch (userConstants) {
                case MAIN_MENU -> {
                    printMainMenu();
                    mainMenuAlternatives(scanner.nextLine());
                }
                case CATEGORY_MENU -> {
                    System.out.println("CATEGORY MENU");
                    printCategoryChoices();
                    categoryAlternatives(scanner.nextLine());
                }
                case POST_MENU -> {

                }
                case SEARCH_POST -> {
                   searchPostByTitle();
                }
                case CREATE_POST -> {
                    createNewPost();
                }
                case LOGOUT -> {
                    App.logout(user);
                    running = false;
                }
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
            case "2" -> userConstants = CATEGORY_MENU;
            case "3" -> userConstants = SEARCH_POST;
            case "4" -> userConstants = CREATE_POST;
            case "5" -> {}
            case "6" -> userConstants = LOGOUT;
        }
    }

    private void printCategoryChoices() {
        System.out.println("\t1: Abortions & Sins");
        System.out.println("\t2: The classic popes");
        System.out.println("\t3: Fun with bible");
        System.out.println("\t4: Confessions");
        System.out.println("\t5: Back");
    }

    private void createNewPost() {
        System.out.println("Pick a category:");
        printCategoryChoices();
        String category = newPostCategoryChoice(scanner.nextLine());
        if (!category.isEmpty()) {
            System.out.println("Enter a title for your post:\nCategory: " + category);
            String title = scanner.nextLine();
            App.DATASOURCE.createPost(user, category, title);
            System.out.println("Post successfully created!");
            userConstants = MAIN_MENU;
        }
    }

    private String newPostCategoryChoice(String input) {
        switch (input) {
            case "1" -> {return CATEGORY_1;}
            case "2" -> {return CATEGORY_2;}
            case "3" -> {return CATEGORY_3;}
            case "4" -> {return CATEGORY_4;}
            default -> {userConstants = MAIN_MENU; return "";}
        }
    }

    private void categoryAlternatives(String input) {
        switch (input) {
            case "1" -> {App.DATASOURCE.queryPostsByCategory(CATEGORY_1);}
            case "2" -> {App.DATASOURCE.queryPostsByCategory(CATEGORY_2);}
            case "3" -> {App.DATASOURCE.queryPostsByCategory(CATEGORY_3);}
            case "4" -> {App.DATASOURCE.queryPostsByCategory(CATEGORY_4);}
            case "5" -> {userConstants = MAIN_MENU;}
        }
    }

}
