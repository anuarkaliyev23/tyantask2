package main;

public class PlayingCard {
    private CardFace face;
    private CardSuit suit;
    private int points;

    public PlayingCard(CardFace face, CardSuit suit) {
        this.face = face;
        this.suit = suit;
        this.points = face.getPoints();
    }

    public PlayingCard(CardFace face, CardSuit suit, int points) {
        this.face = face;
        this.suit = suit;
        this.points = points;
    }

    public CardFace getFace() {
        return face;
    }

    public void setFace(CardFace face) {
        this.face = face;
    }

    public CardSuit getSuit() {
        return suit;
    }

    public void setSuit(CardSuit suit) {
        this.suit = suit;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
