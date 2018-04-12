package main;


import main.cards.CardDeck;

public class BlackJack {

    private Dealer dealer;
    private Player player;
    private CardDeck deck;
    private int dealerLimit;

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

    }


}
