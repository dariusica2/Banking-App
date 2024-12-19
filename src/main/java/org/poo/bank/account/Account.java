package org.poo.bank.account;

import lombok.Data;
import org.poo.bank.Transaction;
import org.poo.bank.User;
import org.poo.bank.card.Card;

import java.util.ArrayList;

@Data
public class Account {
    private User parentUser;
    private String iban;
    private double balance;
    private String currency;
    private String accountType;
    private double minBalance;
    private ArrayList<Card> cards;
    private ArrayList<Transaction> accountTransactions;

    public Account(String iban, String currency, String accountType, User parentUser) {
        this.parentUser = parentUser;
        this.iban = iban;
        this.currency = currency;
        this.accountType = accountType;
        cards = new ArrayList<>();
        accountTransactions = new ArrayList<>();
    }

    public void increaseBalance(double amount) {
        balance += amount;
    }

    public void decreaseBalance(double amount) {
        balance -= amount;
    }

}
