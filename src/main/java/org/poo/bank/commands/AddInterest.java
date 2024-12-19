package org.poo.bank.commands;

import com.fasterxml.jackson.databind.node.ArrayNode;
import org.poo.bank.BankDataBase;
import org.poo.bank.account.Account;

import java.util.HashMap;

public class AddInterest {
    /**
     * Utility class requirement
     */
    private AddInterest() {
    }

    public static void execute(BankDataBase bankDataBase,
                               String account,
                               int timestamp, ArrayNode output) {
        HashMap<String, Account> accountMap = bankDataBase.getAccountMap();

        Account selectedAccount = accountMap.get(account);
        if (selectedAccount == null) {
            return;
        }

        selectedAccount.addInterest(timestamp, output);
    }
}
