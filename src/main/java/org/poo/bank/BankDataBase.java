package org.poo.bank;

import com.fasterxml.jackson.databind.node.ArrayNode;
import org.poo.bank.commands.AddAccount;
import org.poo.bank.commands.AddFunds;
import org.poo.bank.commands.CreateCard;
import org.poo.bank.commands.PrintUsers;
import org.poo.fileio.CommandInput;
import org.poo.fileio.UserInput;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class BankDataBase {
    private LinkedHashMap<String, User> userMap;
    private HashMap<String, Account> accountMap;

    public BankDataBase() {
        userMap = new LinkedHashMap<String, User>();
        accountMap = new HashMap<String, Account>();
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

            int timestamp = commandInput.getTimestamp();

//            private double minBalance;
//            private String target;
//            private String description;
//            private String cardNumber;
//            private String commerciant;
//            private int timestamp;
//            private int startTimestamp;
//            private int endTimestamp;
//            private String receiver;
//            private String alias;
//            private double interestRate;
//            private List<String> accounts;

            switch (command) {
                case "printUsers":
                    PrintUsers.execute(userMap, timestamp, output);
                    break;
                case "addAccount":
                    AddAccount.execute(userMap, accountMap, email, currency, accountType, timestamp);
                    break;
                case "createCard":
                    CreateCard.execute(userMap, accountMap, account, email, timestamp);
                    break;
                case "addFunds":
                    AddFunds.execute(accountMap, account, amount, timestamp);
                    break;
            }
        }
    }
}
