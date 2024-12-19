package org.poo.bank;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class Output {
    /**
     * Utility class requirement
     */
    private Output() {
    }

    public static void printUsers() {

    }

    public static void printAccounts() {

    }

    public static void printCards() {

    }

    public static void cardNotFound(int timestamp, ObjectNode menuNode) {
        ObjectMapper mapper = new ObjectMapper();

        ObjectNode outputNode = mapper.createObjectNode();
        outputNode.put("description", "Card not found");
        outputNode.put("timestamp", timestamp);
        menuNode.set("output", outputNode);
        menuNode.put("timestamp", timestamp);
    }

    public static void deleteAccountError(int timestamp, ArrayNode output) {
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

    public static void deleteAccountSuccess(int timestamp, ArrayNode output) {
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
