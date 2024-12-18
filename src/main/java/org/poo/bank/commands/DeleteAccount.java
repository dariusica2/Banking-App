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
            ObjectMapper mapper = new ObjectMapper();

            ObjectNode menuNode = mapper.createObjectNode();
            menuNode.put("command", "deleteAccount");

            ObjectNode outputNode = mapper.createObjectNode();
            outputNode.put("error", "Account couldn't be deleted - see org.poo.transactions for details");
            outputNode.put("timestamp", timestamp);
            menuNode.set("output", outputNode);
            menuNode.put("timestamp", timestamp);

            output.add(menuNode);
            return;
        }

        // Checking if account exists
        Account selectedAccount = accountMap.get(account);
        if (selectedAccount == null) {
            ObjectMapper mapper = new ObjectMapper();

            ObjectNode menuNode = mapper.createObjectNode();
            menuNode.put("command", "deleteAccount");

            ObjectNode outputNode = mapper.createObjectNode();
            outputNode.put("error", "Account couldn't be deleted - see org.poo.transactions for details");
            outputNode.put("timestamp", timestamp);
            menuNode.set("output", outputNode);
            menuNode.put("timestamp", timestamp);

            output.add(menuNode);
            return;
        }

        // Checking requirement
        if (selectedAccount.getBalance() != 0) {
            ObjectMapper mapper = new ObjectMapper();

            ObjectNode menuNode = mapper.createObjectNode();
            menuNode.put("command", "deleteAccount");

            ObjectNode outputNode = mapper.createObjectNode();
            outputNode.put("error", "Account couldn't be deleted - see org.poo.transactions for details");
            outputNode.put("timestamp", timestamp);
            menuNode.set("output", outputNode);
            menuNode.put("timestamp", timestamp);

            output.add(menuNode);
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
        ObjectMapper mapper = new ObjectMapper();

        ObjectNode menuNode = mapper.createObjectNode();
        menuNode.put("command", "deleteAccount");

        ObjectNode outputNode = mapper.createObjectNode();
        outputNode.put("success", "Account deleted");
        outputNode.put("timestamp", timestamp);
        menuNode.set("output", outputNode);
        menuNode.put("timestamp", timestamp);

        output.add(menuNode);
    }
}
