package org.poo.bank.card.cardTypes;

import org.poo.bank.card.Card;
import org.poo.bank.card.CardInfo;

public final class ClassicCard extends Card {
    public ClassicCard(final CardInfo cardInfo) {
        super(cardInfo);
    }

    /**
     *
     */
    public void pay(final double amount) {
        getParentAccount().decreaseBalance(amount);
    }
}
