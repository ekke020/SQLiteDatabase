package menu;

import java.util.HashMap;

public class SqlConstants {

    public static final String TABLE_POSTS = "posts";
    public static final String COLUMN_POST_ID = "ID";
    public static final String COLUMN_POSTER_ID = "poster_id";
    public static final String COLUMN_CATEGORY = "category";
    public static final String COLUMN_TITLE = "title";
    public static final int INDEX_POST_ID = 1;
    public static final int INDEX_POSTER_ID = 2;
    public static final int INDEX_CATEGORY = 3;
    public static final int INDEX_TITLE = 4;

    public static final String TABLE_USERS = "users";
    public static final String COLUMN_USER_ID = "ID";
    public static final String COLUMN_USER_NAME = "user_name";
    public static final String COLUMN_USER_EMAIL = "email";
    public static final String COLUMN_USER_PASSWORD = "password";
    public static final int INDEX_USER_ID = 1;
    public static final int INDEX_USER_NAME = 2;
    public static final int INDEX_USER_EMAIL = 3;
    public static final int INDEX_USER_PASSWORD = 4;

    public static final String TABLE_COMMENTS = "comments";
    public static final String COLUMN_INDEX = "_index";
    public static final String COLUMN_TEXT = "text";
    public static final String COLUMN_TIME_STAMP = "time_stamp";

    public static final String CREATE_DATABASE_STATEMENT = "CREATE DATABASE IF NOT EXISTS forum_database";

    public static final String CREATE_POST_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_POSTS +
            "(" + COLUMN_POST_ID + " bigint NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
            COLUMN_POSTER_ID + " integer, " +
            COLUMN_CATEGORY + " text, " + COLUMN_TITLE + " text)";

    public static final String CREATE_USER_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_USERS +
            " (" + COLUMN_USER_ID + " BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
            COLUMN_USER_NAME + " text, " +
            COLUMN_USER_EMAIL + " text, " +
            COLUMN_USER_PASSWORD + " text" +
            ") ";

    public static final String CREATE_COMMENTS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_COMMENTS +
            " (" + COLUMN_POST_ID + " text, " +
            COLUMN_POSTER_ID + " text, " +
            COLUMN_INDEX + " integer, " +
            COLUMN_TEXT + " text, " +
            COLUMN_TIME_STAMP + " text" +
            ") ";

    public static final String QUERY_COMMENTS_FROM_POST = " SELECT " + TABLE_COMMENTS + "." + COLUMN_TEXT + ", " +
            TABLE_COMMENTS + "." + COLUMN_TIME_STAMP + ", " + TABLE_USERS + "."
            + COLUMN_USER_NAME + " FROM " + TABLE_COMMENTS + " INNER JOIN " +
            TABLE_POSTS + " ON " + TABLE_POSTS + "." + COLUMN_POST_ID + "=" + TABLE_COMMENTS + "." +
            COLUMN_POST_ID + " AND " + TABLE_COMMENTS + "." + COLUMN_POST_ID + "=?" +
            " INNER JOIN " + TABLE_USERS + " ON " + TABLE_COMMENTS + "." +
            COLUMN_POSTER_ID + "=" + TABLE_USERS + "." + COLUMN_USER_ID;

    public static final String QUERY_POSTS_BY_CATEGORY = "SELECT * FROM "
            + TABLE_POSTS + " WHERE " + COLUMN_CATEGORY + "=?";

    public static final String QUERY_LOGIN = "SELECT " + COLUMN_USER_ID +", " + COLUMN_USER_NAME + " FROM "
            + TABLE_USERS +" WHERE " +COLUMN_USER_NAME + "=?" +" AND " + COLUMN_USER_PASSWORD +"=?";

    public static final String CREATE_POST = "INSERT INTO " + TABLE_POSTS  +
            "(" + COLUMN_POSTER_ID + ", " + COLUMN_CATEGORY +
            ", " + COLUMN_TITLE + ") VALUES (?,?,?)";
}
