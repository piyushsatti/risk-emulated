package controller.Command.MapEditor;

import controller.Command.Command;
import controller.GameEngine;
import controller.statepattern.MapEditor;
import models.worldmap.WorldMap;
import view.TerminalRenderer;

import java.util.regex.Pattern;

/**
 * EditNeighbor represents a command to edit neighbor relationships between countries in the game.
 */
public class EditNeighbor extends Command {
    /**
     * Constructs an EditNeighbor command with specified input and game engine.
     * @param p_input The input string for the command.
     * @param p_ge The game engine to operate on.
     */
    public EditNeighbor(String p_input, GameEngine p_ge) {
        super(p_input, p_ge);
        this.d_validCommandFormat = "editneighbor -add <countryID> <neighborcountryID>";
        this.d_commandPattern = Pattern.compile("^editneighbor(?:(?:\\s+-add\\s+\\w+\\s+\\w+)*(?:\\s+-remove\\s+\\w+\\s+\\w+)*)*(\\s)*$");
        this.d_validPhases = new Class[1];
        this.d_validPhases[0] = MapEditor.class;
    }

    /**
     * Executes the EditNeighbor command, adding or removing neighbor relationships between countries.
     */
    @Override
    public void execute() {

    }

    /**
     * Validates the logic of the EditNeighbor command.
     * This method checks if the command is valid and its execution won't lead to errors.
     * @return true if the command logic is valid, false otherwise.
     */
    @Override
    public boolean validateLogic() {
        WorldMap copyMap = null;
        TerminalRenderer l_renderer = this.d_ge.d_renderer;
        int commandLength = this.d_splitCommand.length;

        try {
            copyMap = new WorldMap(this.d_ge.d_worldmap);
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }

        int commandIndex = 1;
        while (commandIndex < commandLength) {

            if (this.d_splitCommand[commandIndex].equals("-add")) {

                try {
                    copyMap.addBorder(copyMap.getCountryID(this.d_splitCommand[commandIndex + 1]), copyMap.getCountryID(this.d_splitCommand[commandIndex + 2]));
                } catch (Exception e) {
                    l_renderer.renderError(e.toString());
                    return false;
                }
                commandIndex = commandIndex + 3;

            } else if (this.d_splitCommand[commandIndex].equals("-remove")) {

                try {
                    copyMap.removeBorder(copyMap.getCountryID(this.d_splitCommand[commandIndex + 1]), copyMap.getCountryID(this.d_splitCommand[commandIndex + 2]));
                } catch (Exception e) {
                    l_renderer.renderError(e.toString());
                    return false;
                }
                commandIndex = commandIndex + 3;
            }
        }
        return true;
    }

}
