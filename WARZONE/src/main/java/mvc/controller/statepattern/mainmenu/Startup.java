package mvc.controller.statepattern.mainmenu;

import mvc.controller.GameEngine;
import mvc.controller.middleware.commands.StartupCommands;
import mvc.controller.statepattern.Phase;

/**
 * Represents the startup phase of the game.
 */
public class Startup extends Phase {
    /**
     * Represents an array of menu options.
     */
    String[] d_menu_options = {"Show Map", "Load Map", "gameplayer to Add/Remove Player", "Assign Countries"};

    /**
     * Constructor for Startup phase.
     *
     * @param p_gameEngine The GameEngine object.
     */
    public Startup(GameEngine p_gameEngine) {
        super(p_gameEngine);
    }

    /**
     * Displays the menu for the startup phase.
     */
    @Override
    public void displayMenu() {
        d_ge.d_renderer.renderMenu(
                "Main Menu",
                d_menu_options
        );
    }

    /**
     * This method is intended to advance the game to the next step or phase.
     */
    @Override
    public void next() {

    }

    /**
     * This method is intended to end the game.
     */
    @Override
    public void endGame() {

    }

    /**
     * Runs the startup phase.
     */
    @Override
    public void run() {
        displayMenu();
        StartupCommands l_suc = new StartupCommands(
                this.d_ge.d_renderer.renderUserInput("Enter command: ")
        );
        l_suc.execute(this.d_ge);
    }
}