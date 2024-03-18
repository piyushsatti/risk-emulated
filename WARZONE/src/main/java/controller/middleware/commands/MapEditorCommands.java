package controller.middleware.commands;

import controller.GameEngine;
import controller.MapInterface;
import controller.statepattern.MapEditor;
import controller.statepattern.Phase;
import controller.statepattern.Starting;
import helpers.exceptions.ContinentAlreadyExistsException;
import helpers.exceptions.ContinentDoesNotExistException;
import helpers.exceptions.CountryDoesNotExistException;
import helpers.exceptions.DuplicateCountryException;
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

    @Override
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

    public String getCurrentPhase(GameEngine p_gameEngine) {
        Phase phase = p_gameEngine.getCurrentState();
        String l_currClass = String.valueOf(phase.getClass());
        int l_index = l_currClass.lastIndexOf(".");
        return l_currClass.substring(l_index + 1);
    }

    @Override
    public void execute(GameEngine p_ge) {
        if (!this.validateCommandName()) {
            p_ge.d_renderer.renderError("InvalidCommandException : Invalid Command");
            return;
        }

        String[] l_command = d_command.trim().split("\\s+");
        String d_currPhase = getCurrentPhase(p_ge);

        switch (l_command[0]) {
            case "showmap":
                logEntryBuffer.setString("Phase :" + d_currPhase + "\n" + " Entered Command: showmap");
                p_ge.d_renderer.showMap(false);
                logEntryBuffer.setString("Phase :" + d_currPhase + "\n" + " Executed Command: showmap =>" + d_command);
                break;
            case "validatemap":
                logEntryBuffer.setString("Phase :" + d_currPhase + "\n" + " Entered Command: validatemap");
                if (MapInterface.validateMap(p_ge))
                {
                    p_ge.d_renderer.renderMessage("Map is valid");
                    logEntryBuffer.setString("Phase :" + d_currPhase + "\n" + " Executed Command: validatemap");
                }
                else
                {
                    p_ge.d_renderer.renderMessage("Map is not valid");
                    logEntryBuffer.setString("Phase :" + d_currPhase + "\n" + " Command: validatemap Not Executed: map is not valid!");
                }
                break;
            case "savemap":
                try {
                    logEntryBuffer.setString("Phase :" + d_currPhase + "\n" + " Entered Command: savemap => " + d_command);
                    MapInterface.saveMap(p_ge, l_command[1]);
                    logEntryBuffer.setString("Phase :" + d_currPhase + "\n" + "  Executed Command: savemap => " + d_command);
                } catch (IOException e) {
                    p_ge.d_renderer.renderError("IOException : Encountered File I/O Error");
                    logEntryBuffer.setString("Phase :" + d_currPhase + "\n" + " Command: savemap Not Executed due to File I/O Error");
                }
                break;
            case "editmap":
                logEntryBuffer.setString("Phase :" + d_currPhase + "\n" + " Entered Command: editmap => " + d_command);
                editMap(p_ge, d_currPhase);
                break;
            case "editcontinent":
                logEntryBuffer.setString("Phase :" + d_currPhase + "\n" + " Entered Command: editcontinent => " + d_command);
                if (editContinentValidator(p_ge.d_worldmap)) {
                    editContinent(p_ge.d_worldmap, d_currPhase);

                }
                break;
            case "editcountry":
                logEntryBuffer.setString("Phase :" + d_currPhase + "\n" + " Entered Command: editcountry => " + d_command);
                if (editCountryValidator(p_ge.d_worldmap)) {
                    editCountry(p_ge.d_worldmap, d_currPhase);
                }
                break;
            case "editneighbor":
                logEntryBuffer.setString("Phase :" + d_currPhase + "\n" + " Entered Command: editneighbor => " + d_command);
                if (editNeighborValidator(p_ge.d_worldmap)) {
                    editNeighbor(p_ge.d_worldmap, d_currPhase);
                }
                break;
            case "exit":
                logEntryBuffer.setString("Phase :" + d_currPhase + "\n" + " Entered Command: exit => " + d_command);
                p_ge.resetMap();
                p_ge.setCurrentState(new Starting(p_ge));
                logEntryBuffer.setString("Phase :" + d_currPhase + "\n" + " Executed Command: exit");
                break;
        }
    }

    public boolean editContinentValidator(WorldMap wm) {
        String invalidMessage = "Invalid editcontinent command! Correct format -> editcontinent -add <continentID> <continentvalue> -remove <continentID>";
        WorldMap copyMap;
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

    public void editContinent(WorldMap p_wm, String p_currPhase) {
        int commandLength = this.splitCommand.length;
        int commandIndex = 1;
        while (commandIndex < commandLength) {

            if (splitCommand[commandIndex].equals("-add")) {

                try {
                    p_wm.addContinent(splitCommand[commandIndex + 1], Integer.parseInt(splitCommand[commandIndex + 2]));
                    logEntryBuffer.setString("Phase :" + p_currPhase + "\n" + " added continent" + splitCommand[commandIndex + 1]
                            +" with bonus armies value: "+splitCommand[commandIndex + 2]);
                } catch (Exception e) {
                    logEntryBuffer.setString("Phase :" + p_currPhase + "\n" + " cannot add continent" + splitCommand[commandIndex + 1]
                            +" as it already exists");
                    System.out.println(e);
                }
                commandIndex = commandIndex + 3;

            } else if (splitCommand[commandIndex].equals("-remove")) {

                try {
                    p_wm.removeContinent(p_wm.getContinentID(splitCommand[commandIndex + 1]));
                    logEntryBuffer.setString("Phase :" + p_currPhase + "\n" + " removed continent" +
                            splitCommand[commandIndex + 1]);
                } catch (Exception e) {
                    logEntryBuffer.setString("Phase :" + p_currPhase + "\n" + " cannot remove continent" +
                            splitCommand[commandIndex + 1]+"it does not exist");
                    System.out.println(e);
                }
                commandIndex = commandIndex + 2;

            }
        }

    }


    public boolean editCountryValidator(WorldMap wm) {
        String invalidMessage = "Invalid editcountry command! Correct format -> editcountry -add <countryID> <continentID> -remove <countryID>";
        WorldMap copyMap;
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
                        copyMap.addCountry(splitCommand[commandIndex + 1], wm.getContinentID(splitCommand[commandIndex + 2]));
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


    public void editCountry(WorldMap wm, String p_currPhase) {
        int commandLength = this.splitCommand.length;


        int commandIndex = 1;
        while (commandIndex < commandLength) {

            if (splitCommand[commandIndex].equals("-add")) {

                try {
                    wm.addCountry(splitCommand[commandIndex + 1], wm.getContinentID(splitCommand[commandIndex + 2]));
                    logEntryBuffer.setString("Phase :" + p_currPhase + "\n" + " added country" +
                            splitCommand[commandIndex + 1]+" continent "+splitCommand[commandIndex + 2]);
                } catch (Exception e) {
                    logEntryBuffer.setString("Phase :" + p_currPhase + "\n" + " cannot add country" +
                            splitCommand[commandIndex + 1]+"due to either target continent not existing or country already existing");
                    System.out.println(e);
                    return;
                }
                commandIndex = commandIndex + 3;


            } else if (splitCommand[commandIndex].equals("-remove")) {

                try {
                    wm.removeCountry(wm.getCountryID(splitCommand[commandIndex + 1]));
                    logEntryBuffer.setString("Phase :" + p_currPhase + "\n" + " removed country" +
                            splitCommand[commandIndex + 1]);
                } catch (Exception e) {
                    logEntryBuffer.setString("Phase :" + p_currPhase + "\n" + " could not remove country" +
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
        WorldMap copyMap;
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


    public void editNeighbor(WorldMap wm, String p_currPhase) {
        int commandLength = this.splitCommand.length;


        int commandIndex = 1;
        while (commandIndex < commandLength) {

            if (splitCommand[commandIndex].equals("-add")) {

                try {
                    wm.addBorder(wm.getCountryID(splitCommand[commandIndex+1]),wm.getCountryID(splitCommand[commandIndex+2]));
                    logEntryBuffer.setString("Phase :" + p_currPhase + "\n" + "  => added border of" +
                            splitCommand[commandIndex+2] +" to "+ splitCommand[commandIndex+1] );
                } catch (Exception e) {
                    logEntryBuffer.setString("Phase :" + p_currPhase + "\n" + "  => could Not add borders due to the absence of existing source/target country");
                    System.out.println(e);
                    return;
                }
                commandIndex = commandIndex + 3;


            } else if (splitCommand[commandIndex].equals("-remove")) {

                try {
                    wm.removeBorder(wm.getCountryID(splitCommand[commandIndex+1]),wm.getCountryID(splitCommand[commandIndex+2]));
                    logEntryBuffer.setString("Phase :" + p_currPhase + "\n" + "  => removed border between" +
                            splitCommand[commandIndex+2] +" and "+ splitCommand[commandIndex+1] );
                } catch (Exception e) {
                    logEntryBuffer.setString("Phase :" + p_currPhase + "\n" + "  => coud not remove border between" +
                            splitCommand[commandIndex+2] +" and "+ splitCommand[commandIndex+1] +
                            "either the source or target country does not exist.");
                    System.out.println(e);
                    return;
                }
                commandIndex = commandIndex + 3;
            }
        }
    }

    public void editMap(GameEngine ge, String p_currPhase) {
        String mapName;
        if (this.splitCommand.length < 2) {
            ge.d_renderer.renderError("Invalid command format! Correct format -> editmap <mapname>");
            return;
        }

        mapName = splitCommand[1];

        try {
            MapInterface.loadMap(ge, mapName);
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
            editMap(ge, p_currPhase);
        } catch (NumberFormatException e) {
            ge.d_renderer.renderError("NumberFormatException : File has incorrect formatting.");
            logEntryBuffer.setString("Phase :" + p_currPhase + "\n" + " Entered Command Not Executed: editmap => " + d_command + "  due to Incorrect Formatting");
        } catch (ContinentAlreadyExistsException | ContinentDoesNotExistException |
                 DuplicateCountryException | CountryDoesNotExistException e) {
            ge.d_renderer.renderError("InvalidMapException : Map is disjoint or incorrect.");
            logEntryBuffer.setString("Phase :" + p_currPhase + "\n" + " Entered Command Not Executed: editmap => " + d_command + "  as Map is disjoint or incorrect");
        }
    }

}
