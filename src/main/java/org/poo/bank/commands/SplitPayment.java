package org.poo.bank.commands;

import org.poo.bank.*;
import org.poo.bank.account.Account;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SplitPayment {
    /**
     * Utility class requirement
     */
    private SplitPayment() {
    }

    public static void execute(BankDataBase bankDataBase,
                               List<String> accounts, double amount, String currency,
                               int timestamp) {
        HashMap<String, Account> accountMap = bankDataBase.getAccountMap();
        HashMap<String, HashMap<String, Double>> exchangeRateMap = bankDataBase.getExchangeRateMap();

        // Checking if all accounts exist
        for (String account : accounts) {
            if (!accountMap.containsKey(account)) {
                return;
            }
        }

        double splitAmount = amount / accounts.size();
//        System.out.println("accounts size " + accounts.size() + " splitAmount " + splitAmount);

        ArrayList<Double> convertedAmounts = new ArrayList<Double>();

        // Checking if all accounts have the minimum amount to pay
        for (String account : accounts) {
            Account selectedAccount = accountMap.get(account);
            // splitAmount will be in the currency of the payment
            // splitAmount needs to be converted to the currency of the account
            double convertedAmount;
            if (selectedAccount.getCurrency().equals(currency)) {
                convertedAmount = splitAmount;
            } else {
                convertedAmount = splitAmount * exchangeRateMap.get(currency).get(selectedAccount.getCurrency());
            }
            if (selectedAccount.getBalance() < convertedAmount) {
                return;
            }
            convertedAmounts.add(convertedAmount);
        }

        Transaction transaction = new Transaction.Builder(6, timestamp, "Split payment of " + String.format("%.2f", amount) + " " + currency).putCurrency(currency).putAmount(splitAmount).putInvolvedAccounts(accounts).build();

        for (String account : accounts) {
            Account selectedAccount = accountMap.get(account);
            selectedAccount.decreaseBalance(convertedAmounts.removeFirst());
            User parentUser = selectedAccount.getParentUser();
            parentUser.getTransactions().add(transaction);
            selectedAccount.getAccountTransactions().add(transaction);
        }
    }
}
