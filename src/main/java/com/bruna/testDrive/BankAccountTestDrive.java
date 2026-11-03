package com.bruna.testDrive;

import com.bruna.model.BankAccount;
import com.bruna.model.Client;

import java.math.BigDecimal;

public class BankAccountTestDrive {
    void main() {

        Client client = new Client("jose", "093330000-23");
        BankAccount bankAccount = new BankAccount("02", client);

        System.out.println(bankAccount.getBalance());

        bankAccount.depositMoney(new BigDecimal("-50"));

        System.out.println(bankAccount.getBalance());

        bankAccount.withdrawMoney(new BigDecimal("0"));

        System.out.println(bankAccount.getBalance());
    }

}
