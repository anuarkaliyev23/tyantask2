package main;

import main.cards.CardDeck;
import main.cards.CardSuit;

public class Main {
    public static void main(String[] args) {
        CardDeck deck = new CardDeck();
        System.out.println(deck.getDeck());
    }
}
