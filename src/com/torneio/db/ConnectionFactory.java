package com.torneio.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    private static final String URL      = "jdbc:mysql://localhost:3306/torneio_games"
            + "?useSSL=false&serverTimezone=America/Sao_Paulo"
            + "&allowPublicKeyRetrieval=true";
    private static final String USER     = ""; // seu usuario
    private static final String PASSWORD = ""; // sua senha

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
