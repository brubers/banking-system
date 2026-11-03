package com.bruna.dao;

import com.bruna.database.DBConnection;
import com.bruna.model.Client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientDAO {
    public int save(Client client) {
        if(client == null){ //avaliando se client é null
            return 0;
        }
        String query = "INSERT INTO clients (name, cpf) VALUES (?, ?)";

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS); //possibilidade de executar a query diversas vezes
        ) {

            statement.setString(1, client.getName()); //1 -> está substituindo a primeira interrogacao;
            statement.setString(2, client.getCpf()); //2 -> segunda interrogacao

            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) { //para retornar nosso valor criado pelo sql)
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
        return 0;
    }

}
