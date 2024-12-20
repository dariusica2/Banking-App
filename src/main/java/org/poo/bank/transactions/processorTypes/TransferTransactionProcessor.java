package org.poo.bank.transactions.processorTypes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.bank.transactions.Transaction;
import org.poo.bank.transactions.TransactionProcessor;

public class TransferTransactionProcessor implements TransactionProcessor {
    public ObjectNode process(Transaction transaction, ObjectMapper mapper) {
        ObjectNode transactionNode = mapper.createObjectNode();
        transactionNode.put("amount", transaction.getAmountCurrency());
        transactionNode.put("description", transaction.getDescription());
        transactionNode.put("receiverIBAN", transaction.getReceiverIBAN());
        transactionNode.put("senderIBAN", transaction.getSenderIBAN());
        transactionNode.put("timestamp", transaction.getTimestamp());
        transactionNode.put("transferType", transaction.getTransferType());
        return transactionNode;
    }
}
