package org.poo.bank.commands;

import org.poo.bank.Account;
import org.poo.bank.BankDataBase;

import java.util.HashMap;

public class AddFunds {
    /**
     * Utility class requirement
     */
    private AddFunds() {
    }

    public static void execute(BankDataBase bankDataBase,
                               String account, double amount,
                               int timestamp) {
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
