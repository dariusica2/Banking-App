package org.poo.bank;

import lombok.Data;

import java.util.ArrayList;

@Data
public class Transaction {
    // Mandatory fields
    private int transactionType;
    private int timestamp;
    private String description;
    // Fields for createCard / createOneTimeCard / deleteCard
    private String card;
    private String cardHolder;
    private String account;
    // Fields for payOnline
    private double amount;
    private String commerciant;
    // Fields for sendMoney
    private String amountCurrency;
    private String senderIBAN;
    private String receiverIBAN;
    private String transferType;
    // Fields for splitPayment
    private String currency;
    private ArrayList<String> involvedAccounts;
    private String error;

    public static class Builder {
        // Mandatory fields
        private int transactionType;
        private int timestamp;
        private String description;
        // Fields for createCard / createOneTimeCard / deleteCard
        private String card;
        private String cardHolder;
        private String account;
        // Fields for payOnline
        private double amount;
        private String commerciant;
        // Fields for sendMoney
        private String amountCurrency;
        private String senderIBAN;
        private String receiverIBAN;
        private String transferType;
        // Fields for splitPayment
        private String currency;
        private ArrayList<String> involvedAccounts;
        private String error;

        public Builder(int transactionType, int timestamp, String description) {
            this.transactionType = transactionType;
            this.timestamp = timestamp;
            this.description = description;
        }

        public Builder putCard(String card) {
            this.card = card;
            return this;
        }

        public Builder putCardHolder(String cardHolder) {
            this.cardHolder = cardHolder;
            return this;
        }

        public Builder putAccount(String account) {
            this.account = account;
            return this;
        }

        public Builder putAmount(double amount) {
            this.amount = amount;
            return this;
        }

        public Builder putCommerciant(String commerciant) {
            this.commerciant = commerciant;
            return this;
        }

        public Builder putAmountCurrency(String amountCurrency) {
            this.amountCurrency = amountCurrency;
            return this;
        }

        public Builder putSenderIBAN(String senderIBAN) {
            this.senderIBAN = senderIBAN;
            return this;
        }

        public Builder putReceiverIBAN(String receiverIBAN) {
            this.receiverIBAN = receiverIBAN;
            return this;
        }

        public Builder putTransferType(String transferType) {
            this.transferType = transferType;
            return this;
        }

        public Builder putCurrency(String currency) {
            this.currency = currency;
            return this;
        }

        public Builder putInvolvedAccounts(ArrayList<String> involvedAccounts) {
            this.involvedAccounts = involvedAccounts;
            return this;
        }

        public Builder putError(String error) {
            this.error = error;
            return this;
        }

        public Transaction build() {
            return new Transaction(this);
        }
    }

    private Transaction(Builder builder) {
        transactionType = builder.transactionType;
        timestamp = builder.timestamp;
        description = builder.description;
        card = builder.card;
        cardHolder = builder.cardHolder;
        account = builder.account;
        amount = builder.amount;
        commerciant = builder.commerciant;
        amountCurrency = builder.amountCurrency;
        senderIBAN = builder.senderIBAN;
        receiverIBAN = builder.receiverIBAN;
        transferType = builder.transferType;
        currency = builder.currency;
        involvedAccounts = builder.involvedAccounts;
        error = builder.error;
    }
}
