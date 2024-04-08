package controller.Command.MapEditor;

import controller.Command.Command;
import controller.GameEngine;
import controller.MapFileManagement.ConquestMapInterface;
import controller.MapFileManagement.MapAdapter;
import controller.MapFileManagement.MapFileLoader;
import controller.MapFileManagement.MapInterface;
import controller.statepattern.MapEditor;
import models.worldmap.WorldMap;
import view.TerminalRenderer;

import java.util.regex.Pattern;

/**
 * EditMap represents a command to edit a map in the game.
 */
public class EditMap extends Command {
    /**
     * Constructs an EditMap command with specified input and game engine.
     *
     * @param p_input The input string for the command.
     * @param p_ge    The game engine to operate on.
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

        MapInterface mp = null;
        String mapName = "";
        TerminalRenderer l_renderer = this.d_ge.d_renderer;
        mapName = this.d_splitCommand[1];

        MapFileLoader l_mfl = new MapFileLoader(this.d_ge, mapName);

        if (!l_mfl.fileLoaded()) {
            l_renderer.renderError("File does not exist.");
            l_renderer.renderMessage("Creating new map");
            this.d_ge.d_worldmap = new WorldMap();
            return;
        }

        if (l_mfl.isConquest()) {
            mp = new MapAdapter(new ConquestMapInterface());
        } else {
            mp = new MapInterface();
        }

        this.d_ge.d_worldmap = mp.loadMap(this.d_ge, l_mfl);

    }

    /**
     * Validates the logic of the EditMap command.
     *
     * @return true
     */
    @Override
    public boolean validateLogic() {
        return true;
    }
}
