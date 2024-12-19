package org.poo.bank.account;

import lombok.Data;
import org.poo.bank.User;

@Data
public class AccountInfo {
    private String iban;
    private String currency;
    private String accountType;
    private double interestRate;
    private User parentUser;

    public AccountInfo(final String iban, final String currency, final String accountType,
                       final double interestRate, final User parentUser) {
        this.iban = iban;
        this.currency = currency;
        this.accountType = accountType;
        this.interestRate = interestRate;
        this.parentUser = parentUser;
    }
}
