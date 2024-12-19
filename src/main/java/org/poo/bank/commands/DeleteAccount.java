package org.poo.bank.commands;

import com.fasterxml.jackson.databind.node.ArrayNode;
import org.poo.bank.*;
import org.poo.bank.card.Card;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class DeleteAccount {
    /**
     * Utility class requirement
     */
    private DeleteAccount() {
    }

    public static void execute(BankDataBase bankDataBase,
                               String email, String account,
                               int timestamp, ArrayNode output) {
        LinkedHashMap<String, User> userMap = bankDataBase.getUserMap();
        HashMap<String, Account> accountMap = bankDataBase.getAccountMap();
        HashMap<String, Card> cardMap = bankDataBase.getCardMap();

        // Checking if user exists
        User selectedUser = userMap.get(email);
        if (selectedUser == null) {
            Output.deleteAccountError(timestamp, output);
            return;
        }

        // Checking if account exists
        Account selectedAccount = accountMap.get(account);
        if (selectedAccount == null) {
            Output.deleteAccountError(timestamp, output);
            return;
        }

        // Checking requirement
        if (selectedAccount.getBalance() != 0) {
            Output.deleteAccountError(timestamp, output);
            return;
        }

        // Remove cards from HashMap
        ArrayList<Card> cards = selectedAccount.getCards();
        for (Card deletedCard : cards) {
            cardMap.remove(deletedCard.getCardNumber());
        }
        // Remove account from HashMap
        accountMap.remove(account);

        // Remove account from user's account list
        selectedUser.getAccounts().remove(selectedAccount);

        // Writing to output
        Output.deleteAccountSuccess(timestamp, output);
    }
}
