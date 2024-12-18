package org.poo.bank.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.bank.*;

import java.util.HashMap;

public class CheckCardStatus {
    /**
     * Utility class requirement
     */
    private CheckCardStatus() {
    }

    public static void execute(BankDataBase bankDataBase,
                               String cardNumber,
                               int timestamp, ArrayNode output) {
        HashMap<String, Card> cardMap = bankDataBase.getCardMap();

        // Checking if card exists
        Card selectedCard = cardMap.get(cardNumber);
        if (selectedCard == null) {
            Output.cardNotFound(timestamp, output);
            return;
        }

        if (selectedCard.getStatus().equals("frozen")) {
            return;
        }

        Account parentAccount = selectedCard.getParentAccount();
        User parentUser = parentAccount.getParentUser();
        if (parentAccount.getBalance() <= parentAccount.getMinBalance()) {
            selectedCard.setStatus("frozen");
            Transaction transaction = new Transaction.Builder(1, timestamp, "You have reached the minimum amount of funds, the card will be frozen").build();
            parentUser.getTransactions().add(transaction);
        }
    }
}
