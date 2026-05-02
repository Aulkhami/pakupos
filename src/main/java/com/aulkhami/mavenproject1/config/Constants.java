package com.aulkhami.mavenproject1.config;

public class Constants {

    private Constants() {
    }

    public static final String APP_PROPERTIES_PATH = "/config/application.properties";
    public static final String DB_PROPERTIES_PATH = "/config/database.properties";

    public static final String APP_NAME_KEY = "app.name";
    public static final String APP_VERSION_KEY = "app.version";

    public static final String ACTIVE_DB_KEY = "active.database";

    public static final String MYSQL_URL_KEY = "mysql.url";
    public static final String MYSQL_USERNAME_KEY = "mysql.username";
    public static final String MYSQL_PASSWORD_KEY = "mysql.password";
    public static final String MYSQL_DRIVER_KEY = "mysql.driver";

    public static final String SUPABASE_URL_KEY = "supabase.url";
    public static final String SUPABASE_USERNAME_KEY = "supabase.username";
    public static final String SUPABASE_PASSWORD_KEY = "supabase.password";
    public static final String SUPABASE_DRIVER_KEY = "supabase.driver";

    public static final String DEFAULT_APP_NAME = "Pakupos";
    public static final String DEFAULT_APP_VERSION = "0.1.0";
    public static final String DEFAULT_ACTIVE_DB = "mysql";

    public static final String DEFAULT_MYSQL_URL = "jdbc:mysql://localhost:3306/pakupos";
    public static final String DEFAULT_MYSQL_USERNAME = "root";
    public static final String DEFAULT_MYSQL_PASSWORD = "";
    public static final String DEFAULT_MYSQL_DRIVER = "com.mysql.cj.jdbc.Driver";

    public static final String DEFAULT_SUPABASE_URL = "jdbc:postgresql://db.xxxxx.supabase.co:5432/postgres";
    public static final String DEFAULT_SUPABASE_USERNAME = "postgres";
    public static final String DEFAULT_SUPABASE_PASSWORD = "";
    public static final String DEFAULT_SUPABASE_DRIVER = "org.postgresql.Driver";
}
