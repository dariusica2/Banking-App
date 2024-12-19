package org.poo.bank.commands;

import org.poo.bank.account.Account;
import org.poo.bank.BankDataBase;

import java.util.HashMap;

public final class SetMinimumBalance {
    /**
     * Utility class requirement
     */
    private SetMinimumBalance() {
    }

    public static void execute(final BankDataBase bankDataBase,
                               final double amount, final String account,
                               final int timestamp) {
        HashMap<String, Account> accountMap = bankDataBase.getAccountMap();

        // Checking if account exists
        Account selectedAccount = accountMap.get(account);
        if (selectedAccount == null) {
            return;
        }

        // Increase moneys
        selectedAccount.setMinBalance(amount);
    }
}
