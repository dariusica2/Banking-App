package org.poo.bank.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.bank.BankDataBase;
import org.poo.bank.Output;
import org.poo.bank.Transaction;
import org.poo.bank.User;
import org.poo.bank.account.Account;

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
            Output.accountNotFound(timestamp, menuNode);

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

        ArrayList<Transaction> transactions = parentUser.getTransactions();

        for (Transaction transaction : transactions) {
            if (startTimestamp <= transaction.getTimestamp()
                    && transaction.getTimestamp() <= endTimestamp) {
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
                transactionsNode.add(transactionNode);
            }
        }

        outputNode.set("transactions", transactionsNode);

        menuNode.set("output", outputNode);

        menuNode.put("timestamp", timestamp);

        output.add(menuNode);
    }
}
