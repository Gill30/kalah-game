package com.gill.kalah.exception;

public class GameException extends Exception {
    public GameException(String message) {
        super("Error : " + message);
    }
}

