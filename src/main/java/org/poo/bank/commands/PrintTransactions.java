package org.poo.bank.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.bank.BankDataBase;
import org.poo.bank.transactions.Transaction;
import org.poo.bank.User;
import org.poo.bank.transactions.TransactionProcessor;
import org.poo.bank.transactions.TransactionProcessorFactory;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public final class PrintTransactions {
    /**
     * Utility class requirement
     */
    private PrintTransactions() {
    }

    /**
     * Prints all transactions of a specific user
     * @param bankDataBase database containing all information about users, accounts,
     *                     cards and exchange rates
     * @param email specific email associated to a single user
     */
    public static void execute(final BankDataBase bankDataBase,
                               final String email,
                               final int timestamp, final ArrayNode output) {
        LinkedHashMap<String, User> userMap = bankDataBase.getUserMap();

        ObjectMapper mapper = new ObjectMapper();

        ObjectNode menuNode = mapper.createObjectNode();
        menuNode.put("command", "printTransactions");

        ArrayNode outputNode = mapper.createArrayNode();

        User selectedUser = userMap.get(email);
        ArrayList<Transaction> transactions = selectedUser.getTransactions();
        for (Transaction transaction : transactions) {
            int transactionType = transaction.getTransactionType();

            TransactionProcessor processor = TransactionProcessorFactory
                    .generateProcessor(transactionType);

            ObjectNode transactionNode = processor.process(transaction, mapper);

            outputNode.add(transactionNode);
        }

        menuNode.set("output", outputNode);

        menuNode.put("timestamp", timestamp);

        output.add(menuNode);
    }
}
