package org.poo.bank;

import lombok.Data;

@Data
public final class CommerciantInfo {
    String commerciant;
    double amount;

    public CommerciantInfo(final String commerciant, final double amount) {
        this.commerciant = commerciant;
        this.amount = amount;
    }

    public void increaseAmount(double addedAmount) {
        amount += addedAmount;
    }
}
