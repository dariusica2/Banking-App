package org.poo.bank.card;

import org.poo.bank.Account;

public class ClassicCard extends CardTest {
    public ClassicCard(String cardNumber, Account parentAccount) {
        super(parentAccount, cardNumber);
    }

    public void pay() {

    }
}
