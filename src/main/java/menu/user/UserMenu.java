package menu.user;

import app.App;
import login.Login;
import model.Post;
import model.User;

import java.util.Scanner;

import static menu.user.UserConstants.*;


public class UserMenu implements Runnable {

    private final Scanner scanner = new Scanner(System.in);
    private final User user;
    private Post currentPost;

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
                case PRINT_POSTS -> {
                    App.DATASOURCE.queryPosts();
                    userConstants = ENTER_POST;
                }
                case ENTER_POST -> {
                    enterPost();
                }
                case POST_MENU -> {
                    printPostMenu();
                    postMenuAlternatives(scanner.nextLine());
                }
                case CATEGORY_MENU -> {
                    System.out.println("CATEGORY MENU");
                    printCategoryChoices();
                    categoryAlternatives(scanner.nextLine());
                }
                case SEARCH_POST -> {
                   searchPostByTitle();
                }
                case CREATE_POST -> {
                    createNewPost();
                }
                case LOGOUT -> {
                    Login.logout(user);
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
        System.out.println("\t5: Enter post");
        System.out.println("\t6: Logout");
    }

    private void searchPostByTitle(){
        System.out.println("Enter word to search by (B to return):");
        String input = scanner.nextLine();
        if (input.equalsIgnoreCase("b")) {
            userConstants = MAIN_MENU;
        } else if (App.DATASOURCE.searchPostByTitle(input)) {
            userConstants = ENTER_POST;
        } else {
            System.out.println("Failed to find any post ... ");
        }
    }

    private void mainMenuAlternatives(String input) {
        switch (input) {
            case "1" -> userConstants = PRINT_POSTS;
            case "2" -> userConstants = CATEGORY_MENU;
            case "3" -> userConstants = SEARCH_POST;
            case "4" -> userConstants = CREATE_POST;
            case "5" -> userConstants = ENTER_POST;
            case "6" -> userConstants = LOGOUT;
        }
    }

    private void printCategoryChoices() {
        System.out.println("\t1: Abortions & Sins");
        System.out.println("\t2: The classic popes");
        System.out.println("\t3: Fun with bible");
        System.out.println("\t4: Confessions");
        System.out.println("\t0: Back");
    }

    private void createNewPost() {
        System.out.println("Pick a category:");
        printCategoryChoices();
        String category = newPostCategoryChoice(scanner.nextLine());
        if (!category.isEmpty()) {
            System.out.println("Enter a title for your post:\nCategory: " + category);
            String title = scanner.nextLine();
            System.out.println("Enter text for your post:");
            String text = scanner.nextLine();
            App.DATASOURCE.createPost(user, category, title, text);
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
            case "0" -> {userConstants = MAIN_MENU; return "";}
            default -> {return "";}
        }
    }

    private void categoryAlternatives(String input) {
        switch (input) {
            case "1" -> {
                App.DATASOURCE.queryPostsByCategory(CATEGORY_1);
                userConstants = ENTER_POST;
            }
            case "2" -> {
                App.DATASOURCE.queryPostsByCategory(CATEGORY_2);
                userConstants = ENTER_POST;
            }
            case "3" -> {
                App.DATASOURCE.queryPostsByCategory(CATEGORY_3);
                userConstants = ENTER_POST;
            }
            case "4" -> {
                App.DATASOURCE.queryPostsByCategory(CATEGORY_4);
                userConstants = ENTER_POST;
            }
            case "0" -> userConstants = MAIN_MENU;
        }
    }

    private void enterPost() {
        System.out.println("Enter post ID to view post");
        System.out.println("B to go back");
        String input = scanner.nextLine();
        if (input.equalsIgnoreCase("b")) {
            userConstants = MAIN_MENU;
        } else if (input.matches("[0-9]+")) {
            currentPost = App.DATASOURCE.queryPost(Integer.parseInt(input));
            currentPost.printEntirePost();
            if (currentPost != null)
                userConstants = POST_MENU;
        } else {
            System.out.println("Enter valid input ...");
        }
    }

    private void printPostMenu() {
        System.out.println("POST MENU");
        System.out.println("1: Comment");
        System.out.println("2: back");
    }

    private void postMenuAlternatives(String input) {
        switch (input) {
            case "1" -> postComment(currentPost.getPostId());
            case "2" -> userConstants = MAIN_MENU;
        }
    }

    private void postComment(int postId){
        System.out.println("Post your comment: ");
        App.DATASOURCE.postComment(scanner.nextLine(),postId, user.getUserId());
    }

}
