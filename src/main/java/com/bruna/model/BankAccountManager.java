package com.bruna.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class BankAccountManager {
    private List<BankAccount> accounts = new ArrayList<>();

    public BankAccountManager() {
    }

    public void listAccounts() {
        for (BankAccount account : accounts) {
            System.out.println(account);
        }
    }

    public boolean addAccount(BankAccount account) {
        if(account == null){
            return false;
        }
        if (findAccountByNumber(account.getAccountNumber()) != null) {
            System.out.println("The account already exists");
            return false;
        }
        accounts.add(account);
        return true;
    }

    public BankAccount findAccountByNumber(String accountNumber) {
        for (BankAccount account : accounts) {
            if (account.getAccountNumber().equals(accountNumber)) {
                return account;
            }
        }
        return null;
    }

    public boolean transferMoney(String from, String to, BigDecimal amount) {
        BankAccount originAccount = findAccountByNumber(from); //findAccountByNumber returns the obj i'm trying to find. returns none if don't
        BankAccount destinyAccount = findAccountByNumber(to);

        if (originAccount == null) { //in case the obj wasn't find, return false
            System.out.println("Account to be transferred from couldn't be found.");
        } else if (destinyAccount == null) {
            System.out.println("Account to be transferred to couldn't be found");
            return false;
        }

        if(originAccount.equals(destinyAccount)){ //if both obj are the same
            System.out.println("The accounts are the same.");
            return false;
        }

        if ((amount == null)||(amount.compareTo(BigDecimal.ZERO) <= 0)) { //less than = -1 ; equal = 0; greater = 1
            System.out.println("The money amount to be transferred isn't correct");
            return false;
        } else {
            if (originAccount.withdrawMoney(amount)) {
                destinyAccount.depositMoney(amount);
                return true;
            }
        }
        return false;
    }

}
