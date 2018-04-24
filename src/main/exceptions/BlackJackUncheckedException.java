package main.exceptions;

/*
 * Этот класс - родительский класс всех unchecked exceptions в этом проекте. Это нужно для большего удобства в отлове.
 * То есть ловя этот Exception в катче я гарантирую что поймаю всех его потомков, что гарантирует, что я словлю именно написанные собой исключения.
 * */
public class BlackJackUncheckedException extends RuntimeException {
    public BlackJackUncheckedException() {
    }

    public BlackJackUncheckedException(String message) {
        super(message);
    }
}
