package com.bruna.testDrive;

import com.bruna.model.BankAccount;
import com.bruna.model.BankAccountManager;
import com.bruna.model.Client;

import java.math.BigDecimal;

public class BankAccountManagerTestDrive {
    void main(){

        Client client = new Client("jose", "093330000-23");
        BankAccount bankAccount = new BankAccount("02", client);

        Client client2 = new Client("roberto", "23904092384-32");
        BankAccount bankAccount1 = new BankAccount("03", client2);

        Client client3 = new Client("marcos", "2309i4032-34");
        BankAccount bankAccount2 = new BankAccount("04", client3);

        BankAccountManager bam = new BankAccountManager();

        bankAccount.depositMoney(new BigDecimal("1000"));
        bankAccount1.depositMoney(new BigDecimal("2000"));

        bam.addAccount(bankAccount);
        bam.addAccount((bankAccount1));
        bam.listAccounts();

        if (!(bam.transferMoney(bankAccount1.getAccountNumber(), bankAccount.getAccountNumber(), new BigDecimal("0")))){
            System.out.println("error");
        } else {
            System.out.println("worked");
        }

        System.out.println(bankAccount.getBalance());
        System.out.println(bankAccount1.getBalance());
//        boolean test;
//        BankAccount oo = bam.findAccountByNumber(bankAccount1.getAccountNumber());
//
//        if( oo != null){
//            System.out.println(oo);
//            test = true;
//        } else{
//            test = false;
//        }
//
//        System.out.println(test);

    }
}
