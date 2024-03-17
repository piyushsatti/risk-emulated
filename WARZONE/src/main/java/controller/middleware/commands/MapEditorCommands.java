package controller.middleware.commands;

import controller.GameEngine;
import controller.MapInterface;
import controller.statepattern.MapEditor;
import controller.statepattern.Starting;
import helpers.exceptions.*;
import models.LogEntryBuffer;
import models.worldmap.WorldMap;
import view.Logger;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MapEditorCommands extends Commands {
    LogEntryBuffer logEntryBuffer = new LogEntryBuffer();
    Logger lw = new Logger(logEntryBuffer);
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
        return matcher.matches() && (p_gameEngine.getCurerentState().getClass() == MapEditor.class);
    }

    @Override
    public void execute(GameEngine ge) {
        if (!this.validateCommandName()) {
            ge.d_renderer.renderError("InvalidCommandException : Invalid Command");
            return;
        }

        String[] l_command = d_command.trim().split("\\s+");

        switch (l_command[0]) {
            case "showmap":
                logEntryBuffer.setString("Map Editor Phase: \n"+ " Entered Command: showmap");
                ge.d_renderer.showMap(false);
                logEntryBuffer.setString("Map Editor Phase: \n"+ " Executed Command: showmap =>"+d_command);
                break;
            case "validatemap":
                logEntryBuffer.setString("Map Editor Phase: \n"+ " Entered Command: validatemap");
                if (MapInterface.validateMap(ge))
                {
                    ge.d_renderer.renderMessage("Map is valid");
                    logEntryBuffer.setString("Map Editor Phase: \n"+ " Executed Command: validatemap");
                }
                else
                {
                    ge.d_renderer.renderMessage("Map is not valid");
                    logEntryBuffer.setString("Map Editor Phase: \n"+ " Command: validatemap Not Executed: map is not valid!");
                }
                break;
            case "savemap":
                try {
                    logEntryBuffer.setString("Map Editor Phase: \n"+ " Entered Command: savemap => "+d_command);
                    MapInterface.saveMap(ge, l_command[1]);
                    logEntryBuffer.setString("Map Editor Phase: \n"+ "  Executed Command: savemap => "+d_command);
                } catch (IOException e) {
                    ge.d_renderer.renderError("IOException : Encountered File I/O Error");
                    logEntryBuffer.setString("Map Editor Phase: \n"+ " Command: savemap Not Executed due to File I/O Error");
                }
                break;
            case "editmap":
                logEntryBuffer.setString("Map Editor Phase: \n"+ " Entered Command: editmap => "+d_command);
                editMap(ge);
                break;
            case "editcontinent":
                logEntryBuffer.setString("Map Editor Phase: \n"+ " Entered Command: editcontinent => "+d_command);
                if (editContinentValidator(ge.d_worldmap)) {
                    editContinent(ge.d_worldmap);

                }
                break;
            case "editcountry":
                logEntryBuffer.setString("Map Editor Phase: \n"+ " Entered Command: editcountry => "+d_command);
                if (editCountryValidator(ge.d_worldmap)) {
                    editCountry(ge.d_worldmap);
                }
                break;
            case "editneighbor":
                logEntryBuffer.setString("Map Editor Phase: \n"+ " Entered Command: editneighbor => "+d_command);
                if (editNeighborValidator(ge.d_worldmap)) {
                    editNeighbor(ge.d_worldmap);
                }
                break;
            case "exit":
                logEntryBuffer.setString("Map Editor Phase: \n"+ " Entered Command: exit => "+d_command);
                ge.resetMap();
                ge.setCurrentState(new Starting(ge));
                logEntryBuffer.setString("Map Editor Phase: \n"+ " Executed Command: exit");
                break;
        }
    }

    public boolean editContinentValidator(WorldMap wm) {
        String invalidMessage = "Invalid editcontinent command! Correct format -> editcontinent -add <continentID> <continentvalue> -remove <continentID>";
        WorldMap copyMap = null;
        int commandLength = this.splitCommand.length;

        try {
            copyMap = new WorldMap(wm);
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

    public void editContinent(WorldMap wm) {

        int commandLength = this.splitCommand.length;
        int commandIndex = 1;
        while (commandIndex < commandLength) {

            if (splitCommand[commandIndex].equals("-add")) {

                try {
                    wm.addContinent(splitCommand[commandIndex + 1], Integer.parseInt(splitCommand[commandIndex + 2]));
                    logEntryBuffer.setString("Map Editor Phase: \n"+ " added continent"+splitCommand[commandIndex + 1]
                            +" with bonus armies value: "+splitCommand[commandIndex + 2]);
                } catch (Exception e) {
                    logEntryBuffer.setString("Map Editor Phase: \n"+ " cannot add continent"+splitCommand[commandIndex + 1]
                            +" as it already exists");
                    System.out.println(e);
                }
                commandIndex = commandIndex + 3;

            } else if (splitCommand[commandIndex].equals("-remove")) {

                try {
                    wm.removeContinent(wm.getContinentID(splitCommand[commandIndex + 1]));
                    logEntryBuffer.setString("Map Editor Phase: \n"+ " removed continent"+
                            splitCommand[commandIndex + 1]);
                } catch (Exception e) {
                    logEntryBuffer.setString("Map Editor Phase: \n"+ " cannot remove continent"+
                            splitCommand[commandIndex + 1]+"it does not exist");
                    System.out.println(e);
                }
                commandIndex = commandIndex + 2;

            }
        }

    }


    public boolean editCountryValidator(WorldMap wm) {
        String invalidMessage = "Invalid editcountry command! Correct format -> editcountry -add <countryID> <continentID> -remove <countryID>";
        WorldMap copyMap = null;
        int commandLength = this.splitCommand.length;

        try {
            copyMap = new WorldMap(wm);
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
                        copyMap.addCountry(splitCommand[commandIndex + 1], wm.getContinentID(splitCommand[commandIndex + 2]), null);
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
                        copyMap.removeCountry(wm.getCountryID(splitCommand[commandIndex + 1]));
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


    public void editCountry(WorldMap wm) {
        int commandLength = this.splitCommand.length;


        int commandIndex = 1;
        while (commandIndex < commandLength) {

            if (splitCommand[commandIndex].equals("-add")) {

                try {
                    wm.addCountry(splitCommand[commandIndex + 1], wm.getContinentID(splitCommand[commandIndex + 2]), null);
                    logEntryBuffer.setString("Map Editor Phase: \n"+ " added country"+
                            splitCommand[commandIndex + 1]+" continent "+splitCommand[commandIndex + 2]);
                } catch (Exception e) {
                    logEntryBuffer.setString("Map Editor Phase: \n"+ " cannot add country"+
                            splitCommand[commandIndex + 1]+"due to either target continent not existing or country already existing");
                    System.out.println(e);
                    return;
                }
                commandIndex = commandIndex + 3;


            } else if (splitCommand[commandIndex].equals("-remove")) {

                try {
                    wm.removeCountry(wm.getCountryID(splitCommand[commandIndex + 1]));
                    logEntryBuffer.setString("Map Editor Phase: \n"+ " removed country"+
                            splitCommand[commandIndex + 1]);
                } catch (Exception e) {
                    logEntryBuffer.setString("Map Editor Phase: \n"+ " could not remove country"+
                            splitCommand[commandIndex + 1]+" as it does not exist");
                    System.out.println(e);
                    return;
                }
                commandIndex = commandIndex + 2;
            }
        }
    }

    public boolean editNeighborValidator(WorldMap wm) {
        String invalidMessage = "Invalid editneighbor command! Correct format -> editneighbor -add <countryID> <neighborcountryID> " +
                "-remove <countryID> <neighborcountryID>";
        WorldMap copyMap = null;
        int commandLength = this.splitCommand.length;

        try {
            copyMap = new WorldMap(wm);
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


    public void editNeighbor(WorldMap wm) {
        int commandLength = this.splitCommand.length;


        int commandIndex = 1;
        while (commandIndex < commandLength) {

            if (splitCommand[commandIndex].equals("-add")) {

                try {
                    wm.addBorder(wm.getCountryID(splitCommand[commandIndex+1]),wm.getCountryID(splitCommand[commandIndex+2]));
                    logEntryBuffer.setString("Map Editor Phase: \n"+ "  => added border of"+
                            splitCommand[commandIndex+2] +" to "+ splitCommand[commandIndex+1] );
                } catch (Exception e) {
                    logEntryBuffer.setString("Map Editor Phase: \n"+ "  => could Not add borders due to the absence of existing source/target country" );
                    System.out.println(e);
                    return;
                }
                commandIndex = commandIndex + 3;


            } else if (splitCommand[commandIndex].equals("-remove")) {

                try {
                    wm.removeBorder(wm.getCountryID(splitCommand[commandIndex+1]),wm.getCountryID(splitCommand[commandIndex+2]));
                    logEntryBuffer.setString("Map Editor Phase: \n"+ "  => removed border between"+
                            splitCommand[commandIndex+2] +" and "+ splitCommand[commandIndex+1] );
                } catch (Exception e) {
                    logEntryBuffer.setString("Map Editor Phase: \n"+ "  => coud not remove border between"+
                            splitCommand[commandIndex+2] +" and "+ splitCommand[commandIndex+1] +
                            "either the source or target country does not exist.");
                    System.out.println(e);
                    return;
                }
                commandIndex = commandIndex + 3;
            }
        }
    }

    public void editMap(GameEngine ge) {
        String mapName = "";
        if (this.splitCommand.length < 2) {
            ge.d_renderer.renderError("Invalid command format! Correct format -> editmap <mapname>");
            return;
        }

        mapName = splitCommand[1];

        try {
            MapInterface.loadMap2(ge, mapName);
            logEntryBuffer.setString("loaded map file "+ mapName);
        } catch (FileNotFoundException e) {
            ge.d_renderer.renderError("FileNotFoundException : File does not exist.");
            logEntryBuffer.setString("map file does not exist");
            ge.d_renderer.renderMessage("Creating file by the name : " + mapName);
            logEntryBuffer.setString("creating file "+ mapName);
            try {
                MapInterface.saveMap(ge, mapName);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            editMap(ge);
        } catch (NumberFormatException e) {
            ge.d_renderer.renderError("NumberFormatException : File has incorrect formatting.");
            logEntryBuffer.setString("Map Editor Phase: \n"+ " Entered Command Not Executed: editmap => "+ d_command+ "  due to Incorrect Formatting");
        } catch (ContinentAlreadyExistsException | ContinentDoesNotExistException |
                 DuplicateCountryException | CountryDoesNotExistException e) {
            ge.d_renderer.renderError("InvalidMapException : Map is disjoint or incorrect.");
            logEntryBuffer.setString("Map Editor Phase: \n"+ " Entered Command Not Executed: editmap => "+ d_command+ "  as Map is disjoint or incorrect");
        }
    }

}
