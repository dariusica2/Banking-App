package org.poo.bank.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.bank.BankDataBase;
import org.poo.bank.CommerciantInfo;
import org.poo.bank.Output;
import org.poo.bank.Transaction;
import org.poo.bank.account.Account;

import java.util.ArrayList;
import java.util.Comparator;
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
            Output.accountNotFound(timestamp, menuNode);

            output.add(menuNode);
            return;
        }

        ObjectMapper mapper = new ObjectMapper();

        ObjectNode menuNode = mapper.createObjectNode();
        menuNode.put("command", "spendingsReport");

        ArrayNode transactionsNode = mapper.createArrayNode();

        ArrayList<Transaction> transactions = selectedAccount.getAccountTransactions();
        HashMap<String, CommerciantInfo> commerciantMap = new HashMap<String, CommerciantInfo>();
        for (Transaction transaction : transactions) {
            if (startTimestamp <= transaction.getTimestamp()
                    && transaction.getTimestamp() <= endTimestamp
                    && transaction.getTransactionType() == 4) {
                ObjectNode transactionNode = mapper.createObjectNode();
                transactionNode.put("amount", transaction.getAmount());
                transactionNode.put("commerciant", transaction.getCommerciant());
                transactionNode.put("description", transaction.getDescription());
                transactionNode.put("timestamp", transaction.getTimestamp());

                transactionsNode.add(transactionNode);

                String commerciantName = transaction.getCommerciant();

                if (!commerciantMap.containsKey(commerciantName)) {
                    commerciantMap.put(commerciantName,
                            new CommerciantInfo(commerciantName, transaction.getAmount()));
                } else {
                    commerciantMap.get(commerciantName).increaseAmount(transaction.getAmount());
                }
            }
        }

        ArrayList<CommerciantInfo> commerciantList
                = new ArrayList<CommerciantInfo>(commerciantMap.size());
        commerciantList.addAll(commerciantMap.values());
        commerciantList.sort(Comparator.comparing(CommerciantInfo::getCommerciant));

        ArrayNode commerciantsNode = mapper.createArrayNode();
        for (CommerciantInfo commerciantInfo : commerciantList) {
            ObjectNode commerciantNode = mapper.createObjectNode();
            commerciantNode.put("commerciant", commerciantInfo.getCommerciant());
            commerciantNode.put("total", commerciantInfo.getAmount());
            commerciantsNode.add(commerciantNode);
        }

        ObjectNode outputNode = mapper.createObjectNode();

        outputNode.put("balance", selectedAccount.getBalance());

        // Commerciants

        outputNode.set("commerciants", commerciantsNode);

        outputNode.put("currency", selectedAccount.getCurrency());
        outputNode.put("IBAN", selectedAccount.getIban());

        outputNode.set("transactions", transactionsNode);

        menuNode.set("output", outputNode);

        menuNode.put("timestamp", timestamp);

        output.add(menuNode);
    }
}
