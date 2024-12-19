package org.poo.bank.account;

import com.fasterxml.jackson.databind.node.ArrayNode;
import lombok.Data;
import org.poo.bank.Transaction;
import org.poo.bank.User;
import org.poo.bank.card.Card;

import java.util.ArrayList;

@Data
public abstract class Account {
    private User parentUser;
    private String iban;
    private double balance;
    private String currency;
    private String accountType;
    private double minBalance;
    private ArrayList<Card> cards;
    private ArrayList<Transaction> accountTransactions;

    public Account(final AccountInfo accountInfo) {
        this.parentUser = accountInfo.getParentUser();
        this.iban = accountInfo.getIban();
        this.currency = accountInfo.getCurrency();
        this.accountType = accountInfo.getAccountType();
        cards = new ArrayList<Card>();
        accountTransactions = new ArrayList<Transaction>();
    }

    /**
     * Changes based on card type
     */
    public abstract void addInterest(int timestamp, ArrayNode output);

    /**
     * Changes based on card type
     */
    public abstract void changeInterestRate(double newInterestRate,
                                            int timestamp, ArrayNode output);

    /**
     *
     */
    public final void increaseBalance(final double amount) {
        balance += amount;
    }

    /**
     *
     */
    public final void decreaseBalance(final double amount) {
        balance -= amount;
    }

}
