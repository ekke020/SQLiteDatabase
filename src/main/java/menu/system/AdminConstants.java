package menu.system;

public enum AdminConstants {

    MAIN_MENU, USER_TABLE_MENU, POST_TABLE_MENU;

    private static AdminConstants adminConstants = AdminConstants.MAIN_MENU;

    public static AdminConstants getAdminConstants() {
        return adminConstants;
    }

    public static void setMenuConstants(AdminConstants adminConstants) {
        AdminConstants.adminConstants = adminConstants;
    }
}
