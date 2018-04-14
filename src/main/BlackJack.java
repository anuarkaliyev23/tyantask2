package main;

import main.cards.CardDeck;
import main.exceptions.ImpossibleSplitException;
import main.exceptions.NoSplitException;
import main.exceptions.NotEnoughMoneyException;
import main.exceptions.PlayerHaveNoPlacedBetException;

import java.util.Objects;


public class BlackJack {
    private static final int INITIAL_GIVEOUT_CARDS_COUNT = 2;
    private static final int BLACKJACK = 21;

    private static final int BLACKJACK_MULTIPLIER = 3;
    private static final int WINNING_MULTIPLIER = 2;

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
        addCardToPlayer(toHand);
    }


    public void giveOutInitialCards() {
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

    public void endRound() {
        if (!split) {
            int playerPoints = this.player.calculatePoints();
            int dealerPoints = this.dealer.calculatePoints();
            endRound(identifyEnding(playerPoints, dealerPoints));
        } else {
            int playerHand = this.player.calculatePoints();
            int playerSplit = this.player.calculatePointsInSplit();
            int dealerPoints = this.dealer.calculatePoints();
            endRoundSplit(identifyEnding(playerHand, dealerPoints), identifyEnding(playerSplit, dealerPoints));
        }

        takeCardsFromAll();
        cancelAllBets();
        this.split = false;
    }

    private void cancelAllBets() {
        this.currentBet = 0;
        this.firstBet = 0;
    }

    private void endRound(BlackJackEnding ending) {
        switch (ending) {
            case DRAW: endRoundDraw();
            case PLAYER_WON: endRoundPlayerWon(false);
            case PLAYER_BLACKJACK: endRoundPlayerWon(true);
//            case DEALER_WON: endRoundDealerWon();
        }
    }


    private void endRoundSplit(BlackJackEnding playerHand, BlackJackEnding playerSplit) {
        if (playerHand == BlackJackEnding.DRAW && playerSplit == BlackJackEnding.DRAW) endRoundDraw();

        if (playerHand == BlackJackEnding.PLAYER_WON) {
            switch (playerSplit) {
                case DRAW: player.addMoney((int) (currentBet * 1.5));
                case DEALER_WON: player.addMoney(currentBet);
                case PLAYER_BLACKJACK: player.addMoney((int) (currentBet + currentBet * 1.5));
                case PLAYER_WON: player.addMoney(currentBet * 2 * WINNING_MULTIPLIER);
            }
        } else if (playerHand == BlackJackEnding.PLAYER_BLACKJACK) {
            switch (playerSplit) {
                case PLAYER_BLACKJACK: player.addMoney(currentBet * BLACKJACK_MULTIPLIER / 2 + currentBet * BLACKJACK_MULTIPLIER / 2);
                case DRAW: player.addMoney(currentBet / 2 + currentBet * BLACKJACK_MULTIPLIER / 2);
                case DEALER_WON: player.addMoney(currentBet * BLACKJACK_MULTIPLIER / 2);
                case PLAYER_WON: player.addMoney(currentBet * BLACKJACK_MULTIPLIER/ 2 + currentBet);
            }
        } else if (playerHand == BlackJackEnding.DEALER_WON) {
            switch (playerSplit) {
                case PLAYER_BLACKJACK: player.addMoney(currentBet * BLACKJACK_MULTIPLIER / 2);
                case DRAW: player.addMoney(currentBet / 2);
//                case DEALER_WON: player.addMoney();
                case PLAYER_WON: player.addMoney(currentBet);
            }
        } else if (playerHand == BlackJackEnding.DRAW) {
            switch (playerSplit) {
                case DRAW: player.addMoney(currentBet);
                case PLAYER_BLACKJACK: player.addMoney(currentBet / 2 + currentBet * BLACKJACK_MULTIPLIER/ 2);
                case DEALER_WON: player.addMoney(currentBet / 2);
                case PLAYER_WON: player.addMoney(currentBet / 2 + currentBet);
            }
        }
    }

    private void endRoundPlayerWon(boolean isBlackJack) {
        if (isBlackJack) {
            player.addMoney(currentBet * BLACKJACK_MULTIPLIER);
        } else {
            player.addMoney(currentBet * WINNING_MULTIPLIER);
        }
    }
    private void endRoundDealerWon() {
        // do nothing
    }
    private void endRoundDraw() {
        player.addMoney(currentBet);
    }

    private BlackJackEnding identifyEnding(int playerPoints, int dealerPoints) {
        if (playerPoints == dealerPoints) return BlackJackEnding.DRAW;
        if (playerPoints == BLACKJACK) return BlackJackEnding.PLAYER_BLACKJACK;

        if (playerPoints > dealerPoints) {
            if (playerPoints <= BLACKJACK) return BlackJackEnding.PLAYER_WON;
            else return BlackJackEnding.DEALER_WON;
        } else {
            if (dealerPoints > BLACKJACK) return BlackJackEnding.PLAYER_WON;
            else return BlackJackEnding.DEALER_WON;
        }
    }

    public void doSplit() throws ImpossibleSplitException, NotEnoughMoneyException {
        player.doSplit();
        placeBet(firstBet * 2);
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
