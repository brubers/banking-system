package com.bruna;

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

    public Application() {
        sc = new Scanner(System.in);
        manager = new BankAccountManager();
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

            //exception if not int later
            System.out.println("Select one operation: ");
            int choice = sc.nextInt();
            sc.nextLine();
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
                System.out.println();
                System.out.println("What is the account holder's name");
                String name = sc.nextLine();
                System.out.println("What is the holder's CPF?");
                String cpf = sc.nextLine();
                System.out.println("What is the account number?");
                String accountNumber = sc.nextLine();

                if (manager.findAccountByNumber(accountNumber) != null){
                    System.out.println("Account already exists.");
                    break;
                }

                BankAccount bankAccount = new BankAccount(accountNumber, new Client(name, cpf));
                manager.addAccount(bankAccount);
                System.out.println("New account successfully added!");

                break;
            }
            case 2: {
                System.out.println();
                if (manager.getAccounts().isEmpty()) {
                    System.out.println("No account has been registered.");
                }
                manager.listAccounts();
                break;
            }
            case 3: {
                System.out.println();
                System.out.println("Insert the account number you're searching for: ");
                String acc = sc.nextLine();
                if(accountWasFound(acc) == null){
                    break;
                }
            }
            case 4: {
                System.out.println();
                System.out.println("Which account do you want to deposit to?");
                String acc = sc.nextLine();
                BankAccount bankFound = accountWasFound(acc);
                if(bankFound == null){
                    break;
                }
                System.out.println("How much money will you deposit?");
                BigDecimal amount = new BigDecimal(sc.nextInt());
                sc.nextLine();
                bankFound.depositMoney(amount);

            }
            case 5: {
                System.out.println();
                System.out.println("Which account do you want to withdraw from?");
                String acc = sc.nextLine();
                BankAccount bankFound = accountWasFound(acc);
                if(bankFound == null){
                    break;
                }
                System.out.println("How much money will you withdraw?");
                BigDecimal amount = new BigDecimal(sc.nextInt());
                sc.nextLine();
                bankFound.withdrawMoney(amount);
            }
            case 6: {
                System.out.println();
                System.out.println("Which account do you want to transfer money from?");
                String acc = sc.nextLine();
                BankAccount bankFound = accountWasFound(acc);
                System.out.println("Which account do you want to transfer money to?");
                String acc2 = sc.nextLine();
                BankAccount bankFound2 = accountWasFound(acc2);
                System.out.println("How much money is going to be transferred");
                BigDecimal amount = new BigDecimal(sc.nextInt());
                boolean result = manager.transferMoney(bankFound.getAccountNumber(),bankFound2.getAccountNumber(), amount);
                if (result){
                    System.out.println("Money transferred successfully!");
                } else {
                    System.out.println("An error occurred");
                }
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
            return result;
        }
        return result;
    }
}
