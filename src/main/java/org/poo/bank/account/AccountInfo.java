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

    public AccountInfo(String iban, String currency, String accountType, double interestRate, User parentUser) {
        this.iban = iban;
        this.currency = currency;
        this.accountType = accountType;
        this.interestRate = interestRate;
        this.parentUser = parentUser;
    }
}
