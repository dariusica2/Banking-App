package org.poo.bank.card;

import org.poo.bank.account.Account;
import org.poo.utils.Utils;

public class OneTimeCard extends Card {
    public OneTimeCard(String cardNumber, Account parentAccount) {
        super(parentAccount, cardNumber);
    }

    public void pay(double amount) {
        getParentAccount().decreaseBalance(amount);
        resetCardNumber();
    }

    public void resetCardNumber() {
        // Generating new cardNumber
        String newCardNumber = Utils.generateCardNumber();
        setCardNumber(newCardNumber);
    }
}