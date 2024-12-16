package org.poo.bank.commands;

import org.poo.bank.Account;
import org.poo.bank.Card;
import org.poo.bank.User;
import org.poo.utils.Utils;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class CreateCard {
    /**
     * Utility class requirement
     */
    private CreateCard() {
    }

    public static void execute(LinkedHashMap<String, User> userMap, HashMap<String, Account> accountMap,
                               String account, String email,
                               int timestamp) {
        User selectedUser = userMap.get(email);
        if (selectedUser == null) {
            return;
        }

        Account selectedAccount = accountMap.get(account);
        if (selectedAccount == null) {
            return;
        }

        Card addedCard = new Card(Utils.generateCardNumber());
        selectedAccount.getCards().add(addedCard);
    }
}
