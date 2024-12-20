package org.poo.bank.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.bank.BankDataBase;
import org.poo.bank.Output;
import org.poo.bank.account.Account;

import java.util.HashMap;

public final class SpendingsReport {
    /**
     * Utility class requirement
     */
    private SpendingsReport() {
    }

    /**
     * Generates a report for a specific account containing all the purchases to certain
     * commerciants
     * @param bankDataBase database containing all information about users, accounts,
     *                     cards and exchange rates
     * @param account specific IBAN associated to a single account
     * @param startTimestamp
     * @param endTimestamp
     */
    public static void execute(final BankDataBase bankDataBase,
                               final int startTimestamp, final int endTimestamp,
                               final String account,
                               final int timestamp, final ArrayNode output) {
        HashMap<String, Account> accountMap = bankDataBase.getAccountMap();

        // Checking if account exists
        Account selectedAccount = accountMap.get(account);
        if (selectedAccount == null) {
            // Writing to output
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode menuNode = mapper.createObjectNode();

            menuNode.put("command", "spendingsReport");
            Output.descriptionNode("Account not found", timestamp, menuNode);

            output.add(menuNode);
            return;
        }

        selectedAccount.spendingsReport(startTimestamp, endTimestamp, timestamp, output);
    }
}
