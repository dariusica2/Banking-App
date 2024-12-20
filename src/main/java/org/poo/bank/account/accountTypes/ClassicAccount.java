package org.poo.bank.account.accountTypes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.bank.CommerciantInfo;
import org.poo.bank.Constants;
import org.poo.bank.Output;
import org.poo.bank.transactions.Transaction;
import org.poo.bank.account.Account;
import org.poo.bank.account.AccountInfo;
import org.poo.bank.transactions.TransactionProcessor;
import org.poo.bank.transactions.TransactionProcessorFactory;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

public final class ClassicAccount extends Account {
    public ClassicAccount(final AccountInfo accountInfo) {
        super(accountInfo);
    }

    /**
     * Unsupported command for classic account
     */
    public void addInterest(final int timestamp, final ArrayNode output) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode menuNode = mapper.createObjectNode();

        menuNode.put("command", "addInterest");

        Output.descriptionNode("This is not a savings account", timestamp, menuNode);

        output.add(menuNode);
    }

    /**
     * Unsupported command for classic account
     */
    public void changeInterestRate(final double newInterestRate,
                                   final int timestamp, final ArrayNode output) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode menuNode = mapper.createObjectNode();

        menuNode.put("command", "changeInterestRate");

        ObjectNode outputNode = mapper.createObjectNode();
        outputNode.put("description", "This is not a savings account");
        outputNode.put("timestamp", timestamp);
        menuNode.set("output", outputNode);
        menuNode.put("timestamp", timestamp);

        output.add(menuNode);
    }

    /*
     *
     * */
    public void spendingsReport(final int startTimestamp, final int endTimestamp,
                                final int timestamp, final ArrayNode output) {
        ObjectMapper mapper = new ObjectMapper();

        ObjectNode menuNode = mapper.createObjectNode();
        menuNode.put("command", "spendingsReport");

        // Building transactions node
        ArrayNode transactionsNode = mapper.createArrayNode();

        ArrayList<Transaction> transactions = getAccountTransactions();
        HashMap<String, CommerciantInfo> commerciantMap = new HashMap<String, CommerciantInfo>();
        for (Transaction transaction : transactions) {
            if (startTimestamp <= transaction.getTimestamp()
                    && transaction.getTimestamp() <= endTimestamp
                    && transaction.getTransactionType() == Constants.PAYMENT) {
                TransactionProcessor processor = TransactionProcessorFactory
                        .generateProcessor(Constants.PAYMENT);

                ObjectNode transactionNode = processor.process(transaction, mapper);

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

        // Building commerciants node
        ArrayNode commerciantsNode = mapper.createArrayNode();
        for (CommerciantInfo commerciantInfo : commerciantList) {
            ObjectNode commerciantNode = mapper.createObjectNode();
            commerciantNode.put("commerciant", commerciantInfo.getCommerciant());
            commerciantNode.put("total", commerciantInfo.getAmount());
            commerciantsNode.add(commerciantNode);
        }

        // Building end node
        ObjectNode outputNode = mapper.createObjectNode();

        outputNode.put("balance", getBalance());
        outputNode.set("commerciants", commerciantsNode);
        outputNode.put("currency", getCurrency());
        outputNode.put("IBAN", getIban());
        outputNode.set("transactions", transactionsNode);
        menuNode.set("output", outputNode);
        menuNode.put("timestamp", timestamp);

        output.add(menuNode);
    }
}
