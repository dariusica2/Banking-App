package org.poo.bank.card;

import lombok.Data;
import org.poo.bank.account.Account;

@Data
public class CardInfo {
    private Account parentAccount;
    private String cardNumber;
    private String type;

    public CardInfo(final Account parentAccount, final String cardNumber, final String type) {
        this.parentAccount = parentAccount;
        this.cardNumber = cardNumber;
        this.type = type;
    }
}
