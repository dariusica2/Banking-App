package org.poo.bank.commands;

import org.poo.bank.BankDataBase;
import org.poo.bank.User;
import org.poo.bank.account.Account;

import java.util.HashMap;

public final class SetAlias {
    /**
     * Utility class requirement
     */
    private SetAlias() {
    }

    /**
     * Sets an alias to an existing IBAN for a specific user
     * @param bankDataBase database containing all information about users, accounts,
     *                     cards and exchange rates
     * @param email specific email associated to a single user
     * @param account specific IBAN associated to a single account
     * @param alias name associated to a single IBAN
     */
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
