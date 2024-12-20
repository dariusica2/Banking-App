package org.poo.bank.commands;

import org.poo.bank.BankDataBase;
import org.poo.bank.Constants;
import org.poo.bank.transactions.Transaction;
import org.poo.bank.User;
import org.poo.bank.account.Account;
import org.poo.bank.card.Card;

import java.util.HashMap;

public final class DeleteCard {

    /**
     * Utility class requirement
     */
    private DeleteCard() {
    }

    /**
     * Deletes an existing card from the database
     * @param bankDataBase database containing all information about users, accounts,
     *                     cards and exchange rates
     * @param cardNumber specific number associated to a single card
     */
    public static void execute(final BankDataBase bankDataBase,
                               final String cardNumber,
                               final int timestamp) {
        HashMap<String, Card> cardMap = bankDataBase.getCardMap();

        // Checking if card exists
        Card selectedCard = cardMap.get(cardNumber);
        if (selectedCard == null) {
            return;
        }

        // Removing from card HashMap
        cardMap.remove(cardNumber);

        // Removing from user's card list
        Account selectedAccount = selectedCard.getParentAccount();
        selectedAccount.getCards().remove(selectedCard);

        User selectedUser = selectedAccount.getParentUser();

        String userEmail = selectedUser.getEmail();
        String userAccount = selectedAccount.getIban();
        // Adding specific transaction
        Transaction transaction = new Transaction.Builder(Constants.CARD, timestamp,
                "The card has been destroyed")
                .putCard(cardNumber)
                .putCardHolder(userEmail)
                .putAccount(userAccount).build();
        selectedUser.getTransactions().add(transaction);
        selectedAccount.getAccountTransactions().add(transaction);
    }
}
