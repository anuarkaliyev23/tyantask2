package main;

import main.cards.Card;
import main.cards.CardFace;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Dealer {
    protected static final int BLACKJACK_POINTS = 21;
    protected static final int ACE_BLACKJACK_CASE_POINTS = 1;

    protected List<Card> hand;

    public Dealer() {
        hand = new ArrayList<>();
    }

    public Dealer(Dealer other) {
        hand = new ArrayList<>(other.getHand());
    }

    public int calculatePoints() {
        int sum = 0;
        for (Card card : hand) {
            sum += card.getPoints();
        }
        return sum;
    }


    public void addCardToHand(Card card) {
        if (card.getFace() == CardFace.ACE) {
            if (calculatePoints() > BLACKJACK_POINTS) {
                card.setPoints(ACE_BLACKJACK_CASE_POINTS);
            }
        }
        hand.add(card);
    }

    public List<Card> getHand() {
        return hand;
    }

    public void setHand(List<Card> hand) {
        this.hand = new ArrayList<>(hand);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dealer dealer = (Dealer) o;
        return Objects.equals(hand, dealer.hand);
    }

    @Override
    public int hashCode() {

        return Objects.hash(hand);
    }
}
