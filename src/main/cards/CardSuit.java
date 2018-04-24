package main.cards;

import main.exceptions.BlackJackUncheckedException;

/*
* Это енам для масти карты.
* Как видишь, в нем перечислены четыре масти, затем идут методы
* */
public enum CardSuit {
    HEARTS, DIAMONDS, CLUBS, SPADES;

    /*
    * Все нижеследующие стринги - просто символы чтобы в консоли отображался реальный символ масти
    * типа сердечка для червей.
    * */
    private static String HEARTS_UNICODE = "\u2661";
    private static String DIAMONDS_UNICODE = "\u2662";
    private static String SPADES_UNICODE = "\u2660";
    private static String CLUBS_UNICODE = "\u2663";

    /*
    * Метод, который отдает символ масти в зависимости от масти
    *
    * default вызывается в случае если не один из кейсов не подошел
    * я его добавил на всякий случай, чтобы если вдруг я когда в будущем добавил бы новую масть (нереальный сценарий, но все же)
    * и забыл выделить для нее символ, то он выкинул бы исключение, если бы я попытался ее использовать.
    *
    * То есть защита от дурака в лице будущего себя
    * */
    public String getUnicodeString() {
        switch (this) {
            case HEARTS: return HEARTS_UNICODE;
            case DIAMONDS: return DIAMONDS_UNICODE;
            case SPADES: return SPADES_UNICODE;
            case CLUBS: return CLUBS_UNICODE;
            default: throw new BlackJackUncheckedException("Unknown card suit");
        }
    }


}
