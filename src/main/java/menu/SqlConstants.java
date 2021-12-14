package menu;


public class SqlConstants {

    public static final String TABLE_POSTS = "posts";
    public static final String COLUMN_POST_ID = "ID";
    public static final String COLUMN_POSTER_ID = "poster_id";
    public static final String COLUMN_CATEGORY = "category";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_POST_TEXT = "text";
    public static final int INDEX_POST_ID = 1;
    public static final int INDEX_POSTER_ID = 2;
    public static final int INDEX_CATEGORY = 3;
    public static final int INDEX_TITLE = 4;
    public static final int INDEX_POST_TEXT = 5;
    public static final int INDEX_POSTER_NAME = 6;

    public static final String TABLE_USERS = "users";
    public static final String COLUMN_USER_ID = "ID";
    public static final String COLUMN_USER_NAME = "user_name";
    public static final String COLUMN_USER_EMAIL = "email";
    public static final String COLUMN_USER_PASSWORD = "password";
    public static final String COLUMN_USER_LEVEL = "level";
    public static final int INDEX_USER_ID = 1;
    public static final int INDEX_USER_NAME = 2;
    public static final int INDEX_USER_EMAIL = 3;
    public static final int INDEX_USER_PASSWORD = 4;
    public static final int INDEX_USER_LEVEL = 5;

    public static final String TABLE_COMMENTS = "comments";
    public static final String COLUMN_INDEX = "_index";
    public static final String COLUMN_TEXT = "text";
    public static final String COLUMN_TIME_STAMP = "time_stamp";

    public static final String CREATE_DATABASE_STATEMENT = "CREATE DATABASE IF NOT EXISTS forum_database";

    public static final String CREATE_POST_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_POSTS +
            "(" + COLUMN_POST_ID + " bigint NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
            COLUMN_POSTER_ID + " integer, " +
            COLUMN_CATEGORY + " text, " + COLUMN_TITLE + " text, " + COLUMN_POST_TEXT + " text)";

    public static final String CREATE_USER_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_USERS +
            " (" + COLUMN_USER_ID + " BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
            COLUMN_USER_NAME + " text, " +
            COLUMN_USER_EMAIL + " text, " +
            COLUMN_USER_PASSWORD + " text, " +
            COLUMN_USER_LEVEL + " varchar(8) DEFAULT 'standard'" +
            ") ";

    public static final String CREATE_COMMENTS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_COMMENTS +
            " (" + COLUMN_POST_ID + " integer, " +
            COLUMN_POSTER_ID + " integer, " +
            COLUMN_INDEX + " integer, " +
            COLUMN_TEXT + " text, " +
            COLUMN_TIME_STAMP + " text" +
            ") ";

    public static final String QUERY_COMMENTS_FROM_POST = " SELECT " + TABLE_COMMENTS + "." + COLUMN_TEXT + ", " +
            TABLE_COMMENTS + "." + COLUMN_INDEX + ", " + TABLE_COMMENTS + "." + COLUMN_TIME_STAMP +
            ", " + TABLE_USERS + "." + COLUMN_USER_NAME +
            " FROM " + TABLE_COMMENTS + " INNER JOIN " + TABLE_POSTS + " ON " + TABLE_POSTS +
            "." + COLUMN_POST_ID + "=" + TABLE_COMMENTS + "." + COLUMN_POST_ID + " AND " +
            TABLE_COMMENTS + "." + COLUMN_POST_ID + "=?" + " INNER JOIN " + TABLE_USERS +
            " ON " + TABLE_COMMENTS + "." + COLUMN_POSTER_ID + "=" + TABLE_USERS + "." + COLUMN_USER_ID;

    public static final String QUERY_POSTS_BY_CATEGORY = "SELECT * FROM "
            + TABLE_POSTS + " WHERE " + COLUMN_CATEGORY + "=?";

    public static final String QUERY_LOGIN = "SELECT " + COLUMN_USER_ID +
            ", " + COLUMN_USER_NAME + ", " + COLUMN_USER_LEVEL + " FROM "
            + TABLE_USERS + " WHERE " +COLUMN_USER_NAME + "=?" + " AND " +
            COLUMN_USER_PASSWORD + "=?";

    public static final String CREATE_POST = "INSERT INTO " + TABLE_POSTS  +
            "(" + COLUMN_POSTER_ID + ", " + COLUMN_CATEGORY +
            ", " + COLUMN_TITLE +  ", " + COLUMN_POST_TEXT +") VALUES (?,?,?,?)";

    public static final String CREATE_COMMENT = "INSERT INTO " + TABLE_COMMENTS + " VALUES" +"(?,?,?,?,?)";

    public static final String QUERY_POST = "SELECT " + TABLE_POSTS + "." + COLUMN_POST_ID + ", " + TABLE_POSTS + "." +
            COLUMN_POSTER_ID + ", " + TABLE_POSTS + "." + COLUMN_CATEGORY + ", " + TABLE_POSTS + "." + COLUMN_TITLE +
            ", " + TABLE_POSTS + "." + COLUMN_POST_TEXT + ", " + TABLE_USERS + "." + COLUMN_USER_NAME + " FROM " +
            TABLE_POSTS + " INNER JOIN " + TABLE_USERS + " ON " + TABLE_USERS + "." + COLUMN_USER_ID + "=" +
            TABLE_POSTS + "." + COLUMN_POSTER_ID + " WHERE " + TABLE_POSTS + "." + COLUMN_POST_ID + "=?";

    public static final String QUERY_USER_TABLE = "SELECT * FROM " + TABLE_USERS;
    public static final String QUERY_POST_TABLE = "SELECT * FROM " + TABLE_POSTS;

    public static final String CREATE_USER_ACCOUNT = "INSERT INTO " + TABLE_USERS + "("
            + COLUMN_USER_NAME + ", "
            + COLUMN_USER_EMAIL + ", "
            + COLUMN_USER_PASSWORD + ") VALUES (?,?,?)";

    //SELECT (user_name) FROM users WHERE user_name='pelle';

    public static final String QUERY_USERNAME = "SELECT (" + COLUMN_USER_NAME + ") FROM " + TABLE_USERS + " WHERE "
                + COLUMN_USER_NAME + "=?";

    public static final String QUERY_EMAIL = "SELECT (" + COLUMN_USER_EMAIL + ") FROM " + TABLE_USERS + " WHERE "
            + COLUMN_USER_EMAIL + "=?";

    //    COLUMN_USER_NAME
//    COLUMN_USER_EMAIL
//    COLUMN_USER_PASSWORD
    public static final String UPDATE_USER_COLUMN_NAME = "UPDATE " + TABLE_USERS + " SET " + COLUMN_USER_NAME + "=? WHERE " + COLUMN_USER_ID + "=?";
    public static final String UPDATE_USER_COLUMN_EMAIL = "UPDATE " + TABLE_USERS + " SET " + COLUMN_USER_EMAIL + "=? WHERE " + COLUMN_USER_ID + "=?";
    public static final String UPDATE_USER_COLUMN_PASSWORD = "UPDATE " + TABLE_USERS + " SET " + COLUMN_USER_PASSWORD + "=? WHERE " + COLUMN_USER_ID + "=?";

    public static final String SEARCH_TABLE = "SELECT * FROM ? WHERE ? LIKE ?%";
}
