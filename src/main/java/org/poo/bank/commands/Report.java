package org.poo.bank.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.bank.BankDataBase;
import org.poo.bank.Output;
import org.poo.bank.transactions.Transaction;
import org.poo.bank.User;
import org.poo.bank.account.Account;
import org.poo.bank.transactions.TransactionProcessor;
import org.poo.bank.transactions.TransactionProcessorFactory;

import java.util.ArrayList;
import java.util.HashMap;

public final class Report {
    /**
     * Utility class requirement
     */
    private Report() {
    }

    /**
     * Creates a report based on a given account
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

            menuNode.put("command", "report");
            Output.descriptionNode("Account not found", timestamp, menuNode);

            output.add(menuNode);
            return;
        }

        User parentUser = selectedAccount.getParentUser();

        ObjectMapper mapper = new ObjectMapper();

        ObjectNode menuNode = mapper.createObjectNode();
        menuNode.put("command", "report");

        ObjectNode outputNode = mapper.createObjectNode();

        outputNode.put("balance", selectedAccount.getBalance());
        outputNode.put("currency", selectedAccount.getCurrency());
        outputNode.put("IBAN", selectedAccount.getIban());

        ArrayNode transactionsNode = mapper.createArrayNode();

        ArrayList<Transaction> transactions = selectedAccount.getAccountTransactions();

        for (Transaction transaction : transactions) {
            if (startTimestamp <= transaction.getTimestamp()
                    && transaction.getTimestamp() <= endTimestamp) {
                int transactionType = transaction.getTransactionType();

                TransactionProcessor processor = TransactionProcessorFactory
                        .generateProcessor(transactionType);

                ObjectNode transactionNode = processor.process(transaction, mapper);

                transactionsNode.add(transactionNode);
            }
        }

        outputNode.set("transactions", transactionsNode);

        menuNode.set("output", outputNode);

        menuNode.put("timestamp", timestamp);

        output.add(menuNode);
    }
}
