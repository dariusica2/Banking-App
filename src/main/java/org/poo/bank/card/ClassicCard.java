package org.poo.bank.card;

import org.poo.bank.Account;

import java.util.HashMap;

public class ClassicCard extends Card {
    public ClassicCard(String cardNumber, Account parentAccount) {
        super(parentAccount, cardNumber);
    }

    public void pay(double amount) {
        getParentAccount().decreaseBalance(amount);
    }
}
