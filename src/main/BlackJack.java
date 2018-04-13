package main;

import main.cards.CardDeck;
import main.exceptions.ImpossibleSplitException;
import main.exceptions.NoSplitException;
import main.exceptions.NotEnoughMoneyException;
import main.exceptions.PlayerHaveNoPlacedBetException;

import java.util.Objects;


public class BlackJack {
    private int INITIAL_GIVEOUT_CARDS_COUNT = 2;

    private Dealer dealer;
    private Player player;
    private CardDeck deck;
    private boolean split;
    private int currentBet;
    private int firstBet;

    public BlackJack(Dealer dealer, Player player) {
        this.dealer = new Dealer(dealer);
        this.player = new Player(player);
        this.deck = new CardDeck();
        split = false;
        currentBet = 0;
        firstBet = 0;
    }

    public BlackJack(Player player) {
        this(new Dealer(), player);
    }

    public BlackJack(int playerMoney) {
        this(new Player(playerMoney));
    }

    public Dealer getDealer() {
        return dealer;
    }

    public void setDealer(Dealer dealer) {
        this.dealer = new Dealer(dealer);
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = new Player(player);
    }

    public CardDeck getDeck() {
        return deck;
    }

    public void setDeck(CardDeck deck) {
        this.deck = new CardDeck(deck);
    }

    public boolean isSplit() {
        return split;
    }

    public int getCurrentBet() {
        return currentBet;
    }

    public int getFirstBet() {
        return firstBet;
    }

    private void checkCardAdding(boolean toHand) {
        if (!toHand && !split) throw new NoSplitException();
    }

    public void placeBet(int amount) throws NotEnoughMoneyException {
        if (firstBet == 0) {
            firstBet = player.takeMoneyForBet(amount);
            currentBet += firstBet;
        } else {
            currentBet += player.takeMoneyForBet(amount);
        }
    }

    public void doublePlayerBet(boolean toHand) throws PlayerHaveNoPlacedBetException, NotEnoughMoneyException {
        checkCardAdding(toHand);
        if (firstBet == 0) throw new PlayerHaveNoPlacedBetException();
        currentBet += player.takeMoneyForBet(firstBet * 2);
        if (toHand) {
            player.addCardToHand(deck.popCard());
        } else player.addCardToSplit(deck.popCard());
    }


    public void giveOutCards() {
        for (int i = 0; i < INITIAL_GIVEOUT_CARDS_COUNT; i++) {
            player.addCardToHand(deck.popCard());
            dealer.addCardToHand(deck.popCard());
        }
    }

    public void takeCardsFromAll() {
        deck.addCards(dealer.getHand());
        deck.addCards(player.getAllCards());

        dealer.loseCards();
        player.loseCards();

        deck.shuffle();
    }

    private void addCardToPlayerHand() {
        player.addCardToHand(deck.popCard());
    }

    private void addCardToPlayerSplit() {
        player.addCardToSplit(deck.popCard());
    }

    public void addCardToPlayer(boolean toHand) {
        checkCardAdding(toHand);
        if (toHand) {
            addCardToPlayerHand();
        } else addCardToPlayerSplit();
    }

    public void addCardToDealer() {
        dealer.addCardToHand(deck.popCard());
    }

    public void doSplit() throws ImpossibleSplitException {
        player.doSplit();
        split = true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BlackJack blackJack = (BlackJack) o;
        return split == blackJack.split &&
                currentBet == blackJack.currentBet &&
                firstBet == blackJack.firstBet &&
                Objects.equals(dealer, blackJack.dealer) &&
                Objects.equals(player, blackJack.player) &&
                Objects.equals(deck, blackJack.deck);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dealer, player, deck, split, currentBet, firstBet);
    }

    @Override
    public String toString() {
        return "BlackJack{" +
                "\ndealer=" + dealer +
                "\nplayer=" + player +
                "\ncurrentBet=" + currentBet +
                "\nfirstBet=" + firstBet +
                '}';
    }
}
