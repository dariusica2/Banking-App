package org.poo.bank.commands;

import org.poo.bank.BankDataBase;
import org.poo.bank.account.Account;

import java.util.HashMap;

public final class SetMinimumBalance {
    /**
     * Utility class requirement
     */
    private SetMinimumBalance() {
    }

    /**
     * Sets the minimum balance for an account
     * @param bankDataBase database containing all information about users, accounts,
     *                     cards and exchange rates
     * @param account specific IBAN associated to a single account
     * @param amount new minimum balance
     */
    public static void execute(final BankDataBase bankDataBase,
                               final double amount, final String account,
                               final int timestamp) {
        HashMap<String, Account> accountMap = bankDataBase.getAccountMap();

        // Checking if account exists
        Account selectedAccount = accountMap.get(account);
        if (selectedAccount == null) {
            return;
        }

        selectedAccount.setMinBalance(amount);
    }
}
