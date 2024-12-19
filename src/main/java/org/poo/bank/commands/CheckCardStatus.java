package org.poo.bank.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.bank.*;
import org.poo.bank.account.Account;
import org.poo.bank.card.Card;

import java.util.HashMap;

public final class CheckCardStatus {
    /**
     * Utility class requirement
     */
    private CheckCardStatus() {
    }

    public static void execute(final BankDataBase bankDataBase,
                               final String cardNumber,
                               final int timestamp, final ArrayNode output) {
        HashMap<String, Card> cardMap = bankDataBase.getCardMap();

        // Checking if card exists
        Card selectedCard = cardMap.get(cardNumber);
        if (selectedCard == null) {
            // Writing to output
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode menuNode = mapper.createObjectNode();

            menuNode.put("command", "checkCardStatus");
            Output.cardNotFound(timestamp, menuNode);

            output.add(menuNode);
            return;
        }

        // Nothing happens if card is already frozen
        if (selectedCard.getStatus().equals("frozen")) {
            return;
        }

        Account parentAccount = selectedCard.getParentAccount();
        User parentUser = parentAccount.getParentUser();
        if (parentAccount.getBalance() <= parentAccount.getMinBalance()) {
            selectedCard.setStatus("frozen");
            Transaction transaction = new Transaction.Builder(1, timestamp,
                    "You have reached the minimum amount of funds, "
                            + "the card will be frozen").build();
            parentUser.getTransactions().add(transaction);
            parentAccount.getAccountTransactions().add(transaction);
        }
    }
}
