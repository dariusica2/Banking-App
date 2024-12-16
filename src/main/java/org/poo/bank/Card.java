package org.poo.bank;

import lombok.Data;

@Data
public class Card {
    private String cardNumber;
    private String status;

    public Card(String cardNumber) {
        this.cardNumber = cardNumber;
        setStatus("active");
    }
}
