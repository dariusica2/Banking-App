package org.poo.bank.commands;

import org.poo.bank.account.Account;
import org.poo.bank.BankDataBase;
import org.poo.bank.Transaction;
import org.poo.bank.User;

import org.poo.bank.account.AccountFactory;
import org.poo.bank.account.AccountInfo;
import org.poo.utils.Utils;

import java.util.HashMap;
import java.util.LinkedHashMap;

public final class AddAccount {
    /**
     * Utility class requirement
     */
    private AddAccount() {
    }

    public static void execute(final BankDataBase bankDataBase,
                               final String email, final String currency, final String accountType,
                               final int timestamp, final double interestRate) {
        LinkedHashMap<String, User> userMap = bankDataBase.getUserMap();
        HashMap<String, Account> accountMap = bankDataBase.getAccountMap();

        // Checking if user exists
        User selectedUser = userMap.get(email);
        if (selectedUser == null) {
            return;
        }

        // Creating new account
        String iban = Utils.generateIBAN();
//        Account createdAccount = new Account(iban, currency, accountType, selectedUser);
        AccountInfo accountInfo = new AccountInfo(iban, currency, accountType,
                interestRate, selectedUser);
        Account createdAccount = AccountFactory.createAccount(accountInfo);

        // Adding created account in the user's account list
        selectedUser.getAccounts().add(createdAccount);
        // Mapping the account to its IBAN
        accountMap.put(iban, createdAccount);

        // Adding specific transaction
        Transaction transaction = new Transaction.Builder(1, timestamp,
                "New account created").build();
        selectedUser.getTransactions().add(transaction);
        createdAccount.getAccountTransactions().add(transaction);
    }
}
