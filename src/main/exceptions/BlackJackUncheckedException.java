package main.exceptions;

public class BlackJackUncheckedException extends RuntimeException {
    public BlackJackUncheckedException() {
    }

    public BlackJackUncheckedException(String message) {
        super(message);
    }
}
