package org.poo.bank.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.bank.Account;
import org.poo.bank.BankDataBase;
import org.poo.bank.Card;
import org.poo.bank.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class DeleteCard {

    /**
     * Utility class requirement
     */
    private DeleteCard() {
    }

    public static void execute(BankDataBase bankDataBase,
                               String cardNumber,
                               int timestamp) {
        HashMap<String, Account> accountMap = bankDataBase.getAccountMap();
        HashMap<String, Card> cardMap = bankDataBase.getCardMap();

        // Checking if card exists
        Card selectedCard = cardMap.get(cardNumber);
        if (selectedCard == null) {
            return;
        }

        // Removing from card HashMap
        cardMap.remove(cardNumber);

        // Removing from user's card list
        for (Map.Entry<String, Account> mapElement : accountMap.entrySet()) {
            Account selectedAccount = mapElement.getValue();
            ArrayList<Card> selectedCardList = selectedAccount.getCards();
            if (selectedCardList.remove(selectedCard))
                break;
        }
    }
}
