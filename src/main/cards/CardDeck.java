package main.cards;

import java.util.*;


/*
* Это класс колоды карт
* */
public class CardDeck {

    /*
    * Deque - это тоже такой интерфейс контейнера, как List.
    *
    * Deque на самом деле сокращение от Dequeue - типа очередь (Queue) наоборот.
    * Есть такая коллекция, очередь. Если в листе ты обычно добавляешь в конец, но доступ к любому элементу откуда хочешь,
    * то в очереди ты можешь добавлять элементы только в конец и доставать и начала (First in first out) - кто первый "зашел", тот первый и "выйдет",
    * как в настоящей очереди
    *
    * Deque отличается тем, что добавляют туда в конец, но вытаскивать можно или с начала, или с конца. Как колода карт - можно брать или сверху, или снизу.
    * */
    private Deque<Card> deck;

    /*
    * Пустой конструктор иницализирует нашу колоду (ArrayDeque - самая простая реализация интерфейса Deque, как ArrayList для листа)
    * Затем проходится циклом по всем номиналам и всем мастям, создает карту и добавляет ее в Deque
    * То есть в конце обоих циклов мы гарантированно имеем всю колоду
    *
    * Затем вызываем метод shuffle который описан в этом классе и тусует колоду
    * */
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

    /*
    * Просто копи конструктор, копирующий колоду
    * */
    public CardDeck(CardDeck other) {
        deck = new ArrayDeque<>(other.getDeck());
    }

    public Deque<Card> getDeck() {
        return deck;
    }

    /*
    * Два сеттера здесь нужно чтобы можно было скармливать ему как другие Deque, так и простые листы.
    *
    * Кстати, наличие двух методов с одинаковым названием и разными параметрами - проявление полиморфизма
    * */
    private void setDeck(Deque<Card> deck) {
        this.deck = new ArrayDeque<>(deck);
    }

    private void setDeck(List<Card> deck) {
        this.deck = new ArrayDeque<>(deck);
    }

    /*
    * Наш метод тусующий колоду
    * Есть уже существующий библиотечный метод, тусующий листы, но он не принимает deque.
    * Поэтому создаем лист, копирующий содержимое нашего deque, перемешиваем этот лист
    * Затем вызываем метод сеттер, чтобы копировать в том же порядке в нашу колоду
    * */
    public void shuffle() {
        List<Card> deckAsList = new ArrayList<>(deck);
        Collections.shuffle(deckAsList);
        setDeck(deckAsList);
    }

    /*
    * метод popCard - означает вытащить карту с начала колоды.
    * Он так назван потому, что метод у deque вытаскивающий первый элемент называется pop().
    * метод pop() возвращает элемент deque, удаляя его в самой коллеции.
    * */
    public Card popCard() {
        return deck.pop();
    }

    /*
    * метод добавляющий карту в конец колоды
    * */
    public void addCard(Card card) {
        deck.addLast(card);
    }

    /*
    * Метод добавляющий сразу много карт в колоду и тусующий ее
    * */
    public void addCards(List<Card> cards) {
        deck.addAll(cards);
        shuffle();
    }


    /*
    * Метод добавляющий карту и тусующий колоду
    * */
    public void addCardAndShuffle(Card card) {
        addCard(card);
        shuffle();
    }

    /*Это автосгенерированные функции для системных нужд, я их сам не писал, только нажал alt+insert и выбрал их*/
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
