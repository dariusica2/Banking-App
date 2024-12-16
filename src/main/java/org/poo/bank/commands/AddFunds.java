package org.poo.bank.commands;

import org.poo.bank.Account;
import org.poo.bank.User;
import org.poo.utils.Utils;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class AddFunds {
    /**
     * Utility class requirement
     */
    private AddFunds() {
    }

    public static void execute(HashMap<String, Account> accountMap,
                               String account, double amount,
                               int timestamp) {
        Account selectedAccount = accountMap.get(account);
        if (selectedAccount == null) {
            return;
        }

        selectedAccount.increaseBalance(amount);
    }
}
