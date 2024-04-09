package mvc.controller.middleware.commands;

import mvc.controller.GameEngine;
import mvc.controller.middleware.mapfilemanagement.ConquestMapInterface;
import mvc.controller.middleware.mapfilemanagement.MapAdapter;
import mvc.controller.middleware.mapfilemanagement.MapFileLoader;
import mvc.controller.middleware.mapfilemanagement.MapInterface;
import mvc.controller.statepattern.Phase;
import mvc.controller.statepattern.Starting;
import mvc.controller.statepattern.mapeditor.MapEditor;
import mvc.models.LogEntryBuffer;
import mvc.models.worldmap.WorldMap;
import mvc.view.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The MapEditorCommands class represents commands available for map editing.
 * It extends the Commands class and provides functionality for various map editing operations.
 */
public class MapEditorCommands extends Commands {

    /**
     * Represents map interface.
     */
    MapInterface d_mp = new MapInterface();
    /**
     * Represents a buffer for storing log entries.
     */
    LogEntryBuffer d_logEntryBuffer = new LogEntryBuffer();

    /**
     * Represents a logger associated with a log entry buffer.
     */
    Logger d_lw = new Logger(d_logEntryBuffer);

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
        Pattern l_pattern = Pattern.compile("^editcontinent(?:(?:\\s+-add\\s+\\w+\\s+\\d+)*(?:\\s+-remove\\s+\\w+)*)*(\\s)*$|" +
                "^editcountry(?:(?:\\s+-add\\s+\\w+\\s+\\w+)*(?:\\s+-remove\\s+\\w+)*(?:\\s+-remove\\s+\\w+)*)*(\\s)*$|" +
                "^editneighbor(?:(?:\\s+-add\\s+\\w+\\s+\\w+)*(?:\\s+-remove\\s+\\w+\\s+\\w+)*)*(\\s)*$|" +
                "^showmap(\\s)*$|" +
                "^validatemap(\\s)*$|" +
                "^savemap\\s\\w+\\.map(\\s)*$|" +
                "^loadmap\\s\\w+\\.map(\\s)*$|" +
                "^editmap\\s\\w+\\.map(\\s)*$");
        Matcher l_matcher = l_pattern.matcher(d_command);
        //returning true of pattern matches and the current state is MapEditor
        return l_matcher.matches() && (p_gameEngine.getCurrentState().getClass() == MapEditor.class);
    }

    /**
     * Retrieves the name of the current phase of the game.
     *
     * @param p_gameEngine The game engine containing the current game state.
     * @return The name of the current game phase.
     */
    public String getCurrentPhase(GameEngine p_gameEngine) {
        Phase l_phase = p_gameEngine.getCurrentState();
        String l_currClass = String.valueOf(l_phase.getClass());
        int l_index = l_currClass.lastIndexOf(".");
        return l_currClass.substring(l_index + 1);
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
        //from here, we are going to call the appropriate methods depending on what the entered command is
        switch (l_command[0]) {
            case "showmap":
                d_logEntryBuffer.setString("Phase :" + d_currPhase + "\n" + " Entered Command: showmap");
                p_gameEngine.d_renderer.showMap(false);
                d_logEntryBuffer.setString("Phase :" + d_currPhase + "\n" + " Executed Command: showmap =>" + d_command);
                break;
            case "validatemap":
                d_logEntryBuffer.setString("Phase :" + d_currPhase + "\n" + " Entered Command: validatemap");
                if (p_gameEngine.d_worldmap.validateMap()) { //if the map is valid
                    p_gameEngine.d_renderer.renderMessage("Map is valid");
                    d_logEntryBuffer.setString("Phase :" + d_currPhase + "\n" + " Executed Command: validatemap");
                } else { //if the map is invalid
                    p_gameEngine.d_renderer.renderMessage("Map is not valid");
                    d_logEntryBuffer.setString("Phase :" + d_currPhase + "\n" + " Command: validatemap Not Executed: map is not valid!");
                }
                break;
            case "savemap":
                try {
                    d_logEntryBuffer.setString("Phase :" + d_currPhase + "\n" + " Entered Command: savemap => " + d_command);
                    d_mp.saveMap(p_gameEngine, l_command[1]);
                    d_logEntryBuffer.setString("Phase :" + d_currPhase + "\n" + "  Executed Command: savemap => " + d_command);
                } catch (Exception e) {
                    p_gameEngine.d_renderer.renderError("IOException : Encountered File I/O Error");
                    d_logEntryBuffer.setString("Phase :" + d_currPhase + "\n" + " Command: savemap Not Executed due to File I/O Error");
                }
                break;
            case "editmap":
                d_logEntryBuffer.setString("Phase :" + d_currPhase + "\n" + " Entered Command: editmap => " + d_command);
                editMap(p_gameEngine, d_currPhase);
                break;
            case "editcontinent":
                d_logEntryBuffer.setString("Phase :" + d_currPhase + "\n" + " Entered Command: editcontinent => " + d_command);
                if (editContinentValidator(p_gameEngine.d_worldmap)) { //if the editcontinent command is valid
                    editContinent(p_gameEngine.d_worldmap, d_currPhase);

                }
                break;
            case "editcountry":
                d_logEntryBuffer.setString("Phase :" + d_currPhase + "\n" + " Entered Command: editcountry => " + d_command);
                if (editCountryValidator(p_gameEngine.d_worldmap)) { //if the editcountry command is valid
                    editCountry(p_gameEngine.d_worldmap, d_currPhase);
                }
                break;
            case "editneighbor":
                d_logEntryBuffer.setString("Phase :" + d_currPhase + "\n" + " Entered Command: editneighbor => " + d_command);
                if (editNeighborValidator(p_gameEngine.d_worldmap)) { //if the edit neighbor command is valid
                    editNeighbor(p_gameEngine.d_worldmap, d_currPhase);
                }
                break;
            case "exit":
                d_logEntryBuffer.setString("Phase :" + d_currPhase + "\n" + " Entered Command: exit => " + d_command);
                p_gameEngine.resetMap();
                p_gameEngine.setCurrentState(new Starting(p_gameEngine)); //after exit, we need to goto the starting state
                d_logEntryBuffer.setString("Phase :" + d_currPhase + "\n" + " Executed Command: exit");
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
        String l_invalidMessage = "Invalid editcontinent command! Correct format -> editcontinent -add <continentID> <continentvalue> -remove <continentID>";
        WorldMap l_copyMap = null;
        int l_commandLength = this.d_splitCommand.length;

        try {
            l_copyMap = new WorldMap(p_worldMap);
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }

        if (l_commandLength < 3) {
            System.out.println(l_invalidMessage);
            return false;
        }

        int l_commandIndex = 1;
        while (l_commandIndex < l_commandLength) {

            if (d_splitCommand[l_commandIndex].equals("-add")) {

                if (l_commandIndex + 2 >= l_commandLength) {
                    System.out.println(l_invalidMessage);
                    return false;
                } else {
                    try { //adding continent with continent value
                        l_copyMap.addContinent(d_splitCommand[l_commandIndex + 1], Integer.parseInt(d_splitCommand[l_commandIndex + 2]));
                    } catch (Exception e) {
                        System.out.println(e);
                        System.out.println(l_invalidMessage);
                        return false;
                    }
                    l_commandIndex = l_commandIndex + 3;
                }

            } else if (d_splitCommand[l_commandIndex].equals("-remove")) {

                if (l_commandIndex + 1 >= l_commandLength) {
                    System.out.println(l_invalidMessage);
                    return false;
                } else {
                    try {
                        l_copyMap.removeContinent(l_copyMap.getContinentID(d_splitCommand[l_commandIndex + 1]));
                    } catch (Exception e) {
                        System.out.println(e);
                        System.out.println(l_invalidMessage);
                        return false;
                    }
                    l_commandIndex = l_commandIndex + 2;
                }

            } else {

                System.out.println(l_invalidMessage);
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
     * @param p_worldMap  The world map on which the editcontinent command will be applied.
     * @param p_currPhase The current phase of the game.
     */
    public void editContinent(WorldMap p_worldMap, String p_currPhase) {
        int l_commandLength = this.d_splitCommand.length;
        int l_commandIndex = 1;
        while (l_commandIndex < l_commandLength) {

            if (d_splitCommand[l_commandIndex].equals("-add")) {

                try {
                    p_worldMap.addContinent(d_splitCommand[l_commandIndex + 1], Integer.parseInt(d_splitCommand[l_commandIndex + 2]));
                    d_logEntryBuffer.setString("Phase :" + p_currPhase + "\n" + " added continent" + d_splitCommand[l_commandIndex + 1]
                            + " with bonus armies value: " + d_splitCommand[l_commandIndex + 2]);
                } catch (Exception e) {
                    d_logEntryBuffer.setString("Phase :" + p_currPhase + "\n" + " cannot add continent" + d_splitCommand[l_commandIndex + 1]
                            + " as it already exists");
                    System.out.println(e);
                }
                l_commandIndex = l_commandIndex + 3;

            } else if (d_splitCommand[l_commandIndex].equals("-remove")) {

                try {
                    p_worldMap.removeContinent(p_worldMap.getContinentID(d_splitCommand[l_commandIndex + 1]));
                    d_logEntryBuffer.setString("Phase :" + p_currPhase + "\n" + " removed continent" +
                            d_splitCommand[l_commandIndex + 1]);
                } catch (Exception e) {
                    d_logEntryBuffer.setString("Phase :" + p_currPhase + "\n" + " cannot remove continent" +
                            d_splitCommand[l_commandIndex + 1] + "it does not exist");
                    System.out.println(e);
                }
                l_commandIndex = l_commandIndex + 2;

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
        String l_invalidMessage = "Invalid editcountry command! Correct format -> editcountry -add <countryID> <continentID> -remove <countryID>";
        WorldMap l_copyMap = null;
        int l_commandLength = this.d_splitCommand.length;

        try {
            l_copyMap = new WorldMap(p_worldMap);
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }

        if (l_commandLength < 3) {
            System.out.println(l_invalidMessage);
            return false;
        }

        int l_commandIndex = 1;
        while (l_commandIndex < l_commandLength) {

            if (d_splitCommand[l_commandIndex].equals("-add")) {

                if (l_commandIndex + 2 >= l_commandLength) {
                    System.out.println(l_invalidMessage);
                    return false;
                } else {
                    try {
                        l_copyMap.addCountry(d_splitCommand[l_commandIndex + 1], p_worldMap.getContinentID(d_splitCommand[l_commandIndex + 2]));
                    } catch (Exception e) {
                        System.out.println(e);
                        System.out.println(l_invalidMessage);
                        return false;
                    }
                    l_commandIndex = l_commandIndex + 3;
                }

            } else if (d_splitCommand[l_commandIndex].equals("-remove")) {

                if (l_commandIndex + 1 >= l_commandLength) {
                    System.out.println(l_invalidMessage);
                    return false;
                } else {
                    try {
                        l_copyMap.removeCountry(p_worldMap.getCountryID(d_splitCommand[l_commandIndex + 1]));
                    } catch (Exception e) {
                        System.out.println(e);
                        System.out.println(l_invalidMessage);
                        return false;
                    }
                    l_commandIndex = l_commandIndex + 2;
                }

            } else {

                System.out.println(l_invalidMessage);
                return false;
            }

        }
        return true;
    }


    /**
     * Edits countries in the provided world map based on the editcountry command.
     *
     * @param p_worldMap  The world map in which the countries will be edited.
     * @param p_currPhase The current phase of the game.
     */
    public void editCountry(WorldMap p_worldMap, String p_currPhase) {
        int l_commandLength = this.d_splitCommand.length;


        int l_commandIndex = 1;
        while (l_commandIndex < l_commandLength) {

            if (d_splitCommand[l_commandIndex].equals("-add")) {

                try {
                    p_worldMap.addCountry(d_splitCommand[l_commandIndex + 1], p_worldMap.getContinentID(d_splitCommand[l_commandIndex + 2]));
                    d_logEntryBuffer.setString("Phase :" + p_currPhase + "\n" + " added country" +
                            d_splitCommand[l_commandIndex + 1] + " continent " + d_splitCommand[l_commandIndex + 2]);
                } catch (Exception e) {
                    d_logEntryBuffer.setString("Phase :" + p_currPhase + "\n" + " cannot add country" +
                            d_splitCommand[l_commandIndex + 1] + "due to either target continent not existing or country already existing");
                    System.out.println(e);
                    return;
                }
                l_commandIndex = l_commandIndex + 3;


            } else if (d_splitCommand[l_commandIndex].equals("-remove")) {

                try {
                    p_worldMap.removeCountry(p_worldMap.getCountryID(d_splitCommand[l_commandIndex + 1]));
                    d_logEntryBuffer.setString("Phase :" + p_currPhase + "\n" + " removed country" +
                            d_splitCommand[l_commandIndex + 1]);
                } catch (Exception e) {
                    d_logEntryBuffer.setString("Phase :" + p_currPhase + "\n" + " could not remove country" +
                            d_splitCommand[l_commandIndex + 1] + " as it does not exist");
                    System.out.println(e);
                    return;
                }
                l_commandIndex = l_commandIndex + 2;
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
        String l_invalidMessage = "Invalid editneighbor command! Correct format -> editneighbor -add <countryID> <neighborcountryID> " +
                "-remove <countryID> <neighborcountryID>";
        WorldMap l_copyMap = null;
        int l_commandLength = this.d_splitCommand.length;

        try {
            l_copyMap = new WorldMap(p_worldMap);
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }

        if (l_commandLength < 3) {
            System.out.println(l_invalidMessage);
            return false;
        }

        int l_commandIndex = 1;
        while (l_commandIndex < l_commandLength) {

            if (d_splitCommand[l_commandIndex].equals("-add")) {

                if (l_commandIndex + 2 >= l_commandLength) {
                    System.out.println(l_invalidMessage);
                    return false;
                } else {
                    try {
                        l_copyMap.addBorder(l_copyMap.getCountryID(d_splitCommand[l_commandIndex + 1]), l_copyMap.getCountryID(d_splitCommand[l_commandIndex + 2]));
                    } catch (Exception e) {
                        System.out.println(e);
                        System.out.println(l_invalidMessage);
                        return false;
                    }
                    l_commandIndex = l_commandIndex + 3;
                }

            } else if (d_splitCommand[l_commandIndex].equals("-remove")) {

                if (l_commandIndex + 2 >= l_commandLength) {
                    System.out.println(l_invalidMessage);
                    return false;
                } else {
                    try {
                        l_copyMap.removeBorder(l_copyMap.getCountryID(d_splitCommand[l_commandIndex + 1]), l_copyMap.getCountryID(d_splitCommand[l_commandIndex + 2]));
                    } catch (Exception e) {
                        System.out.println(e);
                        System.out.println(l_invalidMessage);
                        return false;
                    }
                    l_commandIndex = l_commandIndex + 3;
                }

            } else {

                System.out.println(l_invalidMessage);
                return false;
            }

        }
        return true;
    }


    /**
     * Edits the neighbors of countries in the provided world map based on the editneighbor command.
     *
     * @param p_worldMap  The world map in which the neighbors will be edited.
     * @param p_currPhase The current phase of the game.
     */
    public void editNeighbor(WorldMap p_worldMap, String p_currPhase) {
        int l_commandLength = this.d_splitCommand.length;
        int l_commandIndex = 1;
        while (l_commandIndex < l_commandLength) {

            if (d_splitCommand[l_commandIndex].equals("-add")) {

                try {
                    p_worldMap.addBorder(p_worldMap.getCountryID(d_splitCommand[l_commandIndex + 1]), p_worldMap.getCountryID(d_splitCommand[l_commandIndex + 2]));
                    d_logEntryBuffer.setString("Phase :" + p_currPhase + "\n" + "  => added border of" +
                            d_splitCommand[l_commandIndex + 2] + " to " + d_splitCommand[l_commandIndex + 1]);
                } catch (Exception e) {
                    d_logEntryBuffer.setString("Phase :" + p_currPhase + "\n" + "  => could Not add borders due to the absence of existing source/target country");
                    System.out.println(e);
                    return;
                }
                l_commandIndex = l_commandIndex + 3;


            } else if (d_splitCommand[l_commandIndex].equals("-remove")) {

                try {
                    p_worldMap.removeBorder(p_worldMap.getCountryID(d_splitCommand[l_commandIndex + 1]), p_worldMap.getCountryID(d_splitCommand[l_commandIndex + 2]));
                    d_logEntryBuffer.setString("Phase :" + p_currPhase + "\n" + "  => removed border between" +
                            d_splitCommand[l_commandIndex + 2] + " and " + d_splitCommand[l_commandIndex + 1]);
                } catch (Exception e) {
                    d_logEntryBuffer.setString("Phase :" + p_currPhase + "\n" + "  => coud not remove border between" +
                            d_splitCommand[l_commandIndex + 2] + " and " + d_splitCommand[l_commandIndex + 1] +
                            "either the source or target country does not exist.");
                    System.out.println(e);
                    return;
                }
                l_commandIndex = l_commandIndex + 3;
            }
        }
    }

    /**
     * Edits the map in the game engine based on the provided map name.
     *
     * @param p_gameEngine The game engine containing the map to be edited.
     * @param p_currPhase  The current phase of the game.
     */
    public void editMap(GameEngine p_gameEngine, String p_currPhase) {
        String l_mapName = "";
        if (this.d_splitCommand.length < 2) {
            p_gameEngine.d_renderer.renderError("Invalid command format! Correct format -> editmap <mapname>");
            return;
        }

        l_mapName = d_splitCommand[1];

        try {
            MapFileLoader l_mfl = new MapFileLoader(p_gameEngine, l_mapName);

            if (l_mfl.isConquest()) {
                d_mp = new MapAdapter(new ConquestMapInterface());
            } else {
                d_mp = new MapInterface();
            }
            p_gameEngine.d_worldmap = d_mp.loadMap(p_gameEngine, l_mfl);
            d_logEntryBuffer.setString("loaded map file " + l_mapName);
        } catch (Exception e) {
            p_gameEngine.d_renderer.renderError("FileNotFoundException : File does not exist.");
            d_logEntryBuffer.setString("map file does not exist");
            p_gameEngine.d_renderer.renderMessage("Creating file by the name : " + l_mapName);
            d_logEntryBuffer.setString("creating file " + l_mapName);
            try {
                d_mp.saveMap(p_gameEngine, l_mapName);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            editMap(p_gameEngine, p_currPhase);
        }

    }
}
