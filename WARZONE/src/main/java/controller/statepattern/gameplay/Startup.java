package controller.statepattern.gameplay;

import controller.GameEngine;
import controller.middleware.commands.StartupCommands;
import controller.statepattern.MapEditor;
import controller.statepattern.Phase;

/**
 * Represents the startup phase of the game.
 */
public class Startup extends Phase {
    String[] menu_options = {"Show Map", "Load Map", "gameplayer to Add/Remove Player", "Assign Countries"};

    /**
     * Constructor for Startup phase.
     *
     * @param gameEngine The GameEngine object.
     */
    public Startup(GameEngine gameEngine) {
        super(gameEngine);
    }

    /**
     * Displays the menu for the startup phase.
     */
    @Override
    public void displayMenu() {
        d_ge.d_renderer.renderMenu(
                "Main Menu",
                menu_options
        );
    }

    @Override
    public void next() {
        this.d_ge.setCurrentState(new MapEditor(d_ge));
    }

    @Override
    public void endGame() {

    }

    /**
     * Runs the startup phase.
     */
    @Override
    public void run() {
        displayMenu();
        StartupCommands suc = new StartupCommands(
                this.d_ge.d_renderer.renderUserInput("Enter command: ")
        );
        suc.execute(this.d_ge);
    }
}