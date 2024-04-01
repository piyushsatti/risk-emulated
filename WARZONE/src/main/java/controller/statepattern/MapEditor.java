package controller.statepattern;

import controller.Command.Command;
import controller.Command.CommandCreator;
import controller.GameEngine;
import controller.middleware.commands.MapEditorCommands;

/**
 * The MapEditor phase class represents the phase where the map editing functionality is available.
 * It extends the Phase class and provides functionality specific to the map editing phase of the game.
 */
public class MapEditor extends Phase {
    /**
     * Constructs a MapEditor phase with the specified GameEngine.
     *
     * @param p_gameEngine The GameEngine object managing the game.
     */
    public MapEditor(GameEngine p_gameEngine) {
        super(p_gameEngine);
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
        displayMenu();

        /*MapEditorCommands mec = new MapEditorCommands(
                this.d_ge.d_renderer.renderUserInput("Enter command: ")
        );
        mec.execute(this.d_ge);*/

        Command l_command = CommandCreator.createCommand(this.d_ge.d_renderer.renderUserInput("Enter command: "), this.d_ge);

        if(l_command != null){
            if(l_command.validate()){
                l_command.execute();
            }
        }else{
            this.d_ge.d_renderer.renderError("Invalid command!");
        }
    }
}



