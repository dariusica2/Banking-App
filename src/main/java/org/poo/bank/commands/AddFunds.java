package org.poo.bank.commands;

import org.poo.bank.BankDataBase;
import org.poo.bank.account.Account;

import java.util.HashMap;

public final class AddFunds {
    /**
     * Utility class requirement
     */
    private AddFunds() {
    }

    /**
     * Adds a specific amount of money to an account
     * @param bankDataBase database containing all information about users, accounts,
     *                     cards and exchange rates
     * @param account specific IBAN associated to a single account
     * @param amount amount of money added to the account
     */
    public static void execute(final BankDataBase bankDataBase,
                               final String account, final double amount,
                               final int timestamp) {
        HashMap<String, Account> accountMap = bankDataBase.getAccountMap();

        // Checking if account exists
        Account selectedAccount = accountMap.get(account);
        if (selectedAccount == null) {
            return;
        }

        // Increase moneys
        selectedAccount.increaseBalance(amount);
    }
}
