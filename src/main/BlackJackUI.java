package main;

import main.exceptions.ImpossibleSplitException;
import main.exceptions.NotEnoughMoneyException;

public class BlackJackUI {
    private BlackJack blackJack;

    public BlackJackUI(int money) {
        blackJack = new BlackJack(money);
    }

    public BlackJack doAction(BlackJackAction action) {
        throw new UnsupportedOperationException();
    }

    private BlackJack doStart(int bet) throws NotEnoughMoneyException {
        blackJack.placeBet(bet);
        blackJack.giveOutInitialCards();
        return blackJack;
    }

    private BlackJack doSplit() throws ImpossibleSplitException, NotEnoughMoneyException {
        blackJack.doSplit();
        return blackJack;
    }

    private BlackJack doDouble(boolean toHand) throws NotEnoughMoneyException {
        blackJack.doublePlayerBet(toHand);
        return blackJack;
    }

    private BlackJack doHit(boolean toHand) {
        blackJack.addCardToPlayer(toHand);
        return blackJack;
    }

    private BlackJack doStand() {
        throw new UnsupportedOperationException();
    }



}
