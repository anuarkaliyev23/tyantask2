package main;

import main.exceptions.BlackJackNotStartedExcetption;
import main.exceptions.ImpossibleSplitException;
import main.exceptions.InvalidActionException;
import main.exceptions.NotEnoughMoneyException;

public class BlackJackUI {
    private BlackJack blackJack;

    public BlackJack getBlackJack() {
        return blackJack;
    }

    public void setBlackJack(BlackJack blackJack) {
        this.blackJack = new BlackJack(blackJack);
    }

    public BlackJackUI(int money) {
        blackJack = new BlackJack(money);
    }

    public BlackJack doAction(BlackJackAction action) throws ImpossibleSplitException, NotEnoughMoneyException {
        if (!blackJack.isStarted()) throw new BlackJackNotStartedExcetption();
        switch (action) {
            case HIT_HAND: return doHit(true);
            case HIT_SPLIT: return doHit(false);
            case SPLIT: return doSplit();
            case DOUBLE_HAND: return doDouble(true);
            case DOUBLE_SPLIT: return doDouble(false);
            case STAND: return doStand();
            default: throw new UnsupportedOperationException();
        }
    }

    public BlackJack start(int bet) throws NotEnoughMoneyException {
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
        blackJack.endRound();
        return blackJack;
    }

    public String getMenu() {
        StringBuilder builder = new StringBuilder();
        builder.append(blackJack).append("\n");
        builder.append("Available actions: \n");
        for (BlackJackAction action : BlackJackAction.values()) {
            builder.append(action.ordinal()).append(") ").append(action).append("\n");
        }
        return builder.toString();
    }

    public BlackJack doAction(int ordinal) throws ImpossibleSplitException, NotEnoughMoneyException, InvalidActionException {
        for (BlackJackAction action : BlackJackAction.values()) {
            if (action.ordinal() == ordinal)
                doAction(action);
        }
        throw new InvalidActionException();
    }


}
