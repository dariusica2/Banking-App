package org.poo.bank.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.bank.BankDataBase;
import org.poo.bank.Output;
import org.poo.bank.User;
import org.poo.bank.account.Account;
import org.poo.bank.card.Card;

import java.util.LinkedHashMap;
import java.util.Map;

public final class PrintUsers {
    /**
     * Utility class requirement
     */
    private PrintUsers() {
    }

    /**
     * Prints all users in the database
     * @param bankDataBase database containing all information about users, accounts,
     *                     cards and exchange rates
     */
    public static void execute(final BankDataBase bankDataBase,
                               final int timestamp, final ArrayNode output) {
        LinkedHashMap<String, User> userMap = bankDataBase.getUserMap();

        ObjectMapper mapper = new ObjectMapper();

        ObjectNode menuNode = mapper.createObjectNode();
        menuNode.put("command", "printUsers");

        ArrayNode outputNode = mapper.createArrayNode();

        Output.printUsers(userMap, mapper, outputNode);

        menuNode.set("output", outputNode);

        menuNode.put("timestamp", timestamp);

        output.add(menuNode);
    }
}
