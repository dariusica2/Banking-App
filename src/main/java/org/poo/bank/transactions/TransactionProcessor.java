package org.poo.bank.transactions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public interface TransactionProcessor {
    ObjectNode process(Transaction transaction, ObjectMapper mapper);
}
