package menu.system;

public enum MenuConstants {

    MAIN_MENU, USER_TABLE_MENU, POST_TABLE_MENU;

    private static MenuConstants menuConstants = MenuConstants.MAIN_MENU;

    public static MenuConstants getMenuConstants() {
        return menuConstants;
    }

    public static void setMenuConstants(MenuConstants menuConstants) {
        MenuConstants.menuConstants = menuConstants;
    }
}
