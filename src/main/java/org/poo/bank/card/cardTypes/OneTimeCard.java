package org.poo.bank.card.cardTypes;

import org.poo.bank.card.Card;
import org.poo.bank.card.CardInfo;
import org.poo.utils.Utils;

public class OneTimeCard extends Card {
    public OneTimeCard(CardInfo cardInfo) {
        super(cardInfo);
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