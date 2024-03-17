package controller.statepattern;
import controller.GameEngine;

/**
 * The End phase class represents the phase where the game is ending.
 * It extends the Phase class and provides functionality specific to the end phase of the game.
 */
public class End extends Phase {
    GameEngine d_gameEngine;

    /**
     * Constructs an End phase with the specified GameEngine.
     *
     * @param p_gameEngine The GameEngine object managing the game.
     */
    public End(GameEngine p_gameEngine) {
        super(p_gameEngine);
        d_gameEngine = p_gameEngine;
        displayMenu();
    }

    /**
     * Displays the menu for the end phase.
     * This method renders the exit message using the game engine's renderer.
     */
    @Override
    public void displayMenu() {
        d_gameEngine.d_renderer.renderExit();
    }

    /**
     * Proceeds to the next phase.
     *
     */
    @Override
    public void next() {
    }

    /**
     * Ends the game.
     *
     */
    @Override
    public void endGame() {
    }

    /**
     * Runs the end phase.
     *
     */
    @Override
    public void run() {
    }
}
