package controller.statepattern;

import controller.GameEngine;

/**
 * The Phase class represents a phase of the game.
 * It is an abstract class providing common functionality for different phases of the game.
 */
public abstract class Phase {

    public GameEngine d_ge;
    public String d_phaseName;

    public Phase(GameEngine g) {
        this.d_ge = g;
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

    abstract public void run();

}
