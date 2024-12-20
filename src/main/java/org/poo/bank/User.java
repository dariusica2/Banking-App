package org.poo.bank;

import lombok.Data;
import org.poo.bank.account.Account;
import org.poo.bank.transactions.Transaction;
import org.poo.fileio.UserInput;

import java.util.ArrayList;
import java.util.HashMap;

@Data
public class User {
    private String firstName;
    private String lastName;
    private String email;
    private ArrayList<Account> accounts;
    private ArrayList<Transaction> transactions;
    private HashMap<String, String> aliasMap;

    public User(final UserInput userInput) {
        firstName = userInput.getFirstName();
        lastName = userInput.getLastName();
        email = userInput.getEmail();
        accounts = new ArrayList<Account>();
        transactions = new ArrayList<Transaction>();
        aliasMap = new HashMap<String, String>();
    }
}
