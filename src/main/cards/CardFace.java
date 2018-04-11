package main.cards;

import main.exceptions.BlackJackUncheckedException;

public enum CardFace {
    ACE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING;

    private static int ACE_POINTS = 11;
    private static int TWO_POINTS = 2;
    private static int THREE_POINTS = 3;
    private static int FOUR_POINTS = 4;
    private static int FIVE_POINTS = 5;
    private static int SIX_POINTS = 6;
    private static int SEVEN_POINTS = 7;
    private static int EIGHT_POINTS = 8;
    private static int NINE_POINTS = 9;
    private static int TEN_POINTS = 10;
    private static int JACK_POINTS = 10;
    private static int QUEEN_POINTS = 10;
    private static int KING_POINTS = 10;

    public int getPoints() {
        switch (this) {
            case ACE: return ACE_POINTS;
            case TWO: return TWO_POINTS;
            case THREE: return THREE_POINTS;
            case FOUR: return FOUR_POINTS;
            case FIVE: return FIVE_POINTS;
            case SIX: return SIX_POINTS;
            case SEVEN: return SEVEN_POINTS;
            case EIGHT: return EIGHT_POINTS;
            case NINE: return NINE_POINTS;
            case TEN: return TEN_POINTS;
            case JACK: return JACK_POINTS;
            case QUEEN: return QUEEN_POINTS;
            case KING: return KING_POINTS;
            default: throw new BlackJackUncheckedException("Unknown card");
        }
    }
}
