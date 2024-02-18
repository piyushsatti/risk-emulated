package utils.exceptions;

/**
 * The PlayerDoesNotExistException class represents an exception that is thrown
 * when attempting to access a player that does not exist in the game.
 */
public class PlayerDoesNotExistException extends Exception {
    /**
     * Constructs a PlayerDoesNotExistException with a detail message indicating
     * that the player with the specified name does not exist.
     *
     * @param p_playerName The name of the player that does not exist.
     */

    public PlayerDoesNotExistException(String p_playerName) {

        super("player does not exist: " + p_playerName);

    }

}

