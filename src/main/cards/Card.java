package main.cards;

import java.util.Objects;

/*
* Класс карты, у которой внутри масть, номинал и количество очков за нее
* Единственная причина, по которой я сделал ей количество очков, это потому, что
* в случае с тузом очки могут меняться. Если бы не этот факт я бы пользовался только функцией которую
* написал в CardFace
* */
public class Card {
    private CardFace face;
    private CardSuit suit;
    private int points;

    public Card(CardFace face, CardSuit suit) {
        this.face = face;
        this.suit = suit;
        this.points = face.getPoints();
    }

    public Card(CardFace face, CardSuit suit, int points) {
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

    /*Авто сгенерированные функции*/
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card that = (Card) o;
        return points == that.points &&
                face == that.face &&
                suit == that.suit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(face, suit, points);
    }

    @Override
    public String toString() {
        String faceString = face.isPicture() ? String.valueOf(face.name().charAt(0)) : String.valueOf(face.getPoints());
        String suitString = suit.getUnicodeString();
        return faceString + suitString;
    }
}
