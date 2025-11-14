// src/main/java/com/example/demo2/DBConnection.java
package com.example.demo2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static jdk.internal.org.jline.utils.Colors.s;

public class DBConnection {
    private static final String URL = "jdbc:sqlite:inventory.db";
    private static Connection conn;

    private DBConnection() {
        // Private constructor to prevent instantiation
    }

    public static synchronized Connection getConnection() {
        if (conn == null) {
            try {
                conn = DriverManager.getConnection(URL);
                System.out.println("Database connected.");
            } catch (SQLException e) {
                System.out.println("Connection failed: " + e.getMessage());
            }
        }
        return conn;
    }
}