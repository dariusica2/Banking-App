package org.poo.bank.transactions.processorTypes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.bank.transactions.Transaction;
import org.poo.bank.transactions.TransactionProcessor;

public final class DefaultTransactionProcessor implements TransactionProcessor {
    /*
     *
     * */
    public ObjectNode process(final Transaction transaction, final ObjectMapper mapper) {
        ObjectNode transactionNode = mapper.createObjectNode();
        transactionNode.put("timestamp", transaction.getTimestamp());
        transactionNode.put("description", transaction.getDescription());
        return transactionNode;
    }
}
