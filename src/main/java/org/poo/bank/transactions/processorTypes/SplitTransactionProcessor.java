package org.poo.bank.transactions.processorTypes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.bank.transactions.Transaction;
import org.poo.bank.transactions.TransactionProcessor;

public class SplitTransactionProcessor implements TransactionProcessor {
    public ObjectNode process(Transaction transaction, ObjectMapper mapper) {
        ObjectNode transactionNode = mapper.createObjectNode();
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
        return transactionNode;
    }
}
