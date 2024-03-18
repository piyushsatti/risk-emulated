package controller.middleware.commands;

import controller.GameEngine;
import controller.MapInterface;
import controller.statepattern.MapEditor;
import controller.statepattern.Phase;
import controller.statepattern.Starting;
import helpers.exceptions.*;
import models.LogEntryBuffer;
import models.worldmap.WorldMap;
import view.Logger;
import view.TerminalRenderer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The MapEditorCommands class represents commands available for map editing.
 * It extends the Commands class and provides functionality for various map editing operations.
 */
public class MapEditorCommands extends Commands {
    LogEntryBuffer logEntryBuffer = new LogEntryBuffer();
    Logger lw = new Logger(logEntryBuffer);

    /**
     * Constructs a new MapEditorCommands object with the given command.
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
     * Validates the command for map editing based on the current game state.
     *
     * @param p_gameEngine The game engine containing the current game state.
     * @return True if the command is valid according to the game rules and current state, otherwise false.
     */
    public boolean validateCommand(GameEngine p_gameEngine) {
        Pattern pattern = Pattern.compile("^editcontinent(?:(?:\\s+-add\\s+\\w+\\s+\\d+)*(?:\\s+-remove\\s+\\w+)*)*(\\s)*$|" +
                "^editcountry(?:(?:\\s+-add\\s+\\w+\\s+\\w+)*(?:\\s+-remove\\s+\\w+)*(?:\\s+-remove\\s+\\w+)*)*(\\s)*$|" +
                "^editneighbor(?:(?:\\s+-add\\s+\\w+\\s+\\w+)*(?:\\s+-remove\\s+\\w+\\s+\\w+)*)*(\\s)*$|" +
                "^showmap(\\s)*$|" +
                "^validatemap(\\s)*$|" +
                "^savemap\\s\\w+\\.map(\\s)*$|" +
                "^loadmap\\s\\w+\\.map(\\s)*$|" +
                "^editmap\\s\\w+\\.map(\\s)*$");
        Matcher matcher = pattern.matcher(d_command);
        return matcher.matches() && (p_gameEngine.getCurrentState().getClass() == MapEditor.class);
    }
    /**
     * Retrieves the name of the current phase of the game.
     *
     * @param p_gameEngine The game engine containing the current game state.
     * @return The name of the current game phase.
     */
    public String getCurrentPhase(GameEngine p_gameEngine)
    {
        Phase phase = p_gameEngine.getCurrentState();
        String l_currClass = String.valueOf(phase.getClass());
        int l_index = l_currClass.lastIndexOf(".");
        return l_currClass.substring(l_index+1);
    }

    /**
     * Executes the command issued by the user in the game engine.
     *
     * @param p_gameEngine The game engine containing the game state and resources.
     */
    @Override
    public void execute(GameEngine p_gameEngine) {
        if (!this.validateCommandName()) {
            p_gameEngine.d_renderer.renderError("InvalidCommandException : Invalid Command");
            return;
        }

        String[] l_command = d_command.trim().split("\\s+");
        String d_currPhase = getCurrentPhase(p_gameEngine);

        switch (l_command[0]) {
            case "showmap":
                logEntryBuffer.setString("Phase :"+d_currPhase+"\n"+ " Entered Command: showmap");
                p_gameEngine.d_renderer.showMap(false);
                logEntryBuffer.setString("Phase :"+d_currPhase+"\n"+ " Executed Command: showmap =>"+d_command);
                break;
            case "validatemap":
                logEntryBuffer.setString("Phase :"+d_currPhase+"\n"+ " Entered Command: validatemap");
                if (MapInterface.validateMap(p_gameEngine))
                {
                    p_gameEngine.d_renderer.renderMessage("Map is valid");
                    logEntryBuffer.setString("Phase :"+d_currPhase+"\n"+ " Executed Command: validatemap");
                }
                else
                {
                    p_gameEngine.d_renderer.renderMessage("Map is not valid");
                    logEntryBuffer.setString("Phase :"+d_currPhase+"\n"+ " Command: validatemap Not Executed: map is not valid!");
                }
                break;
            case "savemap":
                try {
                    logEntryBuffer.setString("Phase :"+d_currPhase+"\n"+ " Entered Command: savemap => "+d_command);
                    MapInterface.saveMap(p_gameEngine, l_command[1]);
                    logEntryBuffer.setString("Phase :"+d_currPhase+"\n"+ "  Executed Command: savemap => "+d_command);
                } catch (IOException e) {
                    p_gameEngine.d_renderer.renderError("IOException : Encountered File I/O Error");
                    logEntryBuffer.setString("Phase :"+d_currPhase+"\n"+ " Command: savemap Not Executed due to File I/O Error");
                }
                break;
            case "editmap":
                logEntryBuffer.setString("Phase :"+d_currPhase+"\n"+ " Entered Command: editmap => "+d_command);
                editMap(p_gameEngine,d_currPhase);
                break;
            case "editcontinent":
                logEntryBuffer.setString("Phase :"+d_currPhase+"\n"+ " Entered Command: editcontinent => "+d_command);
                if (editContinentValidator(p_gameEngine.d_worldmap)) {
                    editContinent(p_gameEngine.d_worldmap,d_currPhase);

                }
                break;
            case "editcountry":
                logEntryBuffer.setString("Phase :"+d_currPhase+"\n"+ " Entered Command: editcountry => "+d_command);
                if (editCountryValidator(p_gameEngine.d_worldmap)) {
                    editCountry(p_gameEngine.d_worldmap,d_currPhase);
                }
                break;
            case "editneighbor":
                logEntryBuffer.setString("Phase :"+d_currPhase+"\n"+ " Entered Command: editneighbor => "+d_command);
                if (editNeighborValidator(p_gameEngine.d_worldmap)) {
                    editNeighbor(p_gameEngine.d_worldmap,d_currPhase);
                }
                break;
            case "exit":
                logEntryBuffer.setString("Phase :"+d_currPhase+"\n"+ " Entered Command: exit => "+d_command);
                p_gameEngine.resetMap();
                p_gameEngine.setCurrentState(new Starting(p_gameEngine));
                logEntryBuffer.setString("Phase :"+d_currPhase+"\n"+ " Executed Command: exit");
                break;
        }
    }

