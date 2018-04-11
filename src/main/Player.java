package main;

import main.cards.Card;
import main.cards.CardFace;
import main.exceptions.NotEnoughMoneyException;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private static final int BLACKJACK_POINTS = 21;
    private static final int ACE_BLACKJACK_CASE_POINTS = 1;

    private List<Card> hand;
    private int money;
    private List<Card> split;

    public Player(List<Card> hand, List<Card> split, int money) {
        this.hand = new ArrayList<>(hand);
        this.split = new ArrayList<>(split);
        this.money = money;
    }

    public Player(int money) {
        this.money = money;
        this.hand = new ArrayList<>();
        this.split = new ArrayList<>();
    }

    public List<Card> getHand() {
        return hand;
    }

    public void setHand(List<Card> hand) {
        this.hand = new ArrayList<>(hand);
    }

    public List<Card> getSplit() {
        return split;
    }

    public void setSplit(List<Card> split) {
        this.split = new ArrayList<>(split);
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int calculatePointsInSplit() {
        int sum = 0;
        for (Card card: split) {
            sum += card.getPoints();
        }
        return sum;
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

    public void addCardToSplit(Card card) {
        if (card.getFace() == CardFace.ACE) {
            if (calculatePointsInSplit() > BLACKJACK_POINTS) {
                card.setPoints(ACE_BLACKJACK_CASE_POINTS);
            }
        }
        split.add(card);
    }

    public int takeMoneyForBet(int betAmount) throws NotEnoughMoneyException {
        if (money - betAmount > 0) {
            throw new NotEnoughMoneyException();
        } else {
            money -= betAmount;
            return betAmount;
        }
    }



}
