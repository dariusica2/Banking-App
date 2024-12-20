package org.poo.bank.transactions.processorTypes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.bank.transactions.Transaction;
import org.poo.bank.transactions.TransactionProcessor;

public final class CardTransactionProcessor implements TransactionProcessor {
    /*
    *
    * */
    public ObjectNode process(final Transaction transaction, final ObjectMapper mapper) {
        ObjectNode transactionNode = mapper.createObjectNode();
        transactionNode.put("card", transaction.getCard());
        transactionNode.put("cardHolder", transaction.getCardHolder());
        transactionNode.put("account", transaction.getAccount());
        transactionNode.put("description", transaction.getDescription());
        transactionNode.put("timestamp", transaction.getTimestamp());
        return transactionNode;
    }
}
