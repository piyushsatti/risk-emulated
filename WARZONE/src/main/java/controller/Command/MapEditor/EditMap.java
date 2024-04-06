package controller.Command.MapEditor;

import controller.Command.Command;
import controller.GameEngine;
import controller.MapFileManagement.MapInterface;
import controller.statepattern.MapEditor;
import view.TerminalRenderer;
import java.util.regex.Pattern;

/**
 * EditMap represents a command to edit a map in the game.
 */
public class EditMap extends Command {
    /**
     * Constructs an EditMap command with specified input and game engine.
     * @param p_input The input string for the command.
     * @param p_ge The game engine to operate on.
     */
    public EditMap(String p_input, GameEngine p_ge) {
        super(p_input, p_ge);
        this.d_validCommandFormat = "editmap <map name>.map";
        this.d_commandPattern = Pattern.compile("editmap\\s\\w+\\.map(\\s)*$");
        this.d_validPhases = new Class[1];
        this.d_validPhases[0] = MapEditor.class;
    }

    /**
     * Executes the EditMap command, loading or saving map files.
     */
    @Override
    public void execute() {


        MapInterface mp = new MapInterface();
        String mapName = "";
        TerminalRenderer l_renderer = this.d_ge.d_renderer;
        mapName = this.d_splitCommand[1];

        try {
            this.d_ge.d_worldmap = mp.loadMap(this.d_ge, mapName);
        } catch (Exception e) {
            l_renderer.renderError("FileNotFoundException : File does not exist.");
            l_renderer.renderMessage("Creating file by the name : " + mapName);
            try {
                mp.saveMap(this.d_ge, mapName);
                this.d_ge.d_worldmap = mp.loadMap(this.d_ge, mapName);
            } catch (Exception ex) {
                l_renderer.renderError(ex.toString());
            }
        }
    }

    /**
     * Validates the logic of the EditMap command.
     * @return true
     */
    @Override
    public boolean validateLogic() {
        return true;
    }
}
