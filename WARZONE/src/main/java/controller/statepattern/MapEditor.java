package controller.statepattern;

import controller.GameEngine;
import controller.middleware.commands.MapEditorCommands;

/**
 * The MapEditor phase class represents the phase where the map editing functionality is available.
 * It extends the Phase class and provides functionality specific to the map editing phase of the game.
 */
public class MapEditor extends Phase {
    public MapEditor(GameEngine g) {
        super(g);
        displayMenu();
    }

    /**
     * Displays the menu for the map editor phase.
     * This method renders the map editor commands using the game engine's renderer.
     */
    @Override
    public void displayMenu() {
        d_ge.d_renderer.renderMapEditorCommands();
    }

    /**
     * Proceeds to the next phase.
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
     * Runs the map editor phase.
     * This method displays the menu for map editor commands, receives user input, and executes the corresponding command.
     */
    @Override
    public void run() {
        String in = this.d_ge.d_renderer.renderUserInput(">>> ");

        if (in.equals("exit")) {
            d_ge.setCurrentState(new End(d_ge));
            return;
        }

        MapEditorCommands mec = new MapEditorCommands(in);
        mec.execute(this.d_ge);
    }
}



