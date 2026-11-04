package com.bruna.dao;

import com.bruna.database.DBConnection;
import com.bruna.model.BankAccount;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BankAccountDAO {
    public boolean save(BankAccount bankAccount, int clientId) {
        if (bankAccount == null) { //analisando se bankaccount é nulo, em seguida fazemos o mesmo com ClientId
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

    public void listAccounts() {

        String query = "SELECT ba.account_number, ba.balance, c.name, c.cpf " +
                "FROM bank_accounts ba " +
                "INNER JOIN clients c ON ba.client_id = c.id"; //query pra fazer a pesquisa

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery();) {
            //enquanto houver um proxio resultado...
            while (resultSet.next()) {
                String accountNumber = resultSet.getString("account_number"); //associando a variael com o nome da coluna
                BigDecimal balance = resultSet.getBigDecimal("balance");
                String name = resultSet.getString("name");
                String cpf = resultSet.getString("cpf");

                System.out.println("Account: " + accountNumber);
                System.out.println("Balance: " + balance);
                System.out.println("Holder: " + name);
                System.out.println("CPF: " + cpf);
                System.out.println("------------------------");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void findAccountByNumber(String accountNumber) {
        String query = "SELECT ba.account_number, ba.balance, c.name, c.cpf " +
                "FROM bank_accounts ba " +
                "INNER JOIN clients c ON ba.client_id = c.id " +
                "WHERE ba.account_number = ?"; //? condicao que quero

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, accountNumber);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    String account = resultSet.getString("account_number");
                    BigDecimal balance = resultSet.getBigDecimal("balance");
                    String name = resultSet.getString("name");
                    String cpf = resultSet.getString("cpf");

                    System.out.println("Account: " + account);
                    System.out.println("Balance: " + balance);
                    System.out.println("Holder: " + name);
                    System.out.println("CPF: " + cpf);
                } else {
                    System.out.println("No account was found with this number.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean depositMoney(String accountNumber, BigDecimal amount) {
        if (accountNumber == null || amount == null) {
            return false;
        }
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            return false;
        }

        String query = "UPDATE bank_accounts " +
                "SET balance = balance + ? " +
                "WHERE account_number = ?";

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)
        ) {
            statement.setBigDecimal(1, amount);
            statement.setString(2, accountNumber);

            int rowsAffected = statement.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    public boolean withdrawMoney(String accountNumber, BigDecimal amount) {

        if (accountNumber == null || amount == null) {
            return false;
        }

        if (amount.compareTo(BigDecimal.ZERO) <= 0) { //a conta não pode ter menos do que será retiirado
            return false;
        }
        //pesquisa que será feita no sql
        String query = "UPDATE bank_accounts " +
                "SET balance = balance - ? " +
                "WHERE account_number = ? AND balance >= ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setBigDecimal(1, amount);
            statement.setString(2, accountNumber);
            statement.setBigDecimal(3, amount);

            int rowsAffected = statement.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean transferMoney(String fromAccount, String toAccount, BigDecimal amount) {
        if (fromAccount == null || toAccount == null || amount == null) {
            return false;
        }

        if (fromAccount.equals(toAccount)) {
            return false;
        }

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            return false;
        }

        String withdrawQuery = "UPDATE bank_accounts " +
                "SET balance = balance - ? " +
                "WHERE account_number = ? AND balance >= ?";

        String depositQuery = "UPDATE bank_accounts " +
                "SET balance = balance + ? " +
                "WHERE account_number = ?";

        try (Connection connection = DBConnection.getConnection()) {
            connection.setAutoCommit(false); //aqui colocamos que queremos controlar as confirmacoes das operacoes (pra voltar caso algo de erro)

            try (PreparedStatement withdrawStatement = connection.prepareStatement(withdrawQuery);
                 PreparedStatement depositStatement = connection.prepareStatement(depositQuery)) {

                //retirando o dinheiro...
                withdrawStatement.setBigDecimal(1, amount);
                withdrawStatement.setString(2, fromAccount);
                withdrawStatement.setBigDecimal(3, amount);

                int withdrawResult = withdrawStatement.executeUpdate(); //executacao da query

                if (withdrawResult == 0) {
                    connection.rollback(); //se for igual a zero, a gente desfaz a transacao
                    return false;
                }

                //depositando o dinheiro,...
                depositStatement.setBigDecimal(1, amount);
                depositStatement.setString(2, toAccount);

                int depositResult = depositStatement.executeUpdate();

                if (depositResult == 0) {
                    connection.rollback();
                    return false;
                }
                //aqui confirma, salvando.
                connection.commit();

                return true;

            } catch (SQLException e) {
                connection.rollback();
                e.printStackTrace();
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public BigDecimal getBalance(String accountNumber){
        String query = "SELECT balance FROM bank_accounts WHERE account_number = ?";

        try(Connection connection = DBConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)){

            statement.setString(1, accountNumber); //substitui o primeiro ? pelo valor

            try(ResultSet resultSet = statement.executeQuery()){ //aqui é pra gente retornar consultas, que vamos reetornar o que ela trazer pra gente
                if(resultSet.next()){
                    return resultSet.getBigDecimal("balance");
                }
                return null; //caso não encontre a conta, null.
            }
        } catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }
}
