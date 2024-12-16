package org.poo.bank.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.bank.Account;
import org.poo.bank.User;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class DeleteAccount {
    /**
     * Utility class requirement
     */
    private DeleteAccount() {
    }

    public static void execute(LinkedHashMap<String, User> userMap, HashMap<String, Account> accountMap,
                               String email, String account,
                               int timestamp, ArrayNode output) {
        User selectedUser = userMap.get(email);
        if (selectedUser == null) {
            return;
        }

        Account selectedAccount = accountMap.get(account);
        if (selectedAccount == null) {
            return;
        }

        if (selectedAccount.getBalance() > 0) {
            return;
        }

        selectedUser.getAccounts().remove(selectedAccount);

        accountMap.remove(account);

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
