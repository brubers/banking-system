package com.bruna.model;

import java.math.BigDecimal;

public class BankAccount {
    private Integer id;
    private String accountNumber; //string cuz we aren't calculating it. it is just a way to identify.
    private BigDecimal balance = BigDecimal.ZERO; //way more controlled precision! & STARTING WITH 0
    private Client owner;

    public BankAccount(String accountNumber, Client owner) {
        this.accountNumber = accountNumber;
        this.owner = owner;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public Integer getId() {
        return id;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public Client getOwner() {
        return owner;
    }

    public boolean depositMoney(BigDecimal money) {
        if ((money == null) || (money.compareTo(BigDecimal.ZERO) <= 0) ) {
            return false;
        }
        balance = balance.add(money);
        return true;
    }

    public boolean withdrawMoney(BigDecimal money) {
        if ((money == null) || (money.compareTo(BigDecimal.ZERO) <= 0) || (money.compareTo(balance) > 0)) { //comparing if the quantity of money i want to withdraw is less or equal than 0, or if it is a bigger amount than the one we already have
            return false;
        }
        balance = balance.subtract(money);
        return true;
    }

    @Override
    public String toString() {
        return "BankAccount{" +
                "id=" + id +
                ", accountNumber='" + accountNumber + '\'' +
                ", balance=" + balance +
                ", owner=" + owner +
                '}';
    }
}
