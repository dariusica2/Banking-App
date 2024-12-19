package org.poo.bank.card;

import org.poo.bank.Account;

public class OneTimeCard extends CardTest {
    public OneTimeCard(String cardNumber, Account parentAccount) {
        super(parentAccount, cardNumber);
    }

    public void pay() {

    }

    public void reset() {

    }
}