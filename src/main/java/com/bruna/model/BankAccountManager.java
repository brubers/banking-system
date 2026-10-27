package com.bruna.model;

import java.util.ArrayList;
import java.util.List;

public class BankAccountManager {
    private List<BankAccount> accounts = new ArrayList<>();

    public BankAccountManager() {
    }

    public void listAccounts(){
        for (BankAccount account : accounts){
            System.out.println(account);
        }
    }

    public boolean addAccount(BankAccount account){
        //deal with null later
        if(findAccountByNumber(account.getAccountNumber()) != null){
            System.out.println("The account already exists");
            return false;
        }
        accounts.add(account);
        return true;
    }

    public BankAccount findAccountByNumber(String accountNumber){
        for (BankAccount account : accounts){
            if (account.getAccountNumber().equals(accountNumber)){
                return account;
            }
        }
        return null;
    }


}
