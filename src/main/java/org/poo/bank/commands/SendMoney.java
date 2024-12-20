package org.poo.bank.commands;

import org.poo.bank.BankDataBase;
import org.poo.bank.Transaction;
import org.poo.bank.User;
import org.poo.bank.account.Account;

import java.util.HashMap;

public final class SendMoney {
    /**
     * Utility class requirement
     */
    private SendMoney() {
    }

    /**
     * Checks if the receiver is an alias and sends the money to that account (if there are
     * enough funds)
     * @param bankDataBase database containing all information about users, accounts,
     *                     cards and exchange rates
     * @param account specific IBAN associated to a single account
     * @param amount amount to be sent
     * @param description description
     * @param receiver alias or IBAN associated to a single account
     */
    public static void execute(final BankDataBase bankDataBase,
                               final String account, final double amount, String receiver,
                               final int timestamp,
                               final String description) {
        HashMap<String, Account> accountMap = bankDataBase.getAccountMap();
        HashMap<String, HashMap<String, Double>> exchangeRateMap
                = bankDataBase.getExchangeRateMap();

        // Checking if sender account exists
        Account senderAccount = accountMap.get(account);
        if (senderAccount == null) {
            return;
        }

        // Check is the receiver is an alias
        HashMap<String, String> aliasMap = senderAccount.getParentUser().getAliasMap();
        if (aliasMap.get(receiver) != null) {
            receiver = aliasMap.get(receiver);
        }

        // Checking if receiver account exists
        Account receiverAccount = accountMap.get(receiver);
        if (receiverAccount == null) {
            return;
        }

        User senderUser = senderAccount.getParentUser();
        User receiverUser = receiverAccount.getParentUser();

        // If balance would below zero
        if (amount > senderAccount.getBalance()) {
            Transaction transaction = new Transaction.Builder(1, timestamp,
                    "Insufficient funds").build();
            senderUser.getTransactions().add(transaction);
            senderAccount.getAccountTransactions().add(transaction);
            return;
        }

        senderAccount.decreaseBalance(amount);

        String senderCurrency = senderAccount.getCurrency();
        String receiverCurrency = receiverAccount.getCurrency();

        double receivedAmount;
        if (senderCurrency.equals(receiverCurrency)) {
            receivedAmount = amount;
        } else {
            double rate = exchangeRateMap.get(senderCurrency).get(receiverCurrency);
            receivedAmount = amount * rate;
        }
        receiverAccount.increaseBalance(receivedAmount);

        String senderAmountCurrency = amount + " " + senderCurrency;
        Transaction senderTransaction = new Transaction.Builder(5, timestamp, description)
                                            .putSenderIBAN(account)
                                            .putReceiverIBAN(receiver)
                                            .putAmountCurrency(senderAmountCurrency)
                                            .putTransferType("sent").build();
        senderUser.getTransactions().add(senderTransaction);
        senderAccount.getAccountTransactions().add(senderTransaction);

        String receiverAmountCurrency = receivedAmount + " " + receiverCurrency;
        Transaction receiverTransaction = new Transaction.Builder(5, timestamp, description)
                                              .putSenderIBAN(account)
                                              .putReceiverIBAN(receiver)
                                              .putAmountCurrency(receiverAmountCurrency)
                                              .putTransferType("received").build();
        receiverUser.getTransactions().add(receiverTransaction);
        receiverAccount.getAccountTransactions().add(receiverTransaction);
    }
}
