package org.poo.bank.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.bank.BankDataBase;
import org.poo.bank.Transaction;
import org.poo.bank.User;

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
            ObjectNode transactionNode = mapper.createObjectNode();
            switch (transactionType) {
                case 1:
                    transactionNode.put("timestamp", transaction.getTimestamp());
                    transactionNode.put("description", transaction.getDescription());
                    break;
                case 2:
                    transactionNode.put("card", transaction.getCard());
                    transactionNode.put("cardHolder", transaction.getCardHolder());
                    transactionNode.put("account", transaction.getAccount());
                    transactionNode.put("description", transaction.getDescription());
                    transactionNode.put("timestamp", transaction.getTimestamp());
                    break;
                case 3:
                    transactionNode.put("card", transaction.getCard());
                    transactionNode.put("cardHolder", transaction.getCardHolder());
                    transactionNode.put("account", transaction.getAccount());
                    transactionNode.put("description", transaction.getDescription());
                    transactionNode.put("timestamp", transaction.getTimestamp());
                    break;
                case 4:
                    transactionNode.put("amount", transaction.getAmount());
                    transactionNode.put("commerciant", transaction.getCommerciant());
                    transactionNode.put("description", transaction.getDescription());
                    transactionNode.put("timestamp", transaction.getTimestamp());
                    break;
                case 5:
                    transactionNode.put("amount", transaction.getAmountCurrency());
                    transactionNode.put("description", transaction.getDescription());
                    transactionNode.put("receiverIBAN", transaction.getReceiverIBAN());
                    transactionNode.put("senderIBAN", transaction.getSenderIBAN());
                    transactionNode.put("timestamp", transaction.getTimestamp());
                    transactionNode.put("transferType", transaction.getTransferType());
                    break;
                case 6:
                    transactionNode.put("amount", transaction.getAmount());
                    transactionNode.put("currency", transaction.getCurrency());
                    transactionNode.put("description", transaction.getDescription());
                    if (transaction.getError() != null) {
                        transactionNode.put("error", transaction.getError());
                    }
                    ArrayNode accountsArray = mapper.createArrayNode();
                    transaction.getInvolvedAccounts().forEach(accountsArray::add);
                    transactionNode.set("involvedAccounts", accountsArray);
                    transactionNode.put("timestamp", transaction.getTimestamp());
                    break;
                default:
                    throw new IllegalArgumentException("Invalid transaction type");
            }
            outputNode.add(transactionNode);
        }

        menuNode.set("output", outputNode);

        menuNode.put("timestamp", timestamp);

        output.add(menuNode);
    }
}
