package controller.middleware.commands;

import controller.GameEngine;
import controller.MapInterface;
import controller.statepattern.Starting;
import helpers.exceptions.*;
import models.LogEntryBuffer;
import models.worldmap.WorldMap;
import view.Logger;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents commands specific to map editing.
 */
public class MapEditorCommands extends Commands {
    /**
     * Constructor for MapEditorCommands.
     *
     * @param p_command The command string.
     */
    public MapEditorCommands(String p_command) {
        super(p_command, new String[]{
                "editcontinent",
                "editcountry",
                "editneighbor",
                "showmap",
                "savemap",
                "editmap",
                "validatemap",
                "exit"
        });
    }

    /**
     * Validates the command format.
     *
     * @return True if the command is valid, false otherwise.
     */
    @Override
    public boolean validateCommand() {
        Pattern pattern = Pattern.compile("^editcontinent(?:(?:\\s+-add\\s+\\w+\\s+\\d+)*(?:\\s+-remove\\s+\\w+)*)*(\\s)*$|" +
                "^editcountry(?:(?:\\s+-add\\s+\\w+\\s+\\w+)*(?:\\s+-remove\\s+\\w+)*(?:\\s+-remove\\s+\\w+)*)*(\\s)*$|" +
                "^editneighbor(?:(?:\\s+-add\\s+\\w+\\s+\\w+)*(?:\\s+-remove\\s+\\w+\\s+\\w+)*)*(\\s)*$|" +
                "^showmap(\\s)*$|" +
                "^validatemap(\\s)*$|" +
                "^savemap\\s\\w+\\.map(\\s)*$|" +
                "^loadmap\\s\\w+\\.map(\\s)*$|" +
                "^editmap\\s\\w+\\.map(\\s)*$");
        Matcher matcher = pattern.matcher(d_command);
        return matcher.matches();
    }


    /**
     * Executes the command using the provided GameEngine.
     *
     * @param p_worldMap The GameEngine object used to execute the command.
     */
    @Override
    public void execute(GameEngine p_worldMap) {
        if (!this.validateCommandName()) {
            p_worldMap.d_renderer.renderError("InvalidCommandException : Invalid Command");
            return;
        }

        String[] l_command = d_command.trim().split("\\s+");

        switch (l_command[0]) {
            case "showmap":
                p_worldMap.d_renderer.showMap(false);
                break;
            case "validatemap":
                if (MapInterface.validateMap(p_worldMap)) p_worldMap.d_renderer.renderMessage("Map is valid");
                else p_worldMap.d_renderer.renderMessage("Map is not valid");
                break;
            case "savemap":
                try {
                    MapInterface.saveMap(p_worldMap, l_command[1]);
                } catch (IOException e) {
                    p_worldMap.d_renderer.renderError("IOException : Encountered File I/O Error");
                }
                break;
            case "editmap":
                editMap(p_worldMap);
                break;
            case "editcontinent":
                if (editContinentValidator(p_worldMap.d_worldmap)) {
                    editContinent(p_worldMap.d_worldmap);
                }
                break;
            case "editcountry":
                if (editCountryValidator(p_worldMap.d_worldmap)) {
                    editCountry(p_worldMap.d_worldmap);
                }
                break;
            case "editneighbor":
                if (editNeighborValidator(p_worldMap.d_worldmap)) {
                    editNeighbor(p_worldMap.d_worldmap);
                }
                break;
            case "exit":
                p_worldMap.resetMap();
                p_worldMap.setCurrentState(new Starting(p_worldMap));
                break;
        }
    }

    /**
     * Validates the command format for editing continents.
     *
     * @param p_worldMap The WorldMap object representing the game map.
     * @return True if the command format is valid, false otherwise.
     */
    public boolean editContinentValidator(WorldMap p_worldMap) {
        String invalidMessage = "Invalid editcontinent command! Correct format -> editcontinent -add <continentID> <continentvalue> -remove <continentID>";
        WorldMap l_copyMap = null;
        int l_commandLength = this.splitCommand.length;

        try {
            l_copyMap = new WorldMap(p_worldMap);
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }

        if (l_commandLength < 3) {
            System.out.println(invalidMessage);
            return false;
        }

        int commandIndex = 1;
        while (commandIndex < l_commandLength) {

            if (splitCommand[commandIndex].equals("-add")) {

                if (commandIndex + 2 >= l_commandLength) {
                    System.out.println(invalidMessage);
                    return false;
                } else {
                    try {
                        l_copyMap.addContinent(splitCommand[commandIndex + 1], Integer.parseInt(splitCommand[commandIndex + 2]));
                    } catch (Exception e) {
                        System.out.println(e);
                        System.out.println(invalidMessage);
                        return false;
                    }
                    commandIndex = commandIndex + 3;
                }

            } else if (splitCommand[commandIndex].equals("-remove")) {

                if (commandIndex + 1 >= l_commandLength) {
                    System.out.println(invalidMessage);
                    return false;
                } else {
                    try {
                        l_copyMap.removeContinent(l_copyMap.getContinentID(splitCommand[commandIndex + 1]));
                    } catch (Exception e) {
                        System.out.println(e);
                        System.out.println(invalidMessage);
                        return false;
                    }
                    commandIndex = commandIndex + 2;
                }

            } else {

                System.out.println(invalidMessage);
                return false;
            }

        }
        return true;
    }


