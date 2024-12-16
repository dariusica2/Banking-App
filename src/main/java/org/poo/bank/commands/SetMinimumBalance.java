package org.poo.bank.commands;

import org.poo.bank.Account;
import org.poo.bank.BankDataBase;

import java.util.HashMap;

public class SetMinimumBalance {
    /**
     * Utility class requirement
     */
    private SetMinimumBalance() {
    }

    public static void execute(BankDataBase bankDataBase,
                               double amount, String account,
                               int timestamp) {
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
