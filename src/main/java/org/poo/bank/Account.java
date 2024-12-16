package org.poo.bank;

import lombok.Data;

import java.util.ArrayList;

@Data
public class Account {
    private String iban;
    private double balance;
    private String currency;
    private String accountType;
    private double minBalance;
    private ArrayList<Card> cards;

    public Account(String iban, String currency, String accountType) {
        this.iban = iban;
        this.currency = currency;
        this.accountType = accountType;
        cards = new ArrayList<>();
    }

    public void increaseBalance(double amount) {
        balance += amount;
    }

    public void decreaseBalance(double amount) {
        balance -= amount;
    }

}