    /**
     * Validates and executes the editcontinent command to add or remove continents from the world map.
     *
     * @param p_worldMap The original world map object to be edited.
     * @return true if the editcontinent command is successfully validated and executed; false otherwise.
     */
    public boolean editContinentValidator(WorldMap p_worldMap) {
        String invalidMessage = "Invalid editcontinent command! Correct format -> editcontinent -add <continentID> <continentvalue> -remove <continentID>";
        WorldMap copyMap = null;
        int commandLength = this.splitCommand.length;

        try {
            copyMap = new WorldMap(p_worldMap);
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }

        if (commandLength < 3) {
            System.out.println(invalidMessage);
            return false;
        }

        int commandIndex = 1;
        while (commandIndex < commandLength) {

            if (splitCommand[commandIndex].equals("-add")) {

                if (commandIndex + 2 >= commandLength) {
                    System.out.println(invalidMessage);
                    return false;
                } else {
                    try {
                        copyMap.addContinent(splitCommand[commandIndex + 1], Integer.parseInt(splitCommand[commandIndex + 2]));
                    } catch (Exception e) {
                        System.out.println(e);
                        System.out.println(invalidMessage);
                        return false;
                    }
                    commandIndex = commandIndex + 3;
                }

            } else if (splitCommand[commandIndex].equals("-remove")) {

                if (commandIndex + 1 >= commandLength) {
                    System.out.println(invalidMessage);
                    return false;
                } else {
                    try {
                        copyMap.removeContinent(copyMap.getContinentID(splitCommand[commandIndex + 1]));
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
     * Validates the editcontinent command provided by the user.
     * The command should be in the format: editcontinent -add ontinentID continentvalue -remove continentID.
     * This method checks the correctness of the command and ensures that it is valid for modifying continents in the world map.
     *
     * @param p_worldMap The world map on which the editcontinent command will be applied.
     */
    public void editContinent(WorldMap p_worldMap,String p_currPhase) {
        int commandLength = this.splitCommand.length;
        int commandIndex = 1;
        while (commandIndex < commandLength) {

            if (splitCommand[commandIndex].equals("-add")) {

                try {
                    p_worldMap.addContinent(splitCommand[commandIndex + 1], Integer.parseInt(splitCommand[commandIndex + 2]));
                    logEntryBuffer.setString("Phase :"+p_currPhase+"\n"+ " added continent"+splitCommand[commandIndex + 1]
                            +" with bonus armies value: "+splitCommand[commandIndex + 2]);
                } catch (Exception e) {
                    logEntryBuffer.setString("Phase :"+p_currPhase+"\n"+ " cannot add continent"+splitCommand[commandIndex + 1]
                            +" as it already exists");
                    System.out.println(e);
                }
                commandIndex = commandIndex + 3;

            } else if (splitCommand[commandIndex].equals("-remove")) {

                try {
                    p_worldMap.removeContinent(p_worldMap.getContinentID(splitCommand[commandIndex + 1]));
                    logEntryBuffer.setString("Phase :"+p_currPhase+"\n"+ " removed continent"+
                            splitCommand[commandIndex + 1]);
                } catch (Exception e) {
                    logEntryBuffer.setString("Phase :"+p_currPhase+"\n"+ " cannot remove continent"+
                            splitCommand[commandIndex + 1]+"it does not exist");
                    System.out.println(e);
                }
                commandIndex = commandIndex + 2;

            }
        }

    }


    /**
     * Validates the editcountry command for editing countries in the map.
     *
     * @param p_worldMap The original world map on which the command will be applied.
     * @return True if the editcountry command is valid and can be executed, false otherwise.
     */
    public boolean editCountryValidator(WorldMap p_worldMap) {
        String invalidMessage = "Invalid editcountry command! Correct format -> editcountry -add <countryID> <continentID> -remove <countryID>";
        WorldMap copyMap = null;
        int commandLength = this.splitCommand.length;

        try {
            copyMap = new WorldMap(p_worldMap);
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }

        if (commandLength < 3) {
            System.out.println(invalidMessage);
            return false;
        }

        int commandIndex = 1;
        while (commandIndex < commandLength) {

            if (splitCommand[commandIndex].equals("-add")) {

                if (commandIndex + 2 >= commandLength) {
                    System.out.println(invalidMessage);
                    return false;
                } else {
                    try {
                        copyMap.addCountry(splitCommand[commandIndex + 1], p_worldMap.getContinentID(splitCommand[commandIndex + 2]), null);
                    } catch (Exception e) {
                        System.out.println(e);
                        System.out.println(invalidMessage);
                        return false;
                    }
                    commandIndex = commandIndex + 3;
                }

            } else if (splitCommand[commandIndex].equals("-remove")) {

                if (commandIndex + 1 >= commandLength) {
                    System.out.println(invalidMessage);
                    return false;
                } else {
                    try {
                        copyMap.removeCountry(p_worldMap.getCountryID(splitCommand[commandIndex + 1]));
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
     * Edits countries in the provided world map based on the editcountry command.
     *
     * @param p_worldMap          The world map in which the countries will be edited.
     * @param p_currPhase The current phase of the game.
     */
    public void editCountry(WorldMap p_worldMap,String p_currPhase) {
        int commandLength = this.splitCommand.length;


        int commandIndex = 1;
        while (commandIndex < commandLength) {

            if (splitCommand[commandIndex].equals("-add")) {

                try {
                    p_worldMap.addCountry(splitCommand[commandIndex + 1], p_worldMap.getContinentID(splitCommand[commandIndex + 2]), null);
                    logEntryBuffer.setString("Phase :"+p_currPhase+"\n"+ " added country"+
                            splitCommand[commandIndex + 1]+" continent "+splitCommand[commandIndex + 2]);
                } catch (Exception e) {
                    logEntryBuffer.setString("Phase :"+p_currPhase+"\n"+ " cannot add country"+
                            splitCommand[commandIndex + 1]+"due to either target continent not existing or country already existing");
                    System.out.println(e);
                    return;
                }
                commandIndex = commandIndex + 3;


            } else if (splitCommand[commandIndex].equals("-remove")) {

                try {
                    p_worldMap.removeCountry(p_worldMap.getCountryID(splitCommand[commandIndex + 1]));
                    logEntryBuffer.setString("Phase :"+p_currPhase+"\n"+ " removed country"+
                            splitCommand[commandIndex + 1]);
                } catch (Exception e) {
                    logEntryBuffer.setString("Phase :"+p_currPhase+"\n"+ " could not remove country"+
                            splitCommand[commandIndex + 1]+" as it does not exist");
                    System.out.println(e);
                    return;
                }
                commandIndex = commandIndex + 2;
            }
        }
    }

    /**
     * Validates the editneighbor command for editing neighbors of countries in the map.
     *
     * @param p_worldMap The original world map on which the command will be applied.
     * @return True if the editneighbor command is valid and can be executed, false otherwise.
     */
    public boolean editNeighborValidator(WorldMap p_worldMap) {
        String invalidMessage = "Invalid editneighbor command! Correct format -> editneighbor -add <countryID> <neighborcountryID> " +
                "-remove <countryID> <neighborcountryID>";
        WorldMap copyMap = null;
        int commandLength = this.splitCommand.length;

        try {
            copyMap = new WorldMap(p_worldMap);
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }

        if (commandLength < 3) {
            System.out.println(invalidMessage);
            return false;
        }

        int commandIndex = 1;
        while (commandIndex < commandLength) {

            if (splitCommand[commandIndex].equals("-add")) {

                if (commandIndex + 2 >= commandLength) {
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

                if (commandIndex + 2 >= commandLength) {
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
     * Edits the neighbors of countries in the provided world map based on the editneighbor command.
     *
     * @param p_worldMap          The world map in which the neighbors will be edited.
     * @param p_currPhase The current phase of the game.
     */
    public void editNeighbor(WorldMap p_worldMap,String p_currPhase) {
        int commandLength = this.splitCommand.length;


        int commandIndex = 1;
        while (commandIndex < commandLength) {

            if (splitCommand[commandIndex].equals("-add")) {

                try {
                    p_worldMap.addBorder(p_worldMap.getCountryID(splitCommand[commandIndex+1]),p_worldMap.getCountryID(splitCommand[commandIndex+2]));
                    logEntryBuffer.setString("Phase :"+p_currPhase+"\n"+ "  => added border of"+
                            splitCommand[commandIndex+2] +" to "+ splitCommand[commandIndex+1] );
                } catch (Exception e) {
                    logEntryBuffer.setString("Phase :"+p_currPhase+"\n"+ "  => could Not add borders due to the absence of existing source/target country" );
                    System.out.println(e);
                    return;
                }
                commandIndex = commandIndex + 3;


            } else if (splitCommand[commandIndex].equals("-remove")) {

                try {
                    p_worldMap.removeBorder(p_worldMap.getCountryID(splitCommand[commandIndex+1]),p_worldMap.getCountryID(splitCommand[commandIndex+2]));
                    logEntryBuffer.setString("Phase :"+p_currPhase+"\n"+ "  => removed border between"+
                            splitCommand[commandIndex+2] +" and "+ splitCommand[commandIndex+1] );
                } catch (Exception e) {
                    logEntryBuffer.setString("Phase :"+p_currPhase+"\n"+ "  => coud not remove border between"+
                            splitCommand[commandIndex+2] +" and "+ splitCommand[commandIndex+1] +
                            "either the source or target country does not exist.");
                    System.out.println(e);
                    return;
                }
                commandIndex = commandIndex + 3;
            }
        }
    }

    /**
     * Edits the map in the game engine based on the provided map name.
     *
     * @param p_gameEngine          The game engine containing the map to be edited.
     * @param p_currPhase The current phase of the game.
     */
    public void editMap(GameEngine p_gameEngine,String p_currPhase) {
        String mapName = "";
        if (this.splitCommand.length < 2) {
            p_gameEngine.d_renderer.renderError("Invalid command format! Correct format -> editmap <mapname>");
            return;
        }

        mapName = splitCommand[1];

        try {
            MapInterface.loadMap2(p_gameEngine, mapName);
            logEntryBuffer.setString("loaded map file "+ mapName);
        } catch (FileNotFoundException e) {
            p_gameEngine.d_renderer.renderError("FileNotFoundException : File does not exist.");
            logEntryBuffer.setString("map file does not exist");
            p_gameEngine.d_renderer.renderMessage("Creating file by the name : " + mapName);
            logEntryBuffer.setString("creating file "+ mapName);
            try {
                MapInterface.saveMap(p_gameEngine, mapName);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            editMap(p_gameEngine,p_currPhase);
        } catch (NumberFormatException e) {
            p_gameEngine.d_renderer.renderError("NumberFormatException : File has incorrect formatting.");
            logEntryBuffer.setString("Phase :"+p_currPhase+"\n"+ " Entered Command Not Executed: editmap => "+ d_command+ "  due to Incorrect Formatting");
        } catch (ContinentAlreadyExistsException | ContinentDoesNotExistException |
                 DuplicateCountryException | CountryDoesNotExistException e) {
            p_gameEngine.d_renderer.renderError("InvalidMapException : Map is disjoint or incorrect.");
            logEntryBuffer.setString("Phase :"+p_currPhase+"\n"+ " Entered Command Not Executed: editmap => "+ d_command+ "  as Map is disjoint or incorrect");
        }
    }

}
