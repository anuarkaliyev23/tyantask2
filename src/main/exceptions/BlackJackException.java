package main.exceptions;

/*
* Этот класс - родительский класс всех checked exceptions в этом проекте. Это нужно для большего удобства в отлове.
* То есть ловя этот Exception в катче я гарантирую что поймаю всех его потомков, что гарантирует, что я словлю именно написанные собой исключения.
* */
public class BlackJackException extends Exception {
    public BlackJackException() {
    }

    public BlackJackException(String message) {
        super(message);
    }
}
