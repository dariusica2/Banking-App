package org.poo.bank;

import lombok.Data;
import org.poo.fileio.UserInput;

import java.util.ArrayList;

@Data
public class User {
    private String firstName;
    private String lastName;
    private String email;
    private ArrayList<Account> accounts;

    public User(UserInput userInput) {
        firstName = userInput.getFirstName();
        lastName = userInput.getLastName();
        email = userInput.getEmail();
        accounts = new ArrayList<>();
    }
}
