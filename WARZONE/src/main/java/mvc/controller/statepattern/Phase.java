package mvc.controller.statepattern;

import helpers.exceptions.CountryDoesNotExistException;
import helpers.exceptions.InvalidCommandException;
import mvc.controller.GameEngine;

/**
 * The Phase class represents a phase of the game.
 * It is an abstract class providing common functionality for different phases of the game.
 */
public abstract class Phase {

    /**
     * The game engine associated with this phase.
     */
    public GameEngine d_ge;

    /**
     * The name of the phase.
     */
    public String d_phaseName;

    /**
     * Constructs a Phase with the specified GameEngine.
     *
     * @param p_gameEngine The GameEngine object managing the game.
     */
    public Phase(GameEngine p_gameEngine) {
        this.d_ge = p_gameEngine;
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
     * @throws InvalidCommandException      If an invalid command is encountered during the phase execution.
     */
    abstract public void run() throws CountryDoesNotExistException, InvalidCommandException;

}
