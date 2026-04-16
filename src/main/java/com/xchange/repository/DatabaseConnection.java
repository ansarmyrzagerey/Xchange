package com.xchange.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * DatabaseConnection
 */
public class DatabaseConnection {
    private static final String DB_URL_PLACEHOLDER = "jdbc:sqlserver://localhost;database=XChange;encrypt=true;trustServerCertificate=true;";
    private static final String DB_USER_PLACEHOLDER = "YOUR_DB_USERNAME_HERE";
    private static final String DB_PASSWORD_PLACEHOLDER = "YOUR_DB_PASSWORD_HERE";

    private static final String url = readEnvOrDefault("XCHANGE_DB_URL", DB_URL_PLACEHOLDER);
    private static final String user = readEnvOrDefault("XCHANGE_DB_USER", DB_USER_PLACEHOLDER);
    private static final String password = readEnvOrDefault("XCHANGE_DB_PASSWORD", DB_PASSWORD_PLACEHOLDER);

    private static String readEnvOrDefault(String key, String defaultValue) {
        String value = System.getenv(key);
        if (value == null || value.isBlank()) {
            return defaultValue;
        }
        return value.trim();
    }

    public static Connection connectDatabase() {
        try {
            if (DB_USER_PLACEHOLDER.equals(user) || DB_PASSWORD_PLACEHOLDER.equals(password)) {
                throw new IllegalStateException(
                    "Missing DB credentials. Set XCHANGE_DB_USER and XCHANGE_DB_PASSWORD before running."
                );
            }
            Connection connection = DriverManager.getConnection(url, user, password);
            return connection;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
