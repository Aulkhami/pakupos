package com.aulkhami.mavenproject1.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class AppConfig {

    private static final Properties APP_PROPERTIES = new Properties();

    static {
        loadProperties();
    }

    private AppConfig() {
    }

    private static void loadProperties() {
        try (InputStream inputStream = AppConfig.class.getResourceAsStream(Constants.APP_PROPERTIES_PATH)) {
            if (inputStream != null) {
                APP_PROPERTIES.load(inputStream);
            }
        } catch (IOException ignored) {
        }
    }

    public static String get(String key, String defaultValue) {
        return APP_PROPERTIES.getProperty(key, defaultValue);
    }

    public static String getAppName() {
        return get(Constants.APP_NAME_KEY, Constants.DEFAULT_APP_NAME);
    }

    public static String getAppVersion() {
        return get(Constants.APP_VERSION_KEY, Constants.DEFAULT_APP_VERSION);
    }

    public static int getInt(String key, int defaultValue) {
        String value = APP_PROPERTIES.getProperty(key);
        if (value == null || value.isBlank()) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException ex) {
            return defaultValue;
        }
    }

    public static boolean getBoolean(String key, boolean defaultValue) {
        String value = APP_PROPERTIES.getProperty(key);
        if (value == null || value.isBlank()) {
            return defaultValue;
        }
        return Boolean.parseBoolean(value.trim());
    }
}
