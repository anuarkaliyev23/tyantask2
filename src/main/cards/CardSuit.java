package main.cards;

import main.exceptions.BlackJackUncheckedException;

public enum CardSuit {
    HEARTS, DIAMONDS, CLUBS, SPADES;

    private static String HEARTS_UNICODE = "\u2661";
    private static String DIAMONDS_UNICODE = "\u2662";
    private static String SPADES_UNICODE = "\u2660";
    private static String CLUBS_UNICODE = "\u2663";

    public String getUnicodeString() {
        switch (this) {
            case HEARTS: return HEARTS_UNICODE;
            case DIAMONDS: return DIAMONDS_UNICODE;
            case SPADES: return SPADES_UNICODE;
            case CLUBS: return CLUBS_UNICODE;
            default: throw new BlackJackUncheckedException("Unknown card suit");
        }
    }


}
