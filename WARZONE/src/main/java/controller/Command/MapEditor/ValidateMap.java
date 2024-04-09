package controller.Command.MapEditor;

import controller.Command.Command;
import controller.GameEngine;
import controller.statepattern.MapEditor;
import view.TerminalRenderer;

import java.util.regex.Pattern;

/**
 * ValidateMap represents a command to validate the current map.
 * This command allows validating the map in the map editor mode.
 */
public class ValidateMap extends Command {
    /**
     * Constructs a ValidateMap command with specified input and game engine.
     *
     * @param p_input The input string for the command.
     * @param p_ge    The game engine to operate on.
     */
    public ValidateMap(String p_input, GameEngine p_ge) {
        super(p_input, p_ge);
        this.d_validCommandFormat = "validatemap";
        this.d_commandPattern = Pattern.compile("^validatemap(\\s)*$");
        this.d_validPhases = new Class[1];
        this.d_validPhases[0] = MapEditor.class;
    }

    /**
     * Executes the ValidateMap command, validating the current map.
     */
    @Override
    public void execute() {
        TerminalRenderer l_renderer = this.d_ge.d_renderer;
        if (this.d_ge.d_worldmap.validateMap()) {
            l_renderer.renderMessage("Map is valid");
        } else {
            l_renderer.renderMessage("Map is not valid");
        }
    }

    /**
     * Validates the logic of the ValidateMap command.
     * This method checks if the map is empty or not.
     *
     * @return true if the map is not empty, false otherwise.
     */
    @Override
    public boolean validateLogic() {
        if (this.d_ge.d_worldmap.getContinents().isEmpty()) {
            this.d_ge.d_renderer.renderError("Map is empty!");
            return false;
        } else {
            return true;
        }
    }
}
