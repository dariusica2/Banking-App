package org.poo.bank;

import lombok.Data;

@Data
public class CommerciantInfo {
    String commerciant;
    double amount;

    public CommerciantInfo(String commerciant, double amount) {
        this.commerciant = commerciant;
        this.amount = amount;
    }

    public void increaseAmount(double addedAmount) {
        amount += addedAmount;
    }
}
