package org.poo.bank.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.bank.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class SendMoney {
    /**
     * Utility class requirement
     */
    private SendMoney() {
    }

    public static void execute(BankDataBase bankDataBase,
                               String account, double amount, String receiver,
                               int timestamp,
                               String description) {
        HashMap<String, Account> accountMap = bankDataBase.getAccountMap();
        HashMap<String, HashMap<String, Double>> exchangeRateMap = bankDataBase.getExchangeRateMap();

        // Checking if sender account exists
        Account senderAccount = accountMap.get(account);
        if (senderAccount == null) {
            return;
        }

        // Checking if receiver account exists
        Account receiverAccount = accountMap.get(receiver);
        if (receiverAccount == null) {
            return;
        }

        // If balance would below zero
        if (amount > senderAccount.getBalance()) {
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
        User senderUser = senderAccount.getParentUser();
        senderUser.getTransactions().add(senderTransaction);

        String receiverAmountCurrency = receivedAmount + " " + receiverCurrency;
        Transaction receiverTransaction = new Transaction.Builder(5, timestamp, description)
                                              .putSenderIBAN(account)
                                              .putReceiverIBAN(receiver)
                                              .putAmountCurrency(receiverAmountCurrency)
                                              .putTransferType("received").build();
        User receiverUser = receiverAccount.getParentUser();
        receiverUser.getTransactions().add(receiverTransaction);
    }
}
