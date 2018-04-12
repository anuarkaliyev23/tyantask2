package main;

import main.cards.Card;
import main.cards.CardDeck;
import main.cards.CardSuit;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Player player = new Player(100);
        player.setHand(new ArrayList<>(new CardDeck().getDeck()));
        player.setSplit(new ArrayList<>(new CardDeck().getDeck()));
        System.out.println(player);

        player.loseCards();
        System.out.println(player);
    }
}
