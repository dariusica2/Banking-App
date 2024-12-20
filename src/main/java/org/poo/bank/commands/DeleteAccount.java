package org.poo.bank.commands;

import com.fasterxml.jackson.databind.node.ArrayNode;
import org.poo.bank.BankDataBase;
import org.poo.bank.Output;
import org.poo.bank.Transaction;
import org.poo.bank.User;
import org.poo.bank.account.Account;
import org.poo.bank.card.Card;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public final class DeleteAccount {
    /**
     * Utility class requirement
     */
    private DeleteAccount() {
    }

    /**
     * Deletes an account from the database
     * @param bankDataBase database containing all information about users, accounts,
     *                     cards and exchange rates
     * @param email specific email associated to a single user
     * @param account specific IBAN associated to a single account
     */
    public static void execute(final BankDataBase bankDataBase,
                               final String email, final String account,
                               final int timestamp, final ArrayNode output) {
        LinkedHashMap<String, User> userMap = bankDataBase.getUserMap();
        HashMap<String, Account> accountMap = bankDataBase.getAccountMap();
        HashMap<String, Card> cardMap = bankDataBase.getCardMap();

        // Checking if user exists
        User selectedUser = userMap.get(email);
        if (selectedUser == null) {
            Output.deleteAccountError(timestamp, output);
            return;
        }

        // Checking if account exists
        Account selectedAccount = accountMap.get(account);
        if (selectedAccount == null) {
            Output.deleteAccountError(timestamp, output);
            return;
        }

        // Checking requirement
        if (selectedAccount.getBalance() != 0) {
            Output.deleteAccountError(timestamp, output);

            Transaction transaction = new Transaction.Builder(1, timestamp,
                    "Account couldn't be deleted - there are funds remaining").build();
            selectedUser.getTransactions().add(transaction);
            selectedAccount.getAccountTransactions().add(transaction);
            return;
        }

        // Remove cards from HashMap
        ArrayList<Card> cards = selectedAccount.getCards();
        for (Card deletedCard : cards) {
            cardMap.remove(deletedCard.getCardNumber());
        }
        // Remove account from HashMap
        accountMap.remove(account);

        // Remove account from user's account list
        selectedUser.getAccounts().remove(selectedAccount);

        // Writing to output
        Output.deleteAccountSuccess(timestamp, output);
    }
}
