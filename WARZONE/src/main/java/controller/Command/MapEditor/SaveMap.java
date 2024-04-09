package controller.Command.MapEditor;

import controller.Command.Command;
import controller.GameEngine;
import controller.MapFileManagement.MapInterface;
import controller.statepattern.MapEditor;
import view.TerminalRenderer;

import java.io.IOException;
import java.util.regex.Pattern;

/**
 * SaveMap represents a command to save the current map in the map editor mode.
 * This command allows saving the current map to a file.
 */
public class SaveMap extends Command {
    /**
     * Constructs a SaveMap command with specified input and game engine.
     *
     * @param p_input The input string for the command.
     * @param p_ge    The game engine to operate on.
     */
    public SaveMap(String p_input, GameEngine p_ge) {
        super(p_input, p_ge);
        this.d_validCommandFormat = "savemap <map name>.map";
        this.d_commandPattern = Pattern.compile("^savemap\\s\\w+\\.map(\\s)*$");
        this.d_validPhases = new Class[1];
        this.d_validPhases[0] = MapEditor.class;
    }

    /**
     * Executes the SaveMap command, saving the current map to a file.
     */
    @Override
    public void execute() {
        MapInterface mp = new MapInterface();
        TerminalRenderer l_renderer = this.d_ge.d_renderer;
        try {
            mp.saveMap(this.d_ge, this.d_splitCommand[1]);
        } catch (IOException e) {
            l_renderer.renderError(e.toString());
        }
    }

    /**
     * Validates the logic of the SaveMap command.
     * This method always returns true as there is no specific logic to validate.
     *
     * @return true
     */
    @Override
    public boolean validateLogic() {
        return true;
    }
}
