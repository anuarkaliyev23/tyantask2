package main;

import main.cards.Card;
import main.cards.CardFace;
import main.exceptions.ImpossibleSplitException;
import main.exceptions.NotEnoughMoneyException;

import java.util.ArrayList;
import java.util.List;

public class Player extends Dealer {

    private static final int HAND_SIZE_FOR_SPLIT = 2;

    private int money;
    private List<Card> split;

    public Player(int money) {
        super();
        this.money = money;
        this.split = new ArrayList<>();
    }

    public Player(Player other) {
        super();
        this.money = other.getMoney();
        this.split = new ArrayList<>(other.getSplit());
    }


    @Override
    public void addCardToHand(Card card) {
        if (card.getFace() == CardFace.ACE) {
            if (calculatePoints() > BLACKJACK_POINTS) {
                card.setPoints(ACE_BLACKJACK_CASE_POINTS);
            }
        }
        hand.add(card);
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

    public boolean isSplit() {
        return !this.split.isEmpty();
    }

    public void addCardToSplit(Card card) {
        if (card.getFace() == CardFace.ACE) {
            if (calculatePointsInSplit() > BLACKJACK_POINTS) {
                card.setPoints(ACE_BLACKJACK_CASE_POINTS);
            }
        }
        split.add(card);
    }

    public void doSplit() throws ImpossibleSplitException {
        if (hand.size() == HAND_SIZE_FOR_SPLIT) {
            if (hand.get(0).getPoints() == hand.get(1).getPoints()) {
                split.add(hand.get(1));
                hand.remove(1);
            } else throw new ImpossibleSplitException();
        } else throw new ImpossibleSplitException();
    }

    public int takeMoneyForBet(int betAmount) throws NotEnoughMoneyException {
        if (money - betAmount > 0) {
            throw new NotEnoughMoneyException();
        } else {
            money -= betAmount;
            return betAmount;
        }
    }

    @Override
    public void loseCards() {
        super.loseCards();
        this.split = new ArrayList<>();
    }

    public List<Card> getAllCards() {
        List<Card> cards = new ArrayList<>();
        cards.addAll(this.hand);
        cards.addAll(this.split);
        return cards;
    }

    @Override
    public String toString() {
        return "Player{" +
                "money=" + money +
                ", split=" + split +
                ", hand=" + hand +
                '}';
    }
}
