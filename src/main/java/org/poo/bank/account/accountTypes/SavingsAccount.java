package org.poo.bank.account.accountTypes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Data;
import org.poo.bank.transactions.Transaction;
import org.poo.bank.account.Account;
import org.poo.bank.account.AccountInfo;

@Data
public final class SavingsAccount extends Account {
    private double interestRate;

    public SavingsAccount(final AccountInfo accountInfo) {
        super(accountInfo);
        interestRate = accountInfo.getInterestRate();
    }

    /**
     *
     */
    public void addInterest(final int timestamp, final ArrayNode output) {
        increaseBalance(interestRate * getBalance());
    }

    /**
     *
     */
    public void changeInterestRate(final double newInterestRate,
                                   final int timestamp, final ArrayNode output) {
        interestRate = newInterestRate;

        Transaction transaction = new Transaction.Builder(1, timestamp,
                "Interest rate of the account changed to " + newInterestRate).build();
        getParentUser().getTransactions().add(transaction);
        getAccountTransactions().add(transaction);
    }

    public void spendingsReport(final int startTimestamp, final int endTimestamp,
                                final int timestamp, final ArrayNode output) {
        ObjectMapper mapper = new ObjectMapper();

        ObjectNode menuNode = mapper.createObjectNode();
        menuNode.put("command", "spendingsReport");

        ObjectNode outputNode = mapper.createObjectNode();
        outputNode.put("error", "This kind of report is not supported for a saving account");
        menuNode.set("output", outputNode);

        menuNode.put("timestamp", timestamp);
        output.add(menuNode);
    }
}
