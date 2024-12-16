package org.poo.bank;

import com.fasterxml.jackson.databind.node.ArrayNode;

import lombok.Data;

import org.poo.bank.commands.*;
import org.poo.fileio.CommandInput;
import org.poo.fileio.ExchangeInput;
import org.poo.fileio.UserInput;

import java.util.HashMap;
import java.util.LinkedHashMap;

@Data
public class BankDataBase {
    private LinkedHashMap<String, User> userMap;
    private HashMap<String, Account> accountMap;
    private HashMap<String, Card> cardMap;
    private HashMap<String, HashMap<String, Double>> exchangeRateMap;

    public BankDataBase() {
        userMap = new LinkedHashMap<String, User>();
        accountMap = new HashMap<String, Account>();
        cardMap = new HashMap<String, Card>();
        exchangeRateMap = new HashMap<String, HashMap<String, Double>>();
    }

    public void addUsers(UserInput[] userInputs) {
        for (UserInput userInput : userInputs) {
            User user = new User(userInput);
            userMap.put(user.getEmail(), user);
        }
    }

    public void addExchangeRates(ExchangeInput[] exchangeRates) {
        for (ExchangeInput exchangeRate : exchangeRates) {
            String from = exchangeRate.getFrom();
            String to = exchangeRate.getTo();
            double rate = exchangeRate.getRate();

            exchangeRateMap.putIfAbsent(from, new HashMap<>());
            exchangeRateMap.putIfAbsent(to, new HashMap<>());

            exchangeRateMap.get(from).put(to, rate);
            exchangeRateMap.get(to).put(from, 1.0 / rate);
        }

        for (String middle : exchangeRateMap.keySet()) {
            for (String from : exchangeRateMap.keySet()) {
                for (String to : exchangeRateMap.keySet()) {
                    if (from.equals(to)
                            || !exchangeRateMap.get(from).containsKey(middle)
                            || !exchangeRateMap.get(middle).containsKey(to)) {
                        continue;
                    }
                    double intermediateRate = exchangeRateMap.get(from).get(middle) * exchangeRateMap.get(middle).get(to);
                    exchangeRateMap.get(from).put(to, intermediateRate);
                    exchangeRateMap.get(to).put(from, 1.0 / intermediateRate);
                }
            }
        }
    }

    public void interpretCommands(CommandInput[] commands, ArrayNode output) {
        for (CommandInput commandInput : commands) {
            String command = commandInput.getCommand();
            // addAccount
            String email = commandInput.getEmail();
            String currency = commandInput.getCurrency();
            String accountType = commandInput.getAccountType();
            // createCard
            String account = commandInput.getAccount();
            // addFunds
            double amount = commandInput.getAmount();
            // deleteCard
            String cardNumber = commandInput.getCardNumber();
            // payOnline
            String description = commandInput.getDescription();
            String commerciant = commandInput.getCommerciant();

            int timestamp = commandInput.getTimestamp();

//            private double minBalance;
//            private String target;
//            private int startTimestamp;
//            private int endTimestamp;
//            private String receiver;
//            private String alias;
//            private double interestRate;
//            private List<String> accounts;

            switch (command) {
                case "printUsers":
                    PrintUsers.execute(this, timestamp, output);
                    break;
                case "addAccount":
                    AddAccount.execute(this, email, currency, accountType, timestamp);
                    break;
                case "createCard":
                    CreateCard.execute(this, account, email, timestamp);
                    break;
                case "addFunds":
                    AddFunds.execute(this, account, amount, timestamp);
                    break;
                case "deleteAccount":
                    DeleteAccount.execute(this, email, account, timestamp, output);
                    break;
                case "createOneTimeCard":
                    CreateOneTimeCard.execute(this, account, email, timestamp);
                    break;
                case "deleteCard":
                    DeleteCard.execute(this, cardNumber, timestamp);
                    break;
                case "setMinimumBalance":
                    SetMinimumBalance.execute(this, amount, account, timestamp);
                    break;
                case "payOnline":
                    PayOnline.execute(this, cardNumber, amount, currency, timestamp, description, commerciant, email);
                    break;
            }
        }
    }
}
