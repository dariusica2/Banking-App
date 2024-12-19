package org.poo.bank.account;

import org.poo.bank.account.accountTypes.ClassicAccount;
import org.poo.bank.account.accountTypes.SavingsAccount;

public class AccountFactory {
    public static Account createAccount(AccountInfo accountInfo) {
        return switch (accountInfo.getAccountType()) {
            case "savings" -> new SavingsAccount(accountInfo);
            case "classic" -> new ClassicAccount(accountInfo);
            default -> throw new IllegalArgumentException("Uhh... unknown account type: " + accountInfo.getAccountType());
        };
    }
}
