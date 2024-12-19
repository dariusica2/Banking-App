package org.poo.bank.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.bank.account.Account;
import org.poo.bank.BankDataBase;
import org.poo.bank.User;
import org.poo.bank.card.Card;

import java.util.LinkedHashMap;
import java.util.Map;

public class PrintUsers {
    /**
     * Utility class requirement
     */
    private PrintUsers() {
    }

    public static void execute(BankDataBase bankDataBase, int timestamp, ArrayNode output) {
        LinkedHashMap<String, User> userMap = bankDataBase.getUserMap();

        ObjectMapper mapper = new ObjectMapper();

        ObjectNode menuNode = mapper.createObjectNode();
        menuNode.put("command", "printUsers");

        ArrayNode outputNode = mapper.createArrayNode();

        for (Map.Entry<String, User> mapElement : userMap.entrySet()) {
            User user = mapElement.getValue();
            ObjectNode userNode = mapper.createObjectNode();
            userNode.put("firstName", user.getFirstName());
            userNode.put("lastName", user.getLastName());
            userNode.put("email", user.getEmail());

            ArrayNode accountsNode = mapper.createArrayNode();
            for (Account account : user.getAccounts()) {
                ObjectNode accountNode = mapper.createObjectNode();
                accountNode.put("IBAN", account.getIban());
                accountNode.put("balance", account.getBalance());
                accountNode.put("currency", account.getCurrency());
                accountNode.put("type", account.getAccountType());

                ArrayNode cardsNode = mapper.createArrayNode();
                for (Card card : account.getCards()) {
                    ObjectNode cardNode = mapper.createObjectNode();
                    cardNode.put("cardNumber", card.getCardNumber());
                    cardNode.put("status", card.getStatus());
                    cardsNode.add(cardNode);
                }

                accountNode.set("cards", cardsNode);
                accountsNode.add(accountNode);
            }

            userNode.set("accounts", accountsNode);
            outputNode.add(userNode);
        }

        menuNode.set("output", outputNode);

        menuNode.put("timestamp", timestamp);

        output.add(menuNode);
    }
}
