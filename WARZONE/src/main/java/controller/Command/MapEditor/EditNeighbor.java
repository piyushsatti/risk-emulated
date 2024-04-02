package controller.Command.MapEditor;

import controller.Command.Command;
import controller.GameEngine;
import controller.statepattern.MapEditor;
import models.worldmap.WorldMap;
import view.TerminalRenderer;

import java.util.regex.Pattern;

public class EditNeighbor extends Command {

    public EditNeighbor(String p_input, GameEngine p_ge) {
        super(p_input, p_ge);
        this.d_validCommandFormat = "editneighbor -add <countryID> <neighborcountryID>";
        this.d_commandPattern = Pattern.compile("^editneighbor(?:(?:\\s+-add\\s+\\w+\\s+\\w+)*(?:\\s+-remove\\s+\\w+\\s+\\w+)*)*(\\s)*$");
        this.d_validPhases = new Class[1];
        this.d_validPhases[0] = MapEditor.class;
    }

    @Override
    public void execute() {

    }

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
