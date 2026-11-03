package com.bruna.dao;

import com.bruna.database.DBConnection;
import com.bruna.model.BankAccount;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BankAccountDAO {
    public boolean save(BankAccount bankAccount, int clientId){
        if (bankAccount == null){ //analisando se bankaccount é nulo, em seguida fazemos o mesmo com ClientId
            return false;
        }

        if (clientId <= 0) {
            return false;
        }

        String query = "INSERT INTO bank_accounts(account_number, balance, client_id) VALUES (?,?,?)";

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(query) //possibilidade de executar a query diversas vezes
        ) {
            statement.setString(1, bankAccount.getAccountNumber());
            statement.setBigDecimal(2, bankAccount.getBalance());
            statement.setInt(3, clientId);

            int rowsAffected = statement.executeUpdate(); //executa o insert

            return rowsAffected > 0; //true se uma alguma linha foi inserida

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
