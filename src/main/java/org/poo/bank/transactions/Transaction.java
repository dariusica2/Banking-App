package org.poo.bank.transactions;

import lombok.Data;

import java.util.List;

@Data
public final class Transaction {
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
    private List<String> involvedAccounts;
    private String error;

    public final static class Builder {
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
        private List<String> involvedAccounts;
        private String error;

        public Builder(final int transactionType, final int timestamp, final String description) {
            this.transactionType = transactionType;
            this.timestamp = timestamp;
            this.description = description;
        }

        /**
         *
         */
        public Builder putCard(final String newCard) {
            this.card = newCard;
            return this;
        }

        /**
         *
         */
        public Builder putCardHolder(final String newCardHolder) {
            this.cardHolder = newCardHolder;
            return this;
        }

        /**
         *
         */
        public Builder putAccount(final String newAccount) {
            this.account = newAccount;
            return this;
        }

        /**
         *
         */
        public Builder putAmount(final double newAmount) {
            this.amount = newAmount;
            return this;
        }

        /**
         *
         */
        public Builder putCommerciant(final String newCommerciant) {
            this.commerciant = newCommerciant;
            return this;
        }

        /**
         *
         */
        public Builder putAmountCurrency(final String newAmountCurrency) {
            this.amountCurrency = newAmountCurrency;
            return this;
        }

        /**
         *
         */
        public Builder putSenderIBAN(final String newSenderIBAN) {
            this.senderIBAN = newSenderIBAN;
            return this;
        }

        /**
         *
         */
        public Builder putReceiverIBAN(final String newReceiverIBAN) {
            this.receiverIBAN = newReceiverIBAN;
            return this;
        }

        /**
         *
         */
        public Builder putTransferType(final String newTransferType) {
            this.transferType = newTransferType;
            return this;
        }

        /**
         *
         */
        public Builder putCurrency(final String newCurrency) {
            this.currency = newCurrency;
            return this;
        }

        /**
         *
         */
        public Builder putInvolvedAccounts(final List<String> newInvolvedAccounts) {
            this.involvedAccounts = newInvolvedAccounts;
            return this;
        }

        /**
         *
         */
        public Builder putError(final String newError) {
            this.error = newError;
            return this;
        }

        public Transaction build() {
            return new Transaction(this);
        }
    }

    private Transaction(final Builder builder) {
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
