package org.poo.bank.card;

public class CardFactory {
    public static Card createCard(CardInfo cardInfo) {
        return switch (cardInfo.getType()) {
            case "oneTime" -> new OneTimeCard(cardInfo.getCardNumber(), cardInfo.getParentAccount());
            case "classic" -> new ClassicCard(cardInfo.getCardNumber(), cardInfo.getParentAccount());
            default -> throw new IllegalArgumentException("Unknown card type: " + cardInfo.getType());
        };
    }
}
