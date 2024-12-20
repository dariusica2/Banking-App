package org.poo.bank;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.bank.account.Account;
import org.poo.bank.card.Card;

import java.util.LinkedHashMap;
import java.util.Map;

public final class Output {
    /**
     * Utility class requirement
     */
    private Output() {
    }

    public static void printUsers(LinkedHashMap<String, User> userMap, ObjectMapper mapper,
                                  ArrayNode outputNode) {
        for (Map.Entry<String, User> mapElement : userMap.entrySet()) {
            User user = mapElement.getValue();
            ObjectNode userNode = mapper.createObjectNode();
            userNode.put("firstName", user.getFirstName());
            userNode.put("lastName", user.getLastName());
            userNode.put("email", user.getEmail());

            ArrayNode accountsNode = mapper.createArrayNode();
            printAccounts(user, mapper, accountsNode);

            userNode.set("accounts", accountsNode);
            outputNode.add(userNode);
        }
    }

    public static void printAccounts(User user, ObjectMapper mapper,
                                     ArrayNode accountsNode) {
        for (Account account : user.getAccounts()) {
            ObjectNode accountNode = mapper.createObjectNode();
            accountNode.put("IBAN", account.getIban());
            accountNode.put("balance", account.getBalance());
            accountNode.put("currency", account.getCurrency());
            accountNode.put("type", account.getAccountType());

            ArrayNode cardsNode = mapper.createArrayNode();
            printCards(account, mapper, cardsNode);

            accountNode.set("cards", cardsNode);
            accountsNode.add(accountNode);
        }

    }

    public static void printCards(Account account, ObjectMapper mapper,
                                  ArrayNode cardsNode) {
        for (Card card : account.getCards()) {
            ObjectNode cardNode = mapper.createObjectNode();
            cardNode.put("cardNumber", card.getCardNumber());
            cardNode.put("status", card.getStatus());
            cardsNode.add(cardNode);
        }
    }

    /**
     *
     */
    public static void descriptionNode(String description,
                                    final int timestamp, final ObjectNode menuNode) {
        ObjectMapper mapper = new ObjectMapper();

        ObjectNode outputNode = mapper.createObjectNode();
        outputNode.put("description", description);
        outputNode.put("timestamp", timestamp);
        menuNode.set("output", outputNode);
        menuNode.put("timestamp", timestamp);
    }

    /**
     *
     */
    public static void deleteAccountError(final int timestamp, final ArrayNode output) {
        ObjectMapper mapper = new ObjectMapper();

        ObjectNode menuNode = mapper.createObjectNode();
        menuNode.put("command", "deleteAccount");

        ObjectNode outputNode = mapper.createObjectNode();
        outputNode.put("error", "Account couldn't be deleted - see org.poo.transactions for details");
        outputNode.put("timestamp", timestamp);
        menuNode.set("output", outputNode);
        menuNode.put("timestamp", timestamp);

        output.add(menuNode);
    }

    /**
     *
     */
    public static void deleteAccountSuccess(final int timestamp, final ArrayNode output) {
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
