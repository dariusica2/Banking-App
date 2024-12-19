package org.poo.bank.commands;

import com.fasterxml.jackson.databind.node.ArrayNode;
import org.poo.bank.BankDataBase;
import org.poo.bank.account.Account;

import java.util.HashMap;

public final class ChangeInterestRate {
    /**
     * Utility class requirement
     */
    private ChangeInterestRate() {
    }

    public static void execute(final BankDataBase bankDataBase,
                               final String account, final double newInterestRate,
                               final int timestamp, final ArrayNode output) {
        HashMap<String, Account> accountMap = bankDataBase.getAccountMap();

        Account selectedAccount = accountMap.get(account);

        selectedAccount.changeInterestRate(newInterestRate, timestamp, output);

    }
}