    /**
     * Edits the continents of the game map based on the provided command.
     *
     * @param p_worldMap The WorldMap object representing the game map.
     */
    public void editContinent(WorldMap p_worldMap) {

        int l_commandLength = this.splitCommand.length;
        int l_commandIndex = 1;
        while (l_commandIndex < l_commandLength) {

            if (splitCommand[l_commandIndex].equals("-add")) {

                try {
                    p_worldMap.addContinent(splitCommand[l_commandIndex + 1], Integer.parseInt(splitCommand[l_commandIndex + 2]));
                } catch (Exception e) {
                    System.out.println(e);
                }
                l_commandIndex = l_commandIndex + 3;

            } else if (splitCommand[l_commandIndex].equals("-remove")) {

                try {
                    p_worldMap.removeContinent(p_worldMap.getContinentID(splitCommand[l_commandIndex + 1]));
                } catch (Exception e) {
                    System.out.println(e);
                }
                l_commandIndex = l_commandIndex + 2;

            }
        }

    }

    /**
     * Validates the command format for editing countries.
     *
     * @param p_worldMap The WorldMap object representing the game map.
     * @return True if the command format is valid, false otherwise.
     */
    public boolean editCountryValidator(WorldMap p_worldMap) {
        String invalidMessage = "Invalid editcountry command! Correct format -> editcountry -add <countryID> <continentID> -remove <countryID>";
        WorldMap copyMap = null;
        int l_commandLength = this.splitCommand.length;

        try {
            copyMap = new WorldMap(p_worldMap);
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }

        if (l_commandLength < 3) {
            System.out.println(invalidMessage);
            return false;
        }

        int l_commandIndex = 1;
        while (l_commandIndex < l_commandLength) {

            if (splitCommand[l_commandIndex].equals("-add")) {

                if (l_commandIndex + 2 >= l_commandLength) {
                    System.out.println(invalidMessage);
                    return false;
                } else {
                    try {
                        copyMap.addCountry(splitCommand[l_commandIndex + 1], p_worldMap.getContinentID(splitCommand[l_commandIndex + 2]), null);
                    } catch (Exception e) {
                        System.out.println(e);
                        System.out.println(invalidMessage);
                        return false;
                    }
                    l_commandIndex = l_commandIndex + 3;
                }

            } else if (splitCommand[l_commandIndex].equals("-remove")) {

                if (l_commandIndex + 1 >= l_commandLength) {
                    System.out.println(invalidMessage);
                    return false;
                } else {
                    try {
                        copyMap.removeCountry(p_worldMap.getCountryID(splitCommand[l_commandIndex + 1]));
                    } catch (Exception e) {
                        System.out.println(e);
                        System.out.println(invalidMessage);
                        return false;
                    }
                    l_commandIndex = l_commandIndex + 2;
                }

            } else {

                System.out.println(invalidMessage);
                return false;
            }

        }
        return true;
    }

    /**
     * Edits the countries of the game map based on the provided command.
     *
     * @param p_worldMap The WorldMap object representing the game map.
     */
    public void editCountry(WorldMap p_worldMap) {
        int l_commandLength = this.splitCommand.length;


        int l_commandIndex = 1;
        while (l_commandIndex < l_commandLength) {

            if (splitCommand[l_commandIndex].equals("-add")) {

                try {
                    p_worldMap.addCountry(splitCommand[l_commandIndex + 1], p_worldMap.getContinentID(splitCommand[l_commandIndex + 2]), null);
                } catch (Exception e) {
                    System.out.println(e);
                    return;
                }
                l_commandIndex = l_commandIndex + 3;


            } else if (splitCommand[l_commandIndex].equals("-remove")) {

                try {
                    p_worldMap.removeCountry(p_worldMap.getCountryID(splitCommand[l_commandIndex + 1]));
                } catch (Exception e) {
                    System.out.println(e);
                    return;
                }
                l_commandIndex = l_commandIndex + 2;
            }
        }
    }

