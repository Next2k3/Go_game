package org.go_game.go_game_1_0.Client_Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {
    private static final String URL = "jdbc:mysql://localhost:3306/grago";
    private static final String USER = "root";
    private static final String PASSWORD = "cimvyD-4warje-siqcux";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}