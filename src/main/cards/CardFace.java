package main.cards;

import main.exceptions.BlackJackUncheckedException;

/*
* Это енам на номинал карты
* */
public enum CardFace {
    /*
    * Как видишь здесь перечислены разные номиналы, в их числе:
    * Туз, двойка, тройка, четверка, пятерка, шекстрека, семерка, восьмерка, девятка, десятка, валет, дама и король
    * */
    ACE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING;

    /*
    * Здесь перечислены количества баллов за каждую карту.
    *
    * модификатор статик, когда применен на функцию, означает, что ее можно доставать без создания объекта.
    * Для переменных справедливо то же самое.
    *
    * Тебя может удивить зачем тогда я сделал их приватными.
    * Дело в том, что модификатор статик на самом деле означает, что эта функция
    * или переменная создается всего один раз ДЛЯ ВСЕХ экземлпяров (объектов). Вследствие чего
    * она и может доставаться без создания объекта.
    *
    * Поэтому я отметил их как статик, чтобы они не создавались внутри каждого объекта и съедали лишнюю память.
    * */
    private static int ACE_POINTS = 11;
    private static int TWO_POINTS = 2;
    private static int THREE_POINTS = 3;
    private static int FOUR_POINTS = 4;
    private static int FIVE_POINTS = 5;
    private static int SIX_POINTS = 6;
    private static int SEVEN_POINTS = 7;
    private static int EIGHT_POINTS = 8;
    private static int NINE_POINTS = 9;
    private static int TEN_POINTS = 10;
    private static int JACK_POINTS = 10;
    private static int QUEEN_POINTS = 10;
    private static int KING_POINTS = 10;

    /*
    * Функция, возвращающая количество очков за каждую карту
    * В случае default она бросает исключение. Это нужно, чтобы если я случайно добавлю
    * потом новый номинал не офигевал от непредсказуемого поведения этой функции, а вместо этого
    * увидел исключение
    *
    * Всегда лучще когда программа ломается громко с треском и явно, чем незаметно, трудно отслеживаемо и неявно
    * */
    public int getPoints() {
        switch (this) {
            case ACE: return ACE_POINTS;
            case TWO: return TWO_POINTS;
            case THREE: return THREE_POINTS;
            case FOUR: return FOUR_POINTS;
            case FIVE: return FIVE_POINTS;
            case SIX: return SIX_POINTS;
            case SEVEN: return SEVEN_POINTS;
            case EIGHT: return EIGHT_POINTS;
            case NINE: return NINE_POINTS;
            case TEN: return TEN_POINTS;
            case JACK: return JACK_POINTS;
            case QUEEN: return QUEEN_POINTS;
            case KING: return KING_POINTS;
            default: throw new BlackJackUncheckedException("Unknown card");
        }
    }

    /*
    * Функция проверяющая что номинал карты - "картинка", то есть туз, король, дама или валет.
    *
    * Тебя может смутить запись, но это запись абсолютно идентична по результату:
    * if (this == ACE || this == KING || this == QUEEN || this == JACK) return true; else false;
    *
    * Дело в том что вот эта часть: (this == ACE || this == KING || this == QUEEN || this == JACK)
    * это по сути boolean выражение и возвращает тоже boolean. То есть она буквально возвращает true если карта равна тузу или королю или валету или даме.
    * В этом случае нам просто незачем оборачивать это в еще один if чтобы возвращать true если выражение равно true. Можно просто вернуть результат выражения
    * */
    public boolean isPicture() {
        return this == ACE || this == KING || this == QUEEN || this == JACK;
    }
}
