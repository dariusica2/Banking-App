package org.poo.bank;

import lombok.Data;

@Data
public class Card {
    private Account parentAccount;
    private String cardNumber;
    private String status;

    public Card(String cardNumber, Account parentAccount) {
        this.parentAccount = parentAccount;
        this.cardNumber = cardNumber;
        setStatus("active");
    }
}
