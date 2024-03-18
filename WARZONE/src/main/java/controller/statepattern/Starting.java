package controller.statepattern;

import controller.GameEngine;
import controller.statepattern.gameplay.Startup;
import helpers.TerminalColors;

/**
 * The Starting phase class represents the initial phase of the game.
 * It extends the Phase class and provides functionality specific to the starting phase of the game.
 */
public class Starting extends Phase {
    public Starting(GameEngine g) {
        super(g);
        d_phaseName = "Starting Phase";
    }

    /**
     * Displays the menu for the starting phase.
     * If it's the first starting phase, it renders the welcome message.
     * Otherwise, it renders the current game phase and the starting menu options.
     */
    @Override
    public void displayMenu() {
        d_ge.d_renderer.renderMessage("Current Phase : " +
                TerminalColors.ANSI_PURPLE + this.d_phaseName +
                TerminalColors.ANSI_RESET
        );
        String[] menu_options = {"mapeditor", "playgame", "exit"};
        d_ge.d_renderer.renderMenu("Starting Menu", menu_options);
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
        String in = d_ge.d_renderer.renderUserInput(">>> ");

        switch (in) {
            case "mapeditor":
                d_ge.setCurrentState(new MapEditor(d_ge));
                break;
            case "playgame":
                d_ge.setCurrentState(new Startup(d_ge));
                break;
            case "exit":
                d_ge.setCurrentState(new End(d_ge));
                break;
            default:
                d_ge.d_renderer.renderError("Invalid selection!");
        }
    }
}
