package org.poo.bank.card;

import lombok.Data;
import org.poo.bank.Account;

@Data
public class CardInfo {
    private Account parentAccount;
    private String cardNumber;
    private String type;

    public CardInfo(Account parentAccount, String cardNumber, String type) {
        this.parentAccount = parentAccount;
        this.cardNumber = cardNumber;
        this.type = type;
    }
}
