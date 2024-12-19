package org.poo.bank.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.bank.*;
import org.poo.bank.account.Account;
import org.poo.bank.card.Card;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class PayOnline {
    /**
     * Utility class requirement
     */
    private PayOnline() {
    }

    public static void execute(BankDataBase bankDataBase,
                               String cardNumber, double amount, String currency,
                               int timestamp,
                               String description, String commerciant, String email, ArrayNode output) {
        LinkedHashMap<String, User> userMap = bankDataBase.getUserMap();
        HashMap<String, Card> cardMap = bankDataBase.getCardMap();
        HashMap<String, HashMap<String, Double>> exchangeRateMap = bankDataBase.getExchangeRateMap();

        // Checking if user exists
        User selectedUser = userMap.get(email);
        if (selectedUser == null) {
            return;
        }

        // Checking if card exists
        Card selectedCard = cardMap.get(cardNumber);
        if (selectedCard == null) {
            // Writing to output
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode menuNode = mapper.createObjectNode();

            menuNode.put("command", "payOnline");
            Output.cardNotFound(timestamp, menuNode);

            output.add(menuNode);
            return;
        }

        // If card is frozen
        if (selectedCard.getStatus().equals("frozen")) {
            Transaction transaction = new Transaction.Builder(1, timestamp, "The card is frozen").build();
            selectedUser.getTransactions().add(transaction);
            return;
        }

        // Finding the account with the selected card
        Account selectedAccount = selectedCard.getParentAccount();
        double decreaseAmount;

        // Determining if change of currency is needed
        String accountCurrency = selectedAccount.getCurrency();
        if (!accountCurrency.equals(currency)) {
            double rate = exchangeRateMap.get(currency).get(accountCurrency);
            decreaseAmount = rate * amount;
        } else {
            decreaseAmount = amount;
        }

        // If balance would go below zero
        if (decreaseAmount > selectedAccount.getBalance()) {
            Transaction transaction = new Transaction.Builder(1, timestamp, "Insufficient funds").build();
            selectedUser.getTransactions().add(transaction);
            return;
        }

        // Decreasing moneys
        String cardNumberBeforePay = selectedCard.getCardNumber();
        selectedCard.pay(decreaseAmount);
        selectedCard.checkCardNumber(cardNumberBeforePay, cardMap);

        Transaction transaction = new Transaction.Builder(4, timestamp, "Card payment")
                .putAmount(decreaseAmount)
                .putCommerciant(commerciant).build();

        selectedUser.getTransactions().add(transaction);
        selectedAccount.getAccountTransactions().add(transaction);
    }
}
