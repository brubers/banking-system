package com.bruna;

import com.bruna.dao.BankAccountDAO;
import com.bruna.dao.ClientDAO;
import com.bruna.model.BankAccount;
import com.bruna.model.Client;

import java.math.BigDecimal;
import java.util.Scanner;

public class Application {

    public static void main(String[] args) {
        Application app = new Application();
        app.menu();


    }

    private final Scanner sc;
    //sql
    private final ClientDAO clientDAO = new ClientDAO();
    private final BankAccountDAO bankAccountDAO = new BankAccountDAO();

    public Application() {
        sc = new Scanner(System.in); //sc recebe os dados
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
            System.out.println("7 - Check Balance");
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
                //aqui tenta adicionar retornando um booleano pra colocarmos a mensagem
                boolean accountSaved = bankAccountDAO.save(bankAccount, clientId);

                if (accountSaved) {
                    System.out.println("New account successfully added!");
                } else {
                    System.out.println("Account could not be created.");
                }

                break;
            }
            case 2: {
                System.out.println();
                bankAccountDAO.listAccounts();
                break;
            }
            case 3: {
                System.out.println("Enter the account number:");
                String accountNumber = sc.nextLine();

                bankAccountDAO.findAccountByNumber(accountNumber);

                break;
            }
            case 4: {
                System.out.println();
                System.out.println("Which account do you want to deposit to?");

                String accountNumber = sc.nextLine();

                BigDecimal amount = readAmount("How much money will you deposit?");

                boolean result = bankAccountDAO.depositMoney(accountNumber, amount);
                if (result) {
                    System.out.println("Deposit successful!");
                } else {
                    System.out.println("Deposit could not be completed.");
                }
                break;
            }

            case 5: {
                System.out.println();
                System.out.println("Which account do you want to withdraw from?");
                String accountNumber = sc.nextLine();

                BigDecimal amount = readAmount("How much money will you withdraw?");

                boolean result = bankAccountDAO.withdrawMoney(accountNumber, amount);

                if (result) {
                    System.out.println("Withdrawal successful!");
                } else {
                    System.out.println("Withdrawal could not be completed.");
                }

                break;
            }
            case 6: {
                System.out.println();
                System.out.println("From which account?");
                String fromAccount = sc.nextLine();

                System.out.println("To which account?");
                String toAccount = sc.nextLine();

                BigDecimal amount = readAmount("How much money will you transfer?");

                boolean result = bankAccountDAO.transferMoney(fromAccount, toAccount, amount);

                if (result) {
                    System.out.println("Transfer successful!");
                } else {
                    System.out.println("Transfer could not be completed.");
                }

                break;
            }
            case 7: {
                System.out.println("Enter the account number:");
                String accountNumber = sc.nextLine();

                BigDecimal balance = bankAccountDAO.getBalance(accountNumber);

                if (balance != null) {
                    System.out.println("Current balance: R$ " + balance);
                } else {
                    System.out.println("Account not found.");
                }

                break;
            }
            default:
                System.out.println("Number not valid, try again.");
                break;
        }
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
                BigDecimal amount = new BigDecimal(input);
                if(amount.compareTo(BigDecimal.ZERO) <= 0){
                    System.out.println("Amount must be greater than zero.");
                    continue;
                }
                return amount;

            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid amount.");
            }
        }
    }
}
