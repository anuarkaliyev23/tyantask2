package main.cards;

import java.util.*;

public class CardDeck {
    private Deque<Card> deck;

    public CardDeck() {
        deck = new ArrayDeque<>();
        for (CardFace face: CardFace.values()) {
            for (CardSuit suit: CardSuit.values()) {
                Card card = new Card(face, suit);
                deck.add(card);
            }
        }

        this.shuffle();
    }

    public CardDeck(CardDeck other) {
        deck = new ArrayDeque<>(other.getDeck());
    }

    public Deque<Card> getDeck() {
        return deck;
    }

    private void setDeck(Deque<Card> deck) {
        this.deck = new ArrayDeque<>(deck);
    }

    private void setDeck(List<Card> deck) {
        this.deck = new ArrayDeque<>(deck);
    }

    public void shuffle() {
        List<Card> deckAsList = new ArrayList<>(deck);
        Collections.shuffle(deckAsList);
        setDeck(deckAsList);
    }

    public Card popCard() {
        return deck.pop();
    }

    public void addCard(Card card) {
        deck.addLast(card);
    }

    public void addCardAndShuffle(Card card) {
        addCard(card);
        shuffle();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CardDeck cardDeck = (CardDeck) o;
        return Objects.equals(deck, cardDeck.deck);
    }

    @Override
    public int hashCode() {
        return Objects.hash(deck);
    }

    @Override
    public String toString() {
        return "CardDeck{" +
                "deck=" + deck +
                '}';
    }
}
