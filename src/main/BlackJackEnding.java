package main;

/*Перечисление всех возможных окончаний игры
*
* Победа игрока
* Победа дилера
* Ничья
* Победа игрока путем Блэкджека
* */
public enum BlackJackEnding {
    PLAYER_WON, DEALER_WON, DRAW, PLAYER_BLACKJACK;
}
