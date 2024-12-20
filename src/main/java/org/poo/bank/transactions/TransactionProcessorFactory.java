package org.poo.bank.transactions;

import org.poo.bank.Constants;
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
        /*
        *
        * */
        return switch (transactionType) {
            case Constants.STANDARD -> new DefaultTransactionProcessor();
            case Constants.CARD -> new CardTransactionProcessor();
            case Constants.PAYMENT -> new PaymentTransactionProcessor();
            case Constants.TRANSFER -> new TransferTransactionProcessor();
            case Constants.SPLIT -> new SplitTransactionProcessor();
            default -> throw new IllegalArgumentException("Uhh... unknown transaction type...");
        };
    }
}
