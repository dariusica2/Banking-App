package org.poo.bank;

import com.fasterxml.jackson.databind.node.ArrayNode;
import lombok.Data;
import org.poo.bank.account.Account;
import org.poo.bank.card.Card;
import org.poo.bank.commands.AddAccount;
import org.poo.bank.commands.AddFunds;
import org.poo.bank.commands.AddInterest;
import org.poo.bank.commands.ChangeInterestRate;
import org.poo.bank.commands.CheckCardStatus;
import org.poo.bank.commands.CreateCard;
import org.poo.bank.commands.DeleteAccount;
import org.poo.bank.commands.DeleteCard;
import org.poo.bank.commands.PayOnline;
import org.poo.bank.commands.PrintTransactions;
import org.poo.bank.commands.PrintUsers;
import org.poo.bank.commands.Report;
import org.poo.bank.commands.SendMoney;
import org.poo.bank.commands.SetAlias;
import org.poo.bank.commands.SetMinimumBalance;
import org.poo.bank.commands.SpendingsReport;
import org.poo.bank.commands.SplitPayment;
import org.poo.fileio.CommandInput;
import org.poo.fileio.ExchangeInput;
import org.poo.fileio.UserInput;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

@Data
public final class BankDataBase {
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

    /**
     * Adds all users given in the input file to the database
     */
    public void addUsers(final UserInput[] userInputs) {
        for (UserInput userInput : userInputs) {
            User user = new User(userInput);
            userMap.put(user.getEmail(), user);
        }
    }

    /**
     * Creates all possible exchange rates using an algorithm similar to Floyd-Warshall
     * Simulates nodes by using a HashMap which associates a String with another HashMap
     */
    public void addExchangeRates(final ExchangeInput[] exchangeRates) {
        // Going through already provided exchange rates
        for (ExchangeInput exchangeRate : exchangeRates) {
            String from = exchangeRate.getFrom();
            String to = exchangeRate.getTo();
            double rate = exchangeRate.getRate();

            exchangeRateMap.putIfAbsent(from, new HashMap<>());
            exchangeRateMap.putIfAbsent(to, new HashMap<>());

            exchangeRateMap.get(from).put(to, rate);
            exchangeRateMap.get(to).put(from, 1.0 / rate);
        }

        // Calculating intermediate exchange rates using an algorithm similar to Floyd-Warshall
        for (String middle : exchangeRateMap.keySet()) {
            for (String from : exchangeRateMap.keySet()) {
                for (String to : exchangeRateMap.keySet()) {
                    if (from.equals(to)
                            || !exchangeRateMap.get(from).containsKey(middle)
                            || !exchangeRateMap.get(middle).containsKey(to)) {
                        continue;
                    }
                    double intermediateRate = exchangeRateMap.get(from).get(middle)
                            * exchangeRateMap.get(middle).get(to);
                    exchangeRateMap.get(from).put(to, intermediateRate);
                    exchangeRateMap.get(to).put(from, 1.0 / intermediateRate);
                }
            }
        }
    }

    /**
     * Most important part of code, reads and interprets all commands given at input
     */
    public void interpretCommands(final CommandInput[] commands, final ArrayNode output) {
        for (CommandInput commandInput : commands) {
            String command = commandInput.getCommand();

            String email = commandInput.getEmail();
            String currency = commandInput.getCurrency();
            String accountType = commandInput.getAccountType();
            String account = commandInput.getAccount();
            double amount = commandInput.getAmount();
            String cardNumber = commandInput.getCardNumber();
            String description = commandInput.getDescription();
            String commerciant = commandInput.getCommerciant();
            String receiver = commandInput.getReceiver();
            List<String> accounts = commandInput.getAccounts();
            int startTimestamp = commandInput.getStartTimestamp();
            int endTimestamp = commandInput.getEndTimestamp();
            double interestRate = commandInput.getInterestRate();
            String alias = commandInput.getAlias();

            int timestamp = commandInput.getTimestamp();

            switch (command) {
                case "printUsers":
                    PrintUsers.execute(this,
                            timestamp, output);
                    break;
                case "addAccount":
                    AddAccount.execute(this, email, currency, accountType,
                            timestamp, interestRate);
                    break;
                case "createCard":
                    CreateCard.execute(this, account, email,
                            timestamp, "classic");
                    break;
                case "createOneTimeCard":
                    CreateCard.execute(this, account, email,
                            timestamp, "oneTime");
                    break;
                case "addFunds":
                    AddFunds.execute(this, account, amount,
                            timestamp);
                    break;
                case "deleteAccount":
                    DeleteAccount.execute(this, email, account,
                            timestamp, output);
                    break;
                case "deleteCard":
                    DeleteCard.execute(this, cardNumber,
                            timestamp);
                    break;
                case "setMinimumBalance":
                    SetMinimumBalance.execute(this, amount, account,
                            timestamp);
                    break;
                case "payOnline":
                    PayOnline.execute(this, cardNumber, amount, currency,
                            timestamp, description, commerciant, email, output);
                    break;
                case "sendMoney":
                    SendMoney.execute(this, account, amount, receiver,
                            timestamp, description);
                    break;
                case "printTransactions":
                    PrintTransactions.execute(this, email,
                            timestamp, output);
                    break;
                case "checkCardStatus":
                    CheckCardStatus.execute(this, cardNumber,
                            timestamp, output);
                    break;
                case "splitPayment":
                    SplitPayment.execute(this, accounts, amount, currency,
                            timestamp);
                    break;
                case "report":
                    Report.execute(this, startTimestamp, endTimestamp, account,
                            timestamp, output);
                    break;
                case "spendingsReport":
                    SpendingsReport.execute(this, startTimestamp, endTimestamp, account,
                            timestamp, output);
                    break;
                case "addInterest":
                    AddInterest.execute(this, account,
                            timestamp, output);
                    break;
                case "changeInterestRate":
                    ChangeInterestRate.execute(this, account, interestRate,
                            timestamp, output);
                    break;
                case "setAlias":
                    SetAlias.execute(this, email, alias, account);
                    break;
                default:
                    throw new IllegalArgumentException("Unknown command type");
            }
        }
    }
}
