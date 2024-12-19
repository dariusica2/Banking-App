package org.poo.bank.commands;

import com.fasterxml.jackson.databind.node.ArrayNode;
import org.poo.bank.BankDataBase;
import org.poo.bank.account.Account;

import java.util.HashMap;

public final class AddInterest {
    /**
     * Utility class requirement
     */
    private AddInterest() {
    }

    /**
     * Multiplies the interest with the current balance and adds the result to the account
     * @param bankDataBase database containing all information about users, accounts,
     *                     cards and exchange rates
     * @param account specific IBAN associated to a single account
     */
    public static void execute(final BankDataBase bankDataBase,
                               final String account,
                               final int timestamp, final ArrayNode output) {
        HashMap<String, Account> accountMap = bankDataBase.getAccountMap();

        Account selectedAccount = accountMap.get(account);
        if (selectedAccount == null) {
            return;
        }

        selectedAccount.addInterest(timestamp, output);
    }
}
