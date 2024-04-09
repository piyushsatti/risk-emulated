package controller.statepattern;
import controller.GameEngine;

/**
 * The End phase class represents the phase where the game is ending.
 * It extends the Phase class and provides functionality specific to the end phase of the game.
 */
public class End extends Phase {
    /**
     * Represents the game engine associated with this instance.
     */
    GameEngine d_gameEngine;

    /**
     * Constructs an End phase with the specified GameEngine.
     *
     * @param p_gameEngine The GameEngine object managing the game.
     */
    public End(GameEngine p_gameEngine) {
        super(p_gameEngine);
        d_gameEngine = p_gameEngine;
        if(d_gameEngine.isTournamentMode()){
            displayResults(p_gameEngine);
        }

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
    /**
     * Displays the results of the tournament games.
     *
     * @param d_gameEngine The GameEngine object containing the tournament results to be displayed.
     */
    public static void displayResults(GameEngine d_gameEngine) {
        String[][] results = d_gameEngine.getTournamentResults();
        int rows = results.length;
        int cols = results[0].length;

        // Display column headers with column indices
        System.out.print("    ");
        for (int j = 0; j < cols; j++) {
            System.out.print("Game " + (j+1));
        }
        System.out.println();

        // Display top boundary
        System.out.print("   ");
        for (int j = 0; j < (4 * cols) + 2; j++) {
            System.out.print("-");
        }
        System.out.println();

        // Display row numbers, matrix elements, and boundaries
        for (int i = 0; i < rows; i++) {
            System.out.print("Map " + (i+1)+"| ");
            for (int j = 0; j < cols; j++) {
                System.out.print(results[i][j] + " "); // Display matrix element
            }
            System.out.println(" |"); // Right boundary
        }

        // Display bottom boundary
        System.out.print("   ");
        for (int j = 0; j < (4 * cols) + 2; j++) {
            System.out.print("-");
        }
        System.out.println();
    }
}
