package controller.Command.MapEditor;

import controller.Command.Command;
import controller.GameEngine;
import controller.statepattern.End;
import controller.statepattern.MapEditor;
import controller.statepattern.Starting;

import java.util.regex.Pattern;

/**
 * Exit represents a command to exit the map editor mode.
 */
public class Exit extends Command {
    /**
     * Constructs an Exit command with specified input and game engine.
     *
     * @param p_input The input string for the command.
     * @param p_ge    The game engine to operate on.
     */
    public Exit(String p_input, GameEngine p_ge) {
        super(p_input, p_ge);
        this.d_commandPattern = Pattern.compile("^exit(\\s)*$");
        this.d_validPhases = new Class[1];
        this.d_validPhases[0] = MapEditor.class;
    }

    /**
     * Executes the Exit command, transitioning the game state accordingly.
     */
    @Override
    public void execute() {
        if (this.d_ge.getCurrentState().getClass() != Starting.class) {
            this.d_ge.resetMap();
            this.d_ge.setCurrentState(new Starting(this.d_ge));
        } else {
            this.d_ge.setCurrentState(new End(this.d_ge));
        }
    }

    /**
     * Validates the logic of the Exit command.
     * This method always returns true as there is no specific logic to validate.
     *
     * @return true
     */
    @Override
    public boolean validateLogic() {
        return true;
    }
}
