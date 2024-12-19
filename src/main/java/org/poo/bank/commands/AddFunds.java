package org.poo.bank.commands;

import org.poo.bank.account.Account;
import org.poo.bank.BankDataBase;

import java.util.HashMap;

public final class AddFunds {
    /**
     * Utility class requirement
     */
    private AddFunds() {
    }

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
