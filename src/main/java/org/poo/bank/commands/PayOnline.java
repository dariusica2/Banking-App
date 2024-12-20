package org.poo.bank.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.bank.BankDataBase;
import org.poo.bank.Output;
import org.poo.bank.transactions.Transaction;
import org.poo.bank.User;
import org.poo.bank.account.Account;
import org.poo.bank.card.Card;

import java.util.HashMap;
import java.util.LinkedHashMap;

public final class PayOnline {
    /**
     * Utility class requirement
     */
    private PayOnline() {
    }

    /**
     * Converts a specified amount of money (if needed) and subtracts it from the account balance
     * @param bankDataBase database containing all information about users, accounts,
     *                     cards and exchange rates
     * @param email specific email associated to a single user
     * @param currency given currency of account
     * @param amount amount of money that will be subtracted from the account after conversion
     * @param cardNumber specific number associated to a single card
     * @param description description
     * @param commerciant commerciant
     */
    public static void execute(final BankDataBase bankDataBase,
                               final String cardNumber, final double amount, final String currency,
                               final int timestamp,
                               final String description, final String commerciant,
                               final String email, final ArrayNode output) {
        LinkedHashMap<String, User> userMap = bankDataBase.getUserMap();
        HashMap<String, Card> cardMap = bankDataBase.getCardMap();
        HashMap<String, HashMap<String, Double>> exchangeRateMap
                = bankDataBase.getExchangeRateMap();

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
            Output.descriptionNode("Card not found", timestamp, menuNode);

            output.add(menuNode);
            return;
        }

        // If card is frozen
        if (selectedCard.getStatus().equals("frozen")) {
            Transaction transaction = new Transaction.Builder(1, timestamp,
                    "The card is frozen").build();
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
            Transaction transaction = new Transaction.Builder(1, timestamp,
                    "Insufficient funds").build();
            selectedUser.getTransactions().add(transaction);
            selectedAccount.getAccountTransactions().add(transaction);
            return;
        }

        Transaction transaction = new Transaction.Builder(4, timestamp, "Card payment")
                .putAmount(decreaseAmount)
                .putCommerciant(commerciant).build();

        selectedUser.getTransactions().add(transaction);
        selectedAccount.getAccountTransactions().add(transaction);

        // Decreasing moneys
        selectedCard.pay(decreaseAmount, bankDataBase, timestamp);
    }
}
