package controller.Command.MapEditor;

import controller.Command.Command;
import controller.GameEngine;
import controller.statepattern.MapEditor;
import models.worldmap.WorldMap;
import view.TerminalRenderer;

import java.util.regex.Pattern;

/**
 * EditContinent represents a command to edit continents in the game.
 */
public class EditContinent extends Command {

    /**
     * EditContinent command with specified input and game engine.
     *
     * @param p_input The input string for the command.
     * @param p_ge    The game engine to operate on.
     */
    public EditContinent(String p_input, GameEngine p_ge) {
        super(p_input, p_ge);
        this.d_validCommandFormat = "editcontinent -add <continentID> <continentvalue> -remove <continentID>";
        this.d_commandPattern = Pattern.compile("^editcontinent(?:(?:\\s-add\\s+\\w+\\s+\\d+)*(?:\\s+-remove\\s+\\w+)*)*(\\s)*$");
        this.d_validPhases = new Class[1];
        this.d_validPhases[0] = MapEditor.class;
    }

    /**
     * Executes the EditContinent command, adding or removing continents from the map.
     */
    @Override
    public void execute() {
        int l_commandLength = this.d_splitCommand.length;
        int l_commandIndex = 1;
        WorldMap l_worldMap = this.d_ge.d_worldmap;
        while (l_commandIndex < l_commandLength) {

            if (d_splitCommand[l_commandIndex].equals("-add")) { //adding continent with continentID and continentValue in world map

                try {
                    l_worldMap.addContinent(d_splitCommand[l_commandIndex + 1], Integer.parseInt(d_splitCommand[l_commandIndex + 2]));
                } catch (Exception e) {
                    System.out.println(e);
                }
                l_commandIndex = l_commandIndex + 3;

            } else if (d_splitCommand[l_commandIndex].equals("-remove")) { //removing continent with continentID from world map

                try {
                    l_worldMap.removeContinent(l_worldMap.getContinentID(d_splitCommand[l_commandIndex + 1]));
                } catch (Exception e) {
                    System.out.println(e);
                }
                l_commandIndex = l_commandIndex + 2;
            }
        }
    }


    /**
     * Validates the logic of the EditContinent command.
     *
     * @return true if the command logic is valid, false otherwise.
     */
    @Override
    public boolean validateLogic() {
        TerminalRenderer l_renderer = this.d_ge.d_renderer;

        WorldMap l_copyMap = null;

        int l_commandLength = this.d_splitCommand.length;

        try {
            l_copyMap = new WorldMap(this.d_ge.d_worldmap);
        } catch (Exception e) {
            l_renderer.renderError(e.toString());
            return false;
        }

        int l_commandIndex = 1;
        while (l_commandIndex < l_commandLength) {

            if (d_splitCommand[l_commandIndex].equals("-add")) { //adding continent with continentID and continentValue in copy map

                try {
                    l_copyMap.addContinent(d_splitCommand[l_commandIndex + 1], Integer.parseInt(d_splitCommand[l_commandIndex + 2]));
                } catch (Exception e) {
                    l_renderer.renderError(e.toString());
                    return false;
                }
                l_commandIndex = l_commandIndex + 3;

            } else if (d_splitCommand[l_commandIndex].equals("-remove")) { //removing continent with continentID from copy map

                try {
                    l_copyMap.removeContinent(l_copyMap.getContinentID(d_splitCommand[l_commandIndex + 1]));
                } catch (Exception e) {
                    l_renderer.renderError(e.toString());
                    return false;
                }
                l_commandIndex = l_commandIndex + 2;
            }
        }
        return true;
    }


}