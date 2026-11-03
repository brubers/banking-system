package com.bruna.database;

import java.sql.Connection; //conexão ativa
import java.sql.DriverManager; //gerencia a conexao jdbc
import java.sql.SQLException;

public class DBConnection {

    private static final String URL = "JDBC:mysql://localhost:3306/banking_system";

    private static final String USER = "root";

    private static final String PASSWORD = "admin";

    public static Connection getConnection() throws SQLException{
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
