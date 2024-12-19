package org.poo.bank.card;

import lombok.Data;
import org.poo.bank.Account;

import java.util.HashMap;

@Data
public abstract class Card {
    private Account parentAccount;
    private String cardNumber;
    private String status;

    public Card(Account parentAccount, String cardNumber) {
        this.parentAccount = parentAccount;
        this.cardNumber = cardNumber;
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
