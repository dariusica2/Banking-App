package org.poo.bank.card.cardTypes;

import org.poo.bank.BankDataBase;
import org.poo.bank.card.Card;
import org.poo.bank.card.CardInfo;

public final class ClassicCard extends Card {
    public ClassicCard(final CardInfo cardInfo) {
        super(cardInfo);
    }

    /**
     *
     */
    public void pay(final double amount, final BankDataBase bankDataBase, int timestamp) {
        getParentAccount().decreaseBalance(amount);
    }
}
