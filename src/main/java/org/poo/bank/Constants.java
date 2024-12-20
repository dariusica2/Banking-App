package org.poo.bank;

public final class Constants {
    /**
     * Utility class requirement
     */
    private Constants() {
    }

    // Transaction types
    public static final int STANDARD = 1;
    public static final int CARD = 2;
    public static final int PAYMENT = 4;
    public static final int TRANSFER = 5;
    public static final int SPLIT = 6;
}
