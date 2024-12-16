package org.poo.bank;

import com.fasterxml.jackson.databind.node.ArrayNode;

import lombok.Data;

import org.poo.bank.commands.*;
import org.poo.fileio.CommandInput;
import org.poo.fileio.UserInput;

import java.util.HashMap;
import java.util.LinkedHashMap;

@Data
public class BankDataBase {
    private LinkedHashMap<String, User> userMap;
    private HashMap<String, Account> accountMap;
    private HashMap<String, Card> cardMap;

    public BankDataBase() {
        userMap = new LinkedHashMap<String, User>();
        accountMap = new HashMap<String, Account>();
        cardMap = new HashMap<String, Card>();
    }

    public void addUsers(UserInput[] userInputs) {
        for (UserInput userInput : userInputs) {
            User user = new User(userInput);
            userMap.put(user.getEmail(), user);
        }
    }

    public void interpretCommands(CommandInput[] commands, ArrayNode output) {
        for (CommandInput commandInput : commands) {
            String command = commandInput.getCommand();

            String email = commandInput.getEmail();
            String currency = commandInput.getCurrency();
            String accountType = commandInput.getAccountType();

            String account = commandInput.getAccount();

            double amount = commandInput.getAmount();

            String cardNumber = commandInput.getCardNumber();

            int timestamp = commandInput.getTimestamp();

//            private double minBalance;
//            private String target;
//            private String description;
//            private String commerciant;
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
            }
        }
    }
}
