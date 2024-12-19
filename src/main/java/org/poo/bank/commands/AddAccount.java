package org.poo.bank.commands;

import org.poo.bank.BankDataBase;
import org.poo.bank.Transaction;
import org.poo.bank.User;
import org.poo.bank.account.Account;
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

    /**
     * Adds account to the database
     * @param bankDataBase database containing all information about users, accounts,
     *                     cards and exchange rates
     * @param email specific email associated to a single user
     * @param currency given currency of account
     * @param accountType classic or savings
     * @param interestRate value to which the interest rate is changes in a savings account
     */
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
