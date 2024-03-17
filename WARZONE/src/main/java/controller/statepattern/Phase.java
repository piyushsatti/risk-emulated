package controller.statepattern;

import controller.GameEngine;
import helpers.exceptions.CountryDoesNotExistException;
import helpers.exceptions.InvalidCommandException;

/**
 * The Phase class represents a phase of the game.
 * It is an abstract class providing common functionality for different phases of the game.
 */
public abstract class Phase {

    public GameEngine d_ge;
    public String d_phaseName;

    /**
     * Constructs a Phase with the specified GameEngine.
     *
     * @param gameEngine The GameEngine object managing the game.
     */
    public Phase(GameEngine gameEngine) {
        this.d_ge = gameEngine;
    }

    /**
     * Displays the menu for the phase.
     */
    abstract public void displayMenu();

    /**
     * Proceeds to the next phase of the game.
     */
    abstract public void next();

    /**
     * Ends the game.
     */
    abstract public void endGame();

    /**
     * Runs the phase.
     * This method must be implemented in subclasses to define the main behavior of the phase.
     *
     * @throws CountryDoesNotExistException If a country referenced in the phase does not exist.
     * @throws InvalidCommandException If an invalid command is encountered during the phase execution.
     */
    abstract public void run() throws CountryDoesNotExistException, InvalidCommandException;

}
