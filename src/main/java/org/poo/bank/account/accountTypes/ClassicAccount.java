package org.poo.bank.account.accountTypes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.bank.account.Account;
import org.poo.bank.account.AccountInfo;

public class ClassicAccount extends Account {
    public ClassicAccount(AccountInfo accountInfo) {
        super(accountInfo);
    }

    public void addInterest(int timestamp, ArrayNode output) {
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

    public void changeInterestRate(double newInterestRate, int timestamp, ArrayNode output) {
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
