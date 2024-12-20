package org.poo.bank.commands;

import org.poo.bank.BankDataBase;
import org.poo.bank.Transaction;
import org.poo.bank.User;
import org.poo.bank.account.Account;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class SplitPayment {
    /**
     * Utility class requirement
     */
    private SplitPayment() {
    }

    /**
     * Splits the payment between a finite number of people
     * @param bankDataBase database containing all information about users, accounts,
     *                     cards and exchange rates
     * @param currency given currency of account
     * @param amount amount to be split
     * @param accounts list of accounts which split the payment
     */
    public static void execute(final BankDataBase bankDataBase,
                               final List<String> accounts, final double amount,
                               final String currency,
                               final int timestamp) {
        HashMap<String, Account> accountMap = bankDataBase.getAccountMap();
        HashMap<String, HashMap<String, Double>> exchangeRateMap
                = bankDataBase.getExchangeRateMap();

        // Checking if all accounts exist
        for (String account : accounts) {
            if (!accountMap.containsKey(account)) {
                return;
            }
        }

        double splitAmount = amount / (double) accounts.size();

        ArrayList<Double> convertedAmounts = new ArrayList<Double>();

        // Checking if all accounts have the minimum amount to pay
        for (int i = accounts.size() - 1; i >= 0; i--) {
            String account = accounts.get(i);
            Account selectedAccount = accountMap.get(account);

            // splitAmount will be in the currency of the payment
            // splitAmount needs to be converted to the currency of the account
            double convertedAmount;
            if (selectedAccount.getCurrency().equals(currency)) {
                convertedAmount = splitAmount;
            } else {
                convertedAmount = splitAmount * exchangeRateMap
                        .get(currency).get(selectedAccount.getCurrency());
            }

            if (selectedAccount.getBalance() <= convertedAmount) {
                Transaction transaction = new Transaction.Builder(6, timestamp,
                        "Split payment of " + String.format("%.2f", amount) + " " + currency)
                        .putError("Account " + account
                                + " has insufficient funds for a split payment.")
                        .putCurrency(currency)
                        .putAmount(splitAmount)
                        .putInvolvedAccounts(accounts).build();

                for (String involvedAccount : accounts) {
                    accountMap.get(involvedAccount).getAccountTransactions().add(transaction);
                    User parentUser = accountMap.get(involvedAccount).getParentUser();
                    parentUser.getTransactions().add(transaction);
                }
                return;
            }
            convertedAmounts.addFirst(convertedAmount);
        }

        Transaction transaction = new Transaction.Builder(6, timestamp,
                "Split payment of " + String.format("%.2f", amount) + " " + currency)
                .putCurrency(currency)
                .putAmount(splitAmount)
                .putInvolvedAccounts(accounts).build();

        for (String involvedAccount : accounts) {
            Account selectedAccount = accountMap.get(involvedAccount);
            selectedAccount.decreaseBalance(convertedAmounts.removeFirst());
            User parentUser = selectedAccount.getParentUser();
            parentUser.getTransactions().add(transaction);
            selectedAccount.getAccountTransactions().add(transaction);
        }
    }
}
