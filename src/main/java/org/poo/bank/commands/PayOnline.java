package org.poo.bank.commands;

import org.poo.bank.Account;
import org.poo.bank.BankDataBase;
import org.poo.bank.Card;
import org.poo.bank.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class PayOnline {
    /**
     * Utility class requirement
     */
    private PayOnline() {
    }

    public static void execute(BankDataBase bankDataBase,
                               String cardNumber, double amount, String currency,
                               int timestamp,
                               String description, String commerciant, String email) {
        LinkedHashMap<String, User> userMap = bankDataBase.getUserMap();
        HashMap<String, Account> accountMap = bankDataBase.getAccountMap();
        HashMap<String, Card> cardMap = bankDataBase.getCardMap();
        HashMap<String, HashMap<String, Double>> exchangeRateMap = bankDataBase.getExchangeRateMap();

        // Checking if account exists
        User selectedUser = userMap.get(email);
        if (selectedUser == null) {
            return;
        }

        // Checking if card exists
        Card selectedCard = cardMap.get(cardNumber);
        if (selectedCard == null) {
            return;
        }

        // Finding the account with the selected card
        Account selectedAccount = null;
        for (Map.Entry<String, Account> mapElement : accountMap.entrySet()) {
            selectedAccount = mapElement.getValue();
            ArrayList<Card> selectedCardList = selectedAccount.getCards();
            if (selectedCardList.contains(selectedCard))
                break;
        }

        // Determining if change of currency is needed
        if (selectedAccount == null) {
            return;
        }
        String accountCurrency = selectedAccount.getCurrency();
        if (accountCurrency.equals(currency)) {
            selectedAccount.decreaseBalance(amount);
        } else {
            double rate = exchangeRateMap.get(accountCurrency).get(currency);
            double convertedAmount = rate * amount;
            selectedAccount.decreaseBalance(convertedAmount);
        }
    }
}
