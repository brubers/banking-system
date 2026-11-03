package com.bruna;

import com.bruna.dao.BankAccountDAO;
import com.bruna.dao.ClientDAO;
import com.bruna.model.BankAccount;
import com.bruna.model.BankAccountManager;
import com.bruna.model.Client;

import java.math.BigDecimal;
import java.util.Scanner;

public class Application {

    public static void main(String[] args){
        Application app = new Application();
        app.menu();



    }

    private final Scanner sc;
    private final BankAccountManager manager;
    //sql
    private ClientDAO clientDAO = new ClientDAO();
    private BankAccountDAO bankAccountDAO = new BankAccountDAO();

    public Application() {
        sc = new Scanner(System.in); //sc recebe os dados
        manager = new BankAccountManager(); //responsavel por todas as operacoes
    }

    public void menu() {
        while (true) {
            System.out.println();
            System.out.println("=====MENU====");
            System.out.println("1 - Create account");
            System.out.println("2 - List accounts");
            System.out.println("3 - Search account");
            System.out.println("4 - Deposit");
            System.out.println("5 - Withdraw");
            System.out.println("6 - Transfer");
            System.out.println("0 - Exit");

            int choice = readInt("Select one operation: ");
            if (choice == 0) {
                System.out.println("Leaving program...");
                break;
            }

            choiceMenu(choice);
        }
    }

    public void choiceMenu(int choice) {
        switch (choice) {
            case 1: {
                System.out.println(); //pede os dados pra criar a nova conta (um usuario pode ter varias contas)
                System.out.println("What is the account holder's name?");
                String name = sc.nextLine();

                System.out.println("What is the holder's CPF?");
                String cpf = sc.nextLine();

                System.out.println("What is the account number?");
                String accountNumber = sc.nextLine();

                Client client = new Client(name, cpf);
                int clientId = clientDAO.save(client); //aqui vai retornar o ID criado pelo sql pra usarmos
                if (clientId <= 0) {
                    System.out.println("Client could not be created.");
                    break;
                }

                BankAccount bankAccount = new BankAccount(accountNumber, client);
                boolean accountSaved = bankAccountDAO.save(bankAccount, clientId);

                //aqui tenta adicionar retornando um booleano pra colocarmos a mensagem
                if (accountSaved) {
                    manager.addAccount(bankAccount);
                    System.out.println("New account successfully added!");
                } else {
                    System.out.println("Account could not be created. It may already exist.");
                }

                break;
            }
            case 2: {
                System.out.println();
                if (manager.isEmpty()) {
                    System.out.println("No account has been registered.");
                    return;
                }
                manager.listAccounts();
                break;
            }
            case 3: {
                System.out.println();
                System.out.println("Insert the account number you're searching for:");
                String accountNumber = sc.nextLine();
                BankAccount account = accountWasFound(accountNumber);
                if (account != null) {
                    System.out.println(account);
                }
            }
            case 4: {
                System.out.println();
                System.out.println("Which account do you want to deposit to?");
                String accountNumber = sc.nextLine();
                BankAccount account = accountWasFound(accountNumber);

                if (account == null) {
                    return;
                }

                BigDecimal amount = readAmount("How much money will you deposit?");

                if (account.depositMoney(amount)) {
                    System.out.println("Deposit successful!");
                } else {
                    System.out.println("Invalid deposit amount.");
                }
                break;
            }
            case 5: {
                System.out.println();
                System.out.println("Which account do you want to withdraw from?");
                String accountNumber = sc.nextLine();
                BankAccount account = accountWasFound(accountNumber);
                if (account == null) {
                    return;
                }
                BigDecimal amount = readAmount("How much money will you withdraw?");
                if (account.withdrawMoney(amount)) {
                    System.out.println("Withdrawal successful!");
                } else {
                    System.out.println("Invalid amount or insufficient balance.");
                }
                break;
            }
            case 6: {
                System.out.println();
                System.out.println("Which account do you want to transfer money from?");
                String from = sc.nextLine();
                BankAccount originAccount = accountWasFound(from);
                if (originAccount == null) {
                    return;
                }
                System.out.println("Which account do you want to transfer money to?");
                String to = sc.nextLine();
                BankAccount destinyAccount = accountWasFound(to);
                if (destinyAccount == null) {
                    return;
                }
                BigDecimal amount = readAmount("How much money is going to be transferred?");
                boolean result = manager.transferMoney(originAccount.getAccountNumber(), destinyAccount.getAccountNumber(), amount
                );
                if (result) {
                    System.out.println("Money transferred successfully!");
                } else {
                    System.out.println("Transfer could not be completed.");
                }
                break;
            }
            default:
                System.out.println("Number not valid, try again.");
                break;
        }
    }

    public BankAccount accountWasFound(String acc){
        BankAccount result = manager.findAccountByNumber(acc);
        if(result == null){
            System.out.println("Account not found.");
        }
        return result;
    }
    //usando tratamento de excecoes pra ler um int
    private int readInt(String msg) {
        while (true) {
            System.out.println(msg);
            String input = sc.nextLine();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }
    //metodo auxiliar pra ler a quantidade de dinheiro
    private BigDecimal readAmount(String message) {
        while (true) {
            System.out.println(message);
            String input = sc.nextLine().trim().replace(",", ".");

            try { //aqui converte o valor do input pra bigdecimal
                return new BigDecimal(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid amount.");
            }
        }
    }
}
