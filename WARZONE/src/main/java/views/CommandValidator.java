package views;

import exceptions.InvalidCommandException;
import exceptions.InvalidPlayerToRemoveException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class CommandValidator {

    private static final HashMap<String, List<String>> commandGamePhaseMap;
    private static List<String> validCommandList;

    static {
        commandGamePhaseMap = new HashMap<>();
        List<String> commands = new ArrayList<>(Arrays.asList("editcontinent", "editcountry", "editneighbor", "savemap",
                "editmap", "validatemap", "showmap"));
        commandGamePhaseMap.put("mapEditor", commands);
        commands.clear();
        commands.add("showmap");
        commandGamePhaseMap.put("gamePlay", commands);
        commands.clear();
        commands.add("loadmap");
        commands.add("gameplayer");
        commands.add("assigncountries");
        commandGamePhaseMap.put("startUp", commands);
        commands.clear();
        commands.add("deploy");
        commandGamePhaseMap.put("mainGameLoop", commands);

        validCommandList = new ArrayList<>(Arrays.asList("editcontinent", "editcountry", "editneighbor", "showmap",
                "savemap", "editmap", "validatemap", "loadmap", "gameplayer", "deploy", "assigncountries"));
    }

    public void checkCommandValidity(String enteredCommand, String gamePhaseString)
            throws InvalidCommandException, NumberFormatException, InvalidPlayerToRemoveException {
        try {
            if (enteredCommand.length() == 0) {
                throw new InvalidCommandException("empty command entered");
            }
            enteredCommand = enteredCommand.trim();
            String[] command = enteredCommand.split(" ");
            if (!validCommandList.contains(command[0])) {
                throw new InvalidCommandException("invalid command entered");
            } else if (!commandGamePhaseMap.get(gamePhaseString).contains(command[0])) {
                throw new InvalidCommandException("entered command invalid for this gamephase");
            } else if ((command[0].equals("editcontinent") || command[0].equals("editcountry"))
                    && command.length != 6) {
                throw new InvalidCommandException("invalid command");
            } else if (command[0].equals("editneighbor") && command.length != 7) {
                throw new InvalidCommandException("invalid command");
            } else if ((command[0].equals("savemap") || command[0].equals("editmap") || command[0].equals("loadmap"))
                    && command.length != 2) {
                throw new InvalidCommandException("invalid command");
            } else if (command[0].equals("deploy") && command.length != 3) {
                throw new InvalidCommandException("invalid command");
            } else if (command[0].equals("gameplayer") && command.length != 5) {
                throw new InvalidCommandException("invalid command");
            } else if (command.length != 1) {
                throw new InvalidCommandException("invalid command");
            }
            processValidCommand(command, gamePhaseString);
        } catch (InvalidCommandException e) {
            System.out.println(e.getMessage());
        }
    }

    public void processValidCommand(String[] command, String gamePhaseString2)
            throws NumberFormatException, InvalidPlayerToRemoveException {

        try {
            int addContinentID;
            int addContinentValue;
            int removeContinentID;

            int addCountryID;
            int removeCountryID;

            int addNeighborCountryID;
            int removeNeighborCountryID;

            String addPlayer;
            String removePlayer;

            int countryID;
            int num;
            String cmd = command[0];

            if (cmd.equals("editcontinent")) {
                addContinentID = Integer.parseInt(command[2]);
                addContinentValue = Integer.parseInt(command[3]);
                removeContinentID = Integer.parseInt(command[5]);
            } else if (cmd.equals("editcountry")) {
                addCountryID = Integer.parseInt(command[2]);
                addContinentID = Integer.parseInt(command[3]);
                removeCountryID = Integer.parseInt(command[5]);
            } else if (cmd.equals("editneighbor")) {
                addCountryID = Integer.parseInt(command[2]);
                addNeighborCountryID = Integer.parseInt(command[3]);
                removeCountryID = Integer.parseInt(command[5]);
                removeNeighborCountryID = Integer.parseInt(command[6]);
            } else if (cmd.equals("gameplayer")) {
                addPlayer = command[2];
                removePlayer = command[4];
                List<String> playerList = new ArrayList<>(); // tem list, need to use the one in GameEngine.java
                if (!playerList.contains(removePlayer)) // need playerlist from gameengine.java
                {
                    throw new InvalidPlayerToRemoveException("player does not exist");
                }
            } else if (cmd.equals("deploy")) {
                countryID = Integer.parseInt(command[1]);
                num = Integer.parseInt(command[2]);
            }

        } catch (NumberFormatException e) {
            System.out.println(
                    "continentID, continentValue, countryID,neighboringCountryID, number of armies must be numeric values");
        } catch (InvalidPlayerToRemoveException e) {
            System.out.println(e.getMessage());
        }
    }

}
