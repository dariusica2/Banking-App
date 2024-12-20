package org.poo.bank.transactions.processorTypes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.bank.transactions.Transaction;
import org.poo.bank.transactions.TransactionProcessor;

public class PaymentTransactionProcessor implements TransactionProcessor {
    public ObjectNode process(Transaction transaction, ObjectMapper mapper) {
        ObjectNode transactionNode = mapper.createObjectNode();
        transactionNode.put("amount", transaction.getAmount());
        transactionNode.put("commerciant", transaction.getCommerciant());
        transactionNode.put("description", transaction.getDescription());
        transactionNode.put("timestamp", transaction.getTimestamp());
        return transactionNode;
    }
}