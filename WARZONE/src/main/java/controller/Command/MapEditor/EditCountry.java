package controller.Command.MapEditor;

import controller.Command.Command;
import controller.GameEngine;
import controller.statepattern.MapEditor;
import models.worldmap.WorldMap;
import view.TerminalRenderer;

import java.util.regex.Pattern;

/**
 * EditCountry represents a command to edit countries in the game.
 */
public class EditCountry extends Command {

    /**
     * Constructs an EditCountry command with specified input and game engine.
     *
     * @param p_input The input string for the command.
     * @param p_ge    The game engine to operate on.
     */
    public EditCountry(String p_input, GameEngine p_ge) {
        super(p_input, p_ge);
        this.d_validCommandFormat = "editcountry -add <countryID> <continentID> -remove <countryID>";
        this.d_commandPattern = Pattern.compile("^editcountry(?:(?:\\s+-add\\s+\\w+\\s+\\w+)*(?:\\s+-remove\\s+\\w+)*(?:\\s+-remove\\s+\\w+)*)*(\\s)*$");
        this.d_validPhases = new Class[1];
        this.d_validPhases[0] = MapEditor.class;
    }

    /**
     * Executes the EditCountry command, adding or removing countries from the map.
     */
    @Override
    public void execute() {

        int l_commandLength = this.d_splitCommand.length;
        WorldMap l_worldMap = this.d_ge.d_worldmap;

        int l_commandIndex = 1;
        while (l_commandIndex < l_commandLength) {

            if (d_splitCommand[l_commandIndex].equals("-add")) { //adding country with countryID and continent with continentID given by user after add option in the world map.

                try {
                    l_worldMap.addCountry(d_splitCommand[l_commandIndex + 1], l_worldMap.getContinentID(d_splitCommand[l_commandIndex + 2]));
                } catch (Exception e) {
                    System.out.println(e);
                    return;
                }
                l_commandIndex = l_commandIndex + 3;


            } else if (d_splitCommand[l_commandIndex].equals("-remove")) { //removing the country with given countryID from the world map

                try {
                    l_worldMap.removeCountry(l_worldMap.getCountryID(d_splitCommand[l_commandIndex + 1]));
                } catch (Exception e) {
                    System.out.println(e);
                    return;
                }
                l_commandIndex = l_commandIndex + 2;
            }
        }
    }

    /**
     * Validates the logic of the EditCountry command.
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

            if (d_splitCommand[l_commandIndex].equals("-add")) { //adding country with countryID and continent with continentID given by user after add option in the copy map.

                try {
                    l_copyMap.addCountry(d_splitCommand[l_commandIndex + 1], this.d_ge.d_worldmap.getContinentID(d_splitCommand[l_commandIndex + 2]));
                } catch (Exception e) {
                    l_renderer.renderError(e.toString());
                    return false;
                }
                l_commandIndex = l_commandIndex + 3;


            } else if (d_splitCommand[l_commandIndex].equals("-remove")) { //removing the country with given countryID from the copy map


                try {
                    l_copyMap.removeCountry(d_splitCommand[l_commandIndex + 1]);
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
