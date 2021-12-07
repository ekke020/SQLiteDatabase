package menu.user;

public enum UserConstants {

    MAIN_MENU, LOGOUT, POST_MENU, CATEGORY_MENU, SEARCH_POST, ENTER_POST;

    private static UserConstants userConstants = UserConstants.MAIN_MENU;

    public static void setUserConstants(UserConstants userConstants) {
        UserConstants.userConstants = userConstants;
    }

    public static UserConstants getUserConstants() {
        return userConstants;
    }

}
