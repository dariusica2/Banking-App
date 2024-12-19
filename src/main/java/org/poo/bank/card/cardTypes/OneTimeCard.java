package org.poo.bank.card.cardTypes;

import org.poo.bank.card.Card;
import org.poo.bank.card.CardInfo;
import org.poo.utils.Utils;

public final class OneTimeCard extends Card {
    public OneTimeCard(final CardInfo cardInfo) {
        super(cardInfo);
    }

    public void pay(final double amount) {
        getParentAccount().decreaseBalance(amount);
        resetCardNumber();
    }

    public void resetCardNumber() {
        // Generating new cardNumber
        String newCardNumber = Utils.generateCardNumber();
        setCardNumber(newCardNumber);
    }
}
