package org.poo.bank.card;

import lombok.Data;
import org.poo.bank.Account;

@Data
public class Card {
    private Account parentAccount;
    private String cardNumber;
    private String status;

    public Card(String cardNumber, Account parentAccount) {
        this.parentAccount = parentAccount;
        this.cardNumber = cardNumber;
        status = "active";
    }
}
