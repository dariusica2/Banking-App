package org.poo.bank.card.cardTypes;

import org.poo.bank.BankDataBase;
import org.poo.bank.card.Card;
import org.poo.bank.card.CardInfo;
import org.poo.bank.commands.CreateCard;
import org.poo.bank.commands.DeleteCard;
import org.poo.utils.Utils;

public final class OneTimeCard extends Card {
    public OneTimeCard(final CardInfo cardInfo) {
        super(cardInfo);
    }

    /**
     *
     */
    public void pay(final double amount, final BankDataBase bankDataBase, int timestamp) {
        getParentAccount().decreaseBalance(amount);
        DeleteCard.execute(bankDataBase, getCardNumber(), timestamp);
        CreateCard.execute(bankDataBase, getParentAccount().getIban(),
                getParentAccount().getParentUser().getEmail(),
                timestamp, "oneTime");
    }

    /**
     * Generates a new cardNumber (cut some corners here, I know it's supposed to delete
     * itself and create a new one)
     */
    public void resetCardNumber() {
        // Generating new cardNumber
        String newCardNumber = Utils.generateCardNumber();
        setCardNumber(newCardNumber);
    }
}
