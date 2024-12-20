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

    /**
     * Changes interest rate of a savings account
     * @param bankDataBase database containing all information about users, accounts,
     *                     cards and exchange rates
     * @param account specific IBAN associated to a single account
     * @param newInterestRate new interest rate of the savings account
     */
    public static void execute(final BankDataBase bankDataBase,
                               final String account, final double newInterestRate,
                               final int timestamp, final ArrayNode output) {
        HashMap<String, Account> accountMap = bankDataBase.getAccountMap();

        // Checking if account exists
        Account selectedAccount = accountMap.get(account);
        if (selectedAccount == null) {
            return;
        }

        selectedAccount.changeInterestRate(newInterestRate, timestamp, output);
    }
}
