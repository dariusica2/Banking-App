package org.poo.bank.card;

import org.poo.bank.card.cardTypes.ClassicCard;
import org.poo.bank.card.cardTypes.OneTimeCard;

public final class CardFactory {
    /**
     * Utility class requirement
     */
    private CardFactory() {
    }

    public static Card createCard(final CardInfo cardInfo) {
        return switch (cardInfo.getType()) {
            case "oneTime" -> new OneTimeCard(cardInfo);
            case "classic" -> new ClassicCard(cardInfo);
            default -> throw new IllegalArgumentException("Unknown card type: "
                    + cardInfo.getType());
        };
    }
}
