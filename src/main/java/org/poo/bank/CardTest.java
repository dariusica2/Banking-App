package org.poo.bank;

import lombok.Data;

@Data
public abstract class CardTest {
    private Account parentAccount;
    private String cardNumber;
    private String status;

    public CardTest(String cardNumber, Account parentAccount) {
        this.parentAccount = parentAccount;
        this.cardNumber = cardNumber;
        status = "active";
    }

    public abstract void pay();
}
