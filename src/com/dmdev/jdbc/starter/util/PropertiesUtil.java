package com.dmdev.jdbc.starter.util;

import java.io.IOException;
import java.util.Properties;

/**
 * @author elena
 * @project jdbcstarter
 * @date 10/05/2023
 */
public final class PropertiesUtil {

    private static final Properties PROPERTIES = new Properties();

    static { // static initialization block works only 1 time when first loads
        loadProperties();

    }

    private PropertiesUtil() {
    }

    public static String get(String key) {
        return PROPERTIES.getProperty(key);
    }

    private static void loadProperties() {
        try (var inputStream = PropertiesUtil.class.getClassLoader().getResourceAsStream(
                "application.properties")) {
            PROPERTIES.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
