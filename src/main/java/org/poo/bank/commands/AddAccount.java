package org.poo.bank.commands;

import org.poo.bank.Account;
import org.poo.bank.User;

import org.poo.utils.Utils;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class AddAccount {
    /**
     * Utility class requirement
     */
    private AddAccount() {
    }

    public static void execute(LinkedHashMap<String, User> userMap, HashMap<String, Account> accountMap,
                               String email, String currency, String accountType,
                               int timestamp) {
        User selectedUser = userMap.get(email);
        if (selectedUser == null) {
            return;
        }
        String iban = Utils.generateIBAN();
        Account account = new Account(iban, currency, accountType);

        selectedUser.getAccounts().add(account);
        accountMap.put(iban, account);
    }
}
