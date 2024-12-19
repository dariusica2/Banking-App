package org.poo.bank;

import lombok.Data;

@Data
public final class CommerciantInfo {
    private String commerciant;
    private double amount;

    public CommerciantInfo(final String commerciant, final double amount) {
        this.commerciant = commerciant;
        this.amount = amount;
    }

    /**
     *
     */
    public void increaseAmount(final double addedAmount) {
        amount += addedAmount;
    }
}
