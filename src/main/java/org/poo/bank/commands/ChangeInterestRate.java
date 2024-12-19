package org.poo.bank.commands;

import com.fasterxml.jackson.databind.node.ArrayNode;
import org.poo.bank.BankDataBase;
import org.poo.bank.account.Account;

import java.util.HashMap;

public class ChangeInterestRate {
    /**
     * Utility class requirement
     */
    private ChangeInterestRate() {
    }

    public static void execute(BankDataBase bankDataBase,
                               String account,double newInterestRate,
                               int timestamp, ArrayNode output) {
        HashMap<String, Account> accountMap = bankDataBase.getAccountMap();

        Account selectedAccount = accountMap.get(account);

        selectedAccount.changeInterestRate(newInterestRate, timestamp, output);

    }
}
