package org.poo.bank.commands;

import org.poo.bank.BankDataBase;
import org.poo.bank.Constants;
import org.poo.bank.transactions.Transaction;
import org.poo.bank.User;
import org.poo.bank.account.Account;
import org.poo.bank.card.Card;
import org.poo.bank.card.CardFactory;
import org.poo.bank.card.CardInfo;
import org.poo.utils.Utils;

import java.util.HashMap;
import java.util.LinkedHashMap;

public final class CreateCard {
    /**
     * Utility class requirement
     */
    private CreateCard() {
    }

    /**
     * Creates a card and adds it to the database
     * @param bankDataBase database containing all information about users, accounts,
     *                     cards and exchange rates
     * @param email specific email associated to a single user
     * @param account specific IBAN associated to a single account
     */
    public static void execute(final BankDataBase bankDataBase,
                               final String account, final String email,
                               final int timestamp, final String type) {
        LinkedHashMap<String, User> userMap = bankDataBase.getUserMap();
        HashMap<String, Account> accountMap = bankDataBase.getAccountMap();
        HashMap<String, Card> cardMap = bankDataBase.getCardMap();

        // Checking if user exists
        User selectedUser = userMap.get(email);
        if (selectedUser == null) {
            return;
        }

        // Checking if account exists
        Account selectedAccount = accountMap.get(account);
        if (selectedAccount == null) {
            return;
        }

        // Creating new card
        String cardNumber = Utils.generateCardNumber();
        CardInfo cardInfo = new CardInfo(selectedAccount, cardNumber, type);
        Card createdCard = CardFactory.createCard(cardInfo);

        // Mapping card to its card Number
        cardMap.put(cardNumber, createdCard);
        // Adding created card in the user's card list
        selectedAccount.getCards().add(createdCard);

        String userEmail = selectedUser.getEmail();
        // Adding specific transaction
        Transaction transaction = new Transaction.Builder(Constants.CARD, timestamp,
                "New card created")
                .putCard(cardNumber)
                .putCardHolder(userEmail)
                .putAccount(account).build();
        selectedUser.getTransactions().add(transaction);
        selectedAccount.getAccountTransactions().add(transaction);
    }
}
