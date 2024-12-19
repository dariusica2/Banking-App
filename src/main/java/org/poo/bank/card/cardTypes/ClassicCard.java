package org.poo.bank.card.cardTypes;

import org.poo.bank.card.Card;
import org.poo.bank.card.CardInfo;

public class ClassicCard extends Card {
    public ClassicCard(CardInfo cardInfo) {
        super(cardInfo);
    }

    public void pay(double amount) {
        getParentAccount().decreaseBalance(amount);
    }
}
