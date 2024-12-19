package org.poo.bank.commands;

import org.poo.bank.*;
import org.poo.bank.account.Account;

import java.util.HashMap;

public final class SetAlias {
    /**
     * Utility class requirement
     */
    private SetAlias() {
    }

    public static void execute(final BankDataBase bankDataBase,
                               final String email, final String alias, final String account) {
        HashMap<String, User> userMap = bankDataBase.getUserMap();
        HashMap<String, Account> accountMap = bankDataBase.getAccountMap();

        User selectedUser = userMap.get(email);
        if (selectedUser == null) {
            return;
        }

        Account selectedAccount = accountMap.get(alias);
        if (selectedAccount == null) {
            return;
        }

        HashMap<String, String> aliasMap = selectedUser.getAliasMap();
        aliasMap.put(alias, account);
    }
}
