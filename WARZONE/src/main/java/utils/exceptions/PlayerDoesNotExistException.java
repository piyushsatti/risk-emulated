package main.java.utils.exceptions;

public class PlayerDoesNotExistException extends Exception {
    public PlayerDoesNotExistException(String p_playerName){
        super("player does not exist: " + p_playerName);
    }
}

