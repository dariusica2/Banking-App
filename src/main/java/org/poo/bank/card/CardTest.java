package org.poo.bank.card;

import lombok.Data;
import org.poo.bank.Account;

@Data
public abstract class CardTest {
    private Account parentAccount;
    private String cardNumber;
    private String status;

    public CardTest(Account parentAccount, String cardNumber) {
        this.parentAccount = parentAccount;
        this.cardNumber = cardNumber;
        status = "active";
    }

    public abstract void pay();
}
