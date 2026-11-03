package com.bruna.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class BankAccountManager {
    //nossa lsita de contas é adicionada em conjunto com a inicializacao do bankaccountmanager.
    private final List<BankAccount> accounts = new ArrayList<>();

    //avaliando se accounts tá vazio
    public boolean isEmpty() {
        return accounts.isEmpty();
    }

    public void listAccounts() {
        for (BankAccount account : accounts) {
            System.out.println(account);
        }
    }

    public boolean addAccount(BankAccount account) {
        if (account == null) {
            return false;
        }

        if (findAccountByNumber(account.getAccountNumber()) != null) {
            return false;
        }

        accounts.add(account);
        return true;
    }

    public BankAccount findAccountByNumber(String accountNumber) {
        if (accountNumber == null) {
            return null;
        }

        for (BankAccount account : accounts) {
            if (account.getAccountNumber().equals(accountNumber)) {
                return account;
            }
        }

        return null;
    }

    public boolean transferMoney(String from, String to, BigDecimal amount) {
        BankAccount originAccount = findAccountByNumber(from);
        BankAccount destinyAccount = findAccountByNumber(to);

        if (originAccount == null || destinyAccount == null) {
            return false;
        }

        if (originAccount.equals(destinyAccount)) {
            return false;
        }

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            return false;
        }

        if (!originAccount.withdrawMoney(amount)) {
            return false;
        }

        return destinyAccount.depositMoney(amount);
    }
}
