package controller.statepattern;

import controller.GameEngine;
import controller.statepattern.gameplay.Startup;

import java.util.Scanner;

/**
 * The Starting phase class represents the initial phase of the game.
 * It extends the Phase class and provides functionality specific to the starting phase of the game.
 */
public class Starting extends Phase {
    /**
     * check if in starting phase.
     */
    boolean d_first = true;

    /**
     * Constructs a Starting phase with the specified GameEngine.
     *
     * @param gameEngine The GameEngine object managing the game.
     */
    public Starting(GameEngine gameEngine) {
        super(gameEngine);
        d_phaseName = "Starting Phase";
    }

    /**
     * Displays the menu for the starting phase.
     * If it's the first starting phase, it renders the welcome message.
     * Otherwise, it renders the current game phase and the starting menu options.
     */
    @Override
    public void displayMenu() {
        if (d_first) d_ge.d_renderer.renderWelcome();
        d_first = false;
        d_ge.d_renderer.renderMessage("current game phase: " + this.d_phaseName);
        String[] l_menu_options = {"Map Editor", "Play Game", "Tournament"};
        d_ge.d_renderer.renderMenu("Starting Menu", l_menu_options);
        this.d_ge.d_renderer.renderMessage("Enter Selection: ");
    }

    /**
     * Proceeds to the next phase of the game.
     */
    @Override
    public void next() {

    }

    /**
     * Ends the game.
     */
    @Override
    public void endGame() {

    }

    /**
     * Runs the starting phase.
     * It displays the menu, reads the user's selection from the console, and transitions to the appropriate phase based on the selection.
     */
    @Override
    public void run() {
        displayMenu();
        Scanner l_scanner = new Scanner(System.in);
        int l_userSelection = 0;

        try {
            l_userSelection = l_scanner.nextInt();
        } catch (Exception e) {
            this.d_ge.d_renderer.renderError("Invalid selection!");
            return;
        }

        if (l_userSelection == 1) {
            d_ge.setCurrentState(new MapEditor(d_ge));
        } else if (l_userSelection == 2) {
            d_ge.setCurrentState(new Startup(d_ge));
        } else if (l_userSelection == 3) {
            d_ge.setCurrentState(new Tournament(d_ge));
        } else if (l_userSelection == 4) {
            d_ge.setCurrentState(new End(d_ge));
        } else {
            this.d_ge.d_renderer.renderError("Invalid selection!");
        }
    }
}
