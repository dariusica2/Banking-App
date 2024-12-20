package org.poo.bank.transactions;

import org.poo.bank.transactions.processorTypes.CardTransactionProcessor;
import org.poo.bank.transactions.processorTypes.DefaultTransactionProcessor;
import org.poo.bank.transactions.processorTypes.PaymentTransactionProcessor;
import org.poo.bank.transactions.processorTypes.SplitTransactionProcessor;
import org.poo.bank.transactions.processorTypes.TransferTransactionProcessor;

public class TransactionProcessorFactory {
    /**
     * Utility class requirement
     */
    private TransactionProcessorFactory() {
    }

    /**
     * Returns instance of class based on transaction type
     */
    public static TransactionProcessor generateProcessor(final int transactionType) {
        return switch (transactionType) {
            case 1 -> new DefaultTransactionProcessor();
            case 2 -> new CardTransactionProcessor();
            case 3 -> new CardTransactionProcessor();
            case 4 -> new PaymentTransactionProcessor();
            case 5 -> new TransferTransactionProcessor();
            case 6 -> new SplitTransactionProcessor();
            default -> throw new IllegalArgumentException("Uhh... unknown transaction type...");
        };
    }
}
