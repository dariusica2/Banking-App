package org.poo.bank.commands;

import org.poo.bank.*;
import org.poo.bank.card.Card;

import java.util.HashMap;

public class DeleteCard {

    /**
     * Utility class requirement
     */
    private DeleteCard() {
    }

    public static void execute(BankDataBase bankDataBase,
                               String cardNumber,
                               int timestamp) {
        HashMap<String, Card> cardMap = bankDataBase.getCardMap();

        // Checking if card exists
        Card selectedCard = cardMap.get(cardNumber);
        if (selectedCard == null) {
            return;
        }

        // Removing from card HashMap
        cardMap.remove(cardNumber);

        // Removing from user's card list
        Account parentAccount = selectedCard.getParentAccount();
        parentAccount.getCards().remove(selectedCard);

        User selectedUser = parentAccount.getParentUser();

        String userEmail = selectedUser.getEmail();
        String userAccount = parentAccount.getIban();
        // Adding specific transaction
        Transaction transaction = new Transaction.Builder(3, timestamp, "The card has been destroyed")
                .putCard(cardNumber)
                .putCardHolder(userEmail)
                .putAccount(userAccount).build();
        selectedUser.getTransactions().add(transaction);
    }
}
