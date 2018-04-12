package main;

import main.cards.Card;
import main.cards.CardFace;
import main.exceptions.DealerReachedLimitException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Dealer {
    protected static final int BLACKJACK_POINTS = 21;
    protected static final int ACE_BLACKJACK_CASE_POINTS = 1;
    private static final int DEFAULT_LIMIT = 17;

    protected List<Card> hand;
    private int limit;

    public Dealer() {
        hand = new ArrayList<>();
        limit = DEFAULT_LIMIT;
    }

    public Dealer(Dealer other) {
        hand = new ArrayList<>(other.getHand());
        limit = other.getLimit();
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int calculatePoints() {
        int sum = 0;
        for (Card card : hand) {
            sum += card.getPoints();
        }
        return sum;
    }


    public void addCardToHand(Card card) throws DealerReachedLimitException {
        if (reachedLimit()) throw new DealerReachedLimitException();
        if (card.getFace() == CardFace.ACE) {
            if (calculatePoints() > BLACKJACK_POINTS) {
                card.setPoints(ACE_BLACKJACK_CASE_POINTS);
            }
        }
        hand.add(card);
    }

    public boolean reachedLimit() {
        return calculatePoints() < limit;
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
