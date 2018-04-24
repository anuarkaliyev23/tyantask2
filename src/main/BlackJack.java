package main;

import main.cards.CardDeck;
import main.exceptions.ImpossibleSplitException;
import main.exceptions.NoSplitException;
import main.exceptions.NotEnoughMoneyException;
import main.exceptions.PlayerHaveNoPlacedBetException;

import java.util.Objects;

/*
* Класс самой игры
* */
public class BlackJack {
    private static final int INITIAL_GIVEOUT_CARDS_COUNT = 2;
    private static final int BLACKJACK = 21;

    private static final int BLACKJACK_MULTIPLIER = 3;
    private static final int WINNING_MULTIPLIER = 2;

    /*
    * Здесь ты видишь дилера, игрока, колоду
    * boolean split который показывает сплитовал ли игрок.
    * (Говорю заранее - систему сплитов можно было сделать и лучше, не всегда самые лучшие решения были использованы).
    * Количество денег сколько сейчас на кону (currentbet)
    * И сколько была первая ставка (для всяких даблов и т.д.)
    * */
    private Dealer dealer;
    private Player player;
    private CardDeck deck;
    private boolean split;
    private int currentBet;
    private int firstBet;

    /*Разные виды конструкторов*/
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

    public BlackJack(BlackJack other) {
        this.dealer = new Dealer(other.dealer);
        this.player = new Player(other.player);
        this.deck = new CardDeck(other.getDeck());
        this.split = other.isSplit();
        this.currentBet = other.getCurrentBet();
        this.firstBet = other.getFirstBet();
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

    /*Метод проверящий, былв ли начата игрв*/
    public boolean isStarted() {
        return firstBet != 0;
    }

    /*Функция проверяющая можно ли добавить карту в руку (основную или сплитовую) игрока.
    * Если он пытается добавить карту в сплит, но сплита в игре не было - кидает исключение
    * */
    private void checkCardAdding(boolean toHand) {
        if (!toHand && !split) throw new NoSplitException();
    }

    /*
    * Функция делающая ставку за игрока
    * Если не хватает денег - кидает исключение
    * */
    public void placeBet(int amount) throws NotEnoughMoneyException {
        if (firstBet == 0) {
            firstBet = player.takeMoneyForBet(amount);
            currentBet += firstBet;
        } else {
            currentBet += player.takeMoneyForBet(amount);
        }
    }

    /*
    * Функция, производящая дабл за игрока. Ставка удваивается, дается еще одна карта.
    * Параметр toHand нужен чтобы определить куда давать карту - на сплит или в основную руку.
    * К сожалению я не доделал и он не учитывает в расчет выигрыша откуда была какая ставка - все сгребается в общий котел
    *
    * */
    public void doublePlayerBet(boolean toHand) throws PlayerHaveNoPlacedBetException, NotEnoughMoneyException {
        checkCardAdding(toHand);
        if (firstBet == 0) throw new PlayerHaveNoPlacedBetException();
        currentBet += player.takeMoneyForBet(firstBet * 2);
        addCardToPlayer(toHand);
    }

    /*
    * Произвести первую раздачу с колоды
    * */
    public void giveOutInitialCards() {
        for (int i = 0; i < INITIAL_GIVEOUT_CARDS_COUNT; i++) {
            player.addCardToHand(deck.popCard());
            dealer.addCardToHand(deck.popCard());
        }
    }

    /*
    *
    * Функция забирающая у всех карты (обычно вызывается в конце раунда)
    * */

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


    /*
    * Функция завершающая раунд.
    * В случае если спита не было считает очки игрока и дилера, отправляет их другой функции чтобы понять кто выиграл
    * Если был сплит каждую руку считает отдельно
    * */
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

    /*
    * Функция, которая принимает параметром вариант окончания игры и вызывает соответствующую функцию чтобы отдать или забрать выигрыш
    * */
    private void endRound(BlackJackEnding ending) {
        switch (ending) {
            case DRAW: endRoundDraw(); break;
            case PLAYER_WON: endRoundPlayerWon(false); break;
            case PLAYER_BLACKJACK: endRoundPlayerWon(true); break;
//            case DEALER_WON: endRoundDealerWon();
        }
    }

    /*
    * Функция, считающая как закончить раунд в случае сплита со всеми возможными вариантами
    * (например - обе руки выиграли \ одна выиграла, одна проиграла \ обе проиграли)
    * */
    private void endRoundSplit(BlackJackEnding playerHand, BlackJackEnding playerSplit) {
        if (playerHand == BlackJackEnding.DRAW && playerSplit == BlackJackEnding.DRAW) endRoundDraw();

        if (playerHand == BlackJackEnding.PLAYER_WON) {
            switch (playerSplit) {
                case DRAW: player.addMoney((int) (currentBet * 1.5));  break;
                case DEALER_WON: player.addMoney(currentBet); break;
                case PLAYER_BLACKJACK: player.addMoney((int) (currentBet + currentBet * 1.5)); break;
                case PLAYER_WON: player.addMoney(currentBet * 2 * WINNING_MULTIPLIER); break;
            }
        } else if (playerHand == BlackJackEnding.PLAYER_BLACKJACK) {
            switch (playerSplit) {
                case PLAYER_BLACKJACK: player.addMoney(currentBet * BLACKJACK_MULTIPLIER / 2 + currentBet * BLACKJACK_MULTIPLIER / 2); break;
                case DRAW: player.addMoney(currentBet / 2 + currentBet * BLACKJACK_MULTIPLIER / 2); break;
                case DEALER_WON: player.addMoney(currentBet * BLACKJACK_MULTIPLIER / 2); break;
                case PLAYER_WON: player.addMoney(currentBet * BLACKJACK_MULTIPLIER/ 2 + currentBet); break;
            }
        } else if (playerHand == BlackJackEnding.DEALER_WON) {
            switch (playerSplit) {
                case PLAYER_BLACKJACK: player.addMoney(currentBet * BLACKJACK_MULTIPLIER / 2); break;
                case DRAW: player.addMoney(currentBet / 2); break;
//                case DEALER_WON: player.addMoney();
                case PLAYER_WON: player.addMoney(currentBet); break;
            }
        } else if (playerHand == BlackJackEnding.DRAW) {
            switch (playerSplit) {
                case DRAW: player.addMoney(currentBet); break;
                case PLAYER_BLACKJACK: player.addMoney(currentBet / 2 + currentBet * BLACKJACK_MULTIPLIER/ 2); break;
                case DEALER_WON: player.addMoney(currentBet / 2); break;
                case PLAYER_WON: player.addMoney(currentBet / 2 + currentBet); break;
            }
        }
    }

    /*Функция отдающая деньги игроку в случае победы игрока*/
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

    /*Функция отдающая деньги игроку в случае ничьи*/
    private void endRoundDraw() {
        player.addMoney(currentBet);
    }

    /*
    * Функция определяющая как закончился матч
    * Параметром передаются очки игрока и дилера
    * */
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


    /*Функция производящая сплит по правилам Блэкджека*/
    public void doSplit() throws ImpossibleSplitException, NotEnoughMoneyException {
        player.doSplit();
        placeBet(firstBet * 2);
        split = true;
    }

    /*Автосгенеринные функции*/
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
