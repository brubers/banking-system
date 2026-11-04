package com.bruna.database;

import java.sql.Connection; //conexão ativa
import java.sql.DriverManager; //gerencia a conexao jdbc
import java.sql.SQLException;

public class DBConnection {

    private static final String URL = "url";
    private static final String USER = "user";
    private static final String PASSWORD = "password";

    public static Connection getConnection() throws SQLException{
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
