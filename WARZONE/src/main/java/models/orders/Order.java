package models.orders;

import helpers.exceptions.InvalidCommandException;

/**
 * The Order class represents a player's order in the game.
 * It specifies the movement of reinforcements from one country to another.
 */
public interface Order {

    /**
     * Validates the command for the order.
     *
     * @return True if the command is valid, false otherwise
     * @throws InvalidCommandException if the command is invalid
     */
    public boolean validateCommand() throws InvalidCommandException;

    /**
     * used to execute the order. Is implemented by the Concrete Command classes.
     */
    public void execute();

}
