package main;


import main.cards.Card;
import main.cards.CardDeck;

import java.util.ArrayList;

public class BlackJack {
    private int INITIAL_GIVEOUT_CARDS_COUNT = 2;

    private Dealer dealer;
    private Player player;
    private CardDeck deck;

    public BlackJack(Dealer dealer, Player player) {
        this.dealer = new Dealer(dealer);
        this.player = new Player(player);
        this.deck = new CardDeck();
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

    public void giveOutCards() {
        for (int i = 0; i < INITIAL_GIVEOUT_CARDS_COUNT; i++) {
            player.addCardToHand(deck.popCard());
            dealer.addCardToHand(deck.popCard());
        }
    }

    public void takeCardsFromPlayers() {
        deck.addCards(dealer.getHand());
        deck.addCards(player.getAllCards());

        dealer.loseCards();
        player.loseCards();

        deck.shuffle();
    }


}
