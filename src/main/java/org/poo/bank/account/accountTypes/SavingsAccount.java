package org.poo.bank.account.accountTypes;

import com.fasterxml.jackson.databind.node.ArrayNode;
import org.poo.bank.account.Account;
import org.poo.bank.account.AccountInfo;

public class SavingsAccount extends Account {
    double interestRate;

    public SavingsAccount(AccountInfo accountInfo) {
        super(accountInfo);
        interestRate = accountInfo.getInterestRate();
    }

    public void addInterest(int timestamp, ArrayNode output) {
        increaseBalance(interestRate * getBalance());
    }

    public void changeInterestRate(double newInterestRate, int timestamp, ArrayNode output) {
        interestRate = newInterestRate;
    }
}