    /**
     * Validates the command format for editing country neighbors.
     *
     * @param p_worldMap The WorldMap object representing the game map.
     * @return True if the command format is valid, false otherwise.
     */
    public boolean editNeighborValidator(WorldMap p_worldMap) {
        String invalidMessage = "Invalid editneighbor command! Correct format -> editneighbor -add <countryID> <neighborcountryID> " +
                "-remove <countryID> <neighborcountryID>";
        WorldMap copyMap = null;
        int l_commandLength = this.splitCommand.length;

        try {
            copyMap = new WorldMap(p_worldMap);
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }

        if (l_commandLength < 3) {
            System.out.println(invalidMessage);
            return false;
        }

        int commandIndex = 1;
        while (commandIndex < l_commandLength) {

            if (splitCommand[commandIndex].equals("-add")) {

                if (commandIndex + 2 >= l_commandLength) {
                    System.out.println(invalidMessage);
                    return false;
                } else {
                    try {
                        copyMap.addBorder(copyMap.getCountryID(splitCommand[commandIndex+1]),copyMap.getCountryID(splitCommand[commandIndex+2]));
                    } catch (Exception e) {
                        System.out.println(e);
                        System.out.println(invalidMessage);
                        return false;
                    }
                    commandIndex = commandIndex + 3;
                }

            } else if (splitCommand[commandIndex].equals("-remove")) {

                if (commandIndex + 2 >= l_commandLength) {
                    System.out.println(invalidMessage);
                    return false;
                } else {
                    try {
                        copyMap.removeBorder(copyMap.getCountryID(splitCommand[commandIndex+1]),copyMap.getCountryID(splitCommand[commandIndex+2]));
                    } catch (Exception e) {
                        System.out.println(e);
                        System.out.println(invalidMessage);
                        return false;
                    }
                    commandIndex = commandIndex + 3;
                }

            } else {

                System.out.println(invalidMessage);
                return false;
            }

        }
        return true;
    }


    /**
     * Edits the neighbors of countries on the game map based on the provided command.
     *
     * @param p_worldMap The WorldMap object representing the game map.
     */
    public void editNeighbor(WorldMap p_worldMap) {
        int commandLength = this.splitCommand.length;
        int commandIndex = 1;
        while (commandIndex < commandLength) {

            if (splitCommand[commandIndex].equals("-add")) {

                try {
                    p_worldMap.addBorder(p_worldMap.getCountryID(splitCommand[commandIndex+1]),p_worldMap.getCountryID(splitCommand[commandIndex+2]));
                } catch (Exception e) {
                    System.out.println(e);
                    return;
                }
                commandIndex = commandIndex + 3;


            } else if (splitCommand[commandIndex].equals("-remove")) {

                try {
                    p_worldMap.removeBorder(p_worldMap.getCountryID(splitCommand[commandIndex+1]),p_worldMap.getCountryID(splitCommand[commandIndex+2]));
                } catch (Exception e) {
                    System.out.println(e);
                    return;
                }
                commandIndex = commandIndex + 3;
            }
        }
    }


    /**
     * Edits the game map based on the provided command to load, save, or modify the map.
     *
     * @param p_gameEngine The GameEngine object managing the game.
     */
    public void editMap(GameEngine p_gameEngine) {
        String mapName = "";
        if (this.splitCommand.length < 2) {
            p_gameEngine.d_renderer.renderError("Invalid command format! Correct format -> editmap <mapname>");
            return;
        }

        mapName = splitCommand[1];

        try {
            MapInterface.loadMap2(p_gameEngine, mapName);
        }
        catch (FileNotFoundException e) {
            p_gameEngine.d_renderer.renderError("FileNotFoundException : File does not exist.");
            p_gameEngine.d_renderer.renderMessage("Creating file by the name : " + mapName);
            try {
                MapInterface.saveMap(p_gameEngine, mapName);
            }
            catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            editMap(p_gameEngine);
        }
        catch (NumberFormatException e) {
            p_gameEngine.d_renderer.renderError("NumberFormatException : File has incorrect formatting.");
        }
        catch (ContinentAlreadyExistsException | ContinentDoesNotExistException |
                 DuplicateCountryException | CountryDoesNotExistException e) {
            p_gameEngine.d_renderer.renderError("InvalidMapException : Map is disjoint or incorrect.");
        }
    }

}
