package org.poo.bank.commands;

import org.poo.bank.*;
import org.poo.utils.Utils;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class CreateCard {
    /**
     * Utility class requirement
     */
    private CreateCard() {
    }

    public static void execute(BankDataBase bankDataBase,
                               String account, String email,
                               int timestamp) {
        LinkedHashMap<String, User> userMap = bankDataBase.getUserMap();
        HashMap<String, Account> accountMap = bankDataBase.getAccountMap();
        HashMap<String, Card> cardMap = bankDataBase.getCardMap();

        // Checking if user exists
        User selectedUser = userMap.get(email);
        if (selectedUser == null) {
            return;
        }

        // Checking if account exists
        Account selectedAccount = accountMap.get(account);
        if (selectedAccount == null) {
            return;
        }

        // Creating new card
        String cardNumber = Utils.generateCardNumber();
        Card createdCard = new Card(cardNumber, selectedAccount);

        // Mapping card to its card Number
        cardMap.put(cardNumber, createdCard);
        // Adding created card in the user's card list
        selectedAccount.getCards().add(createdCard);

        String userEmail = selectedUser.getEmail();
        // Adding specific transaction
        Transaction transaction = new Transaction.Builder(2, timestamp, "New card created")
                .putCard(cardNumber)
                .putCardHolder(userEmail)
                .putAccount(account).build();
        selectedUser.getTransactions().add(transaction);
    }
}
