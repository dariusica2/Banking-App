package org.poo.bank.card;

import lombok.Data;
import org.poo.bank.account.Account;

import java.util.HashMap;

@Data
public abstract class Card {
    private Account parentAccount;
    private String cardNumber;
    private String status;

    public Card(CardInfo cardInfo) {
        this.parentAccount = cardInfo.getParentAccount();
        this.cardNumber = cardInfo.getCardNumber();
        status = "active";
    }

    public abstract void pay(double amount);

    public void checkCardNumber(String cardNumberBeforePay, HashMap<String, Card> cardMap) {
        if (!cardNumber.equals(cardNumberBeforePay)) {
            cardMap.remove(cardNumberBeforePay);
            cardMap.put(cardNumber, this);
        }
    }
}
