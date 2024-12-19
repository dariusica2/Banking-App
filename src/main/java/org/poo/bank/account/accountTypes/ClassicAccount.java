package org.poo.bank.account.accountTypes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.bank.account.Account;
import org.poo.bank.account.AccountInfo;

public final class ClassicAccount extends Account {
    public ClassicAccount(final AccountInfo accountInfo) {
        super(accountInfo);
    }

    /**
     * Unsupported command for classic account
     */
    public void addInterest(final int timestamp, final ArrayNode output) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode menuNode = mapper.createObjectNode();

        menuNode.put("command", "addInterest");

        ObjectNode outputNode = mapper.createObjectNode();
        outputNode.put("description", "This is not a savings account");
        outputNode.put("timestamp", timestamp);
        menuNode.set("output", outputNode);
        menuNode.put("timestamp", timestamp);

        output.add(menuNode);
    }

    /**
     * Unsupported command for classic account
     */
    public void changeInterestRate(final double newInterestRate,
                                   final int timestamp, final ArrayNode output) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode menuNode = mapper.createObjectNode();

        menuNode.put("command", "changeInterestRate");

        ObjectNode outputNode = mapper.createObjectNode();
        outputNode.put("description", "This is not a savings account");
        outputNode.put("timestamp", timestamp);
        menuNode.set("output", outputNode);
        menuNode.put("timestamp", timestamp);

        output.add(menuNode);
    }
}
