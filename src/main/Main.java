package main;

import main.cards.CardDeck;

public class Main {
    public static void main(String[] args) {
        CardDeck deck = new CardDeck();
        System.out.println(deck);
        System.out.println(deck.getDeck().size());
        System.out.println(deck.popCard());
        System.out.println(deck);
        System.out.println(deck.getDeck().size());
    }
}
