package org.poo.bank.account.accountTypes;

import com.fasterxml.jackson.databind.node.ArrayNode;
import lombok.Data;
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
    }
}
