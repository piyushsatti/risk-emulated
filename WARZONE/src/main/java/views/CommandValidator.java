package views;

import exceptions.InvalidCommandException;
import exceptions.InvalidPlayerToRemoveException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class CommandValidator {

    private static final HashMap<String, List<String>> d_commandGamePhaseMap;
    private static List<String> d_validCommandList;

    static {
        d_commandGamePhaseMap = new HashMap<>();
        List<String> l_commands = new ArrayList<>(
                Arrays.asList("editcontinent", "editcountry", "editneighbor", "savemap",
                        "editmap", "validatemap", "showmap"));
        d_commandGamePhaseMap.put("mapEditor", l_commands);
        l_commands.clear();
        l_commands.add("showmap");
        d_commandGamePhaseMap.put("gamePlay", l_commands);
        l_commands.clear();
        l_commands.add("loadmap");
        l_commands.add("gameplayer");
        l_commands.add("assigncountries");
        d_commandGamePhaseMap.put("startUp", l_commands);
        l_commands.clear();
        l_commands.add("deploy");
        d_commandGamePhaseMap.put("mainGameLoop", l_commands);

        d_validCommandList = new ArrayList<>(Arrays.asList("editcontinent", "editcountry", "editneighbor", "showmap",
                "savemap", "editmap", "validatemap", "loadmap", "gameplayer", "deploy", "assigncountries"));
    }

    public void checkCommandValidity(String p_enteredCommand, String p_gamePhaseString)
            throws InvalidCommandException, NumberFormatException, InvalidPlayerToRemoveException {
        try {
            if (p_enteredCommand.length() == 0) {
                throw new InvalidCommandException("empty command entered");
            }
            p_enteredCommand = p_enteredCommand.trim();
            String[] l_command = p_enteredCommand.split(" ");
            if (!d_validCommandList.contains(l_command[0])) {
                throw new InvalidCommandException("invalid command entered");
            } else if (!d_commandGamePhaseMap.get(p_gamePhaseString).contains(l_command[0])) {
                throw new InvalidCommandException("entered command invalid for this gamephase");
            } else if ((l_command[0].equals("editcontinent") || l_command[0].equals("editcountry"))
                    && l_command.length != 6) {
                throw new InvalidCommandException("invalid command");
            } else if (l_command[0].equals("editneighbor") && l_command.length != 7) {
                throw new InvalidCommandException("invalid command");
            } else if ((l_command[0].equals("savemap") || l_command[0].equals("editmap")
                    || l_command[0].equals("loadmap"))
                    && l_command.length != 2) {
                throw new InvalidCommandException("invalid command");
            } else if (l_command[0].equals("deploy") && l_command.length != 3) {
                throw new InvalidCommandException("invalid command");
            } else if (l_command[0].equals("gameplayer") && l_command.length != 5) {
                throw new InvalidCommandException("invalid command");
            } else if (l_command.length != 1) {
                throw new InvalidCommandException("invalid command");
            }
            processValidCommand(l_command, p_gamePhaseString);
        } catch (InvalidCommandException e) {
            System.out.println(e.getMessage());
        }
    }

    public void processValidCommand(String[] p_command, String p_gamePhaseString2)
            throws NumberFormatException, InvalidPlayerToRemoveException {

        try {
            int l_addContinentID;
            int l_addContinentValue;
            int l_removeContinentID;

            int l_addCountryID;
            int l_removeCountryID;

            int l_addNeighborCountryID;
            int l_removeNeighborCountryID;

            String l_addPlayer;
            String l_removePlayer;

            int l_countryID;
            int l_num;
            String l_cmd = p_command[0];

            if (l_cmd.equals("editcontinent")) {
                l_addContinentID = Integer.parseInt(p_command[2]);
                l_addContinentValue = Integer.parseInt(p_command[3]);
                l_removeContinentID = Integer.parseInt(p_command[5]);
            } else if (l_cmd.equals("editcountry")) {
                l_addCountryID = Integer.parseInt(p_command[2]);
                l_addContinentID = Integer.parseInt(p_command[3]);
                l_removeCountryID = Integer.parseInt(p_command[5]);
            } else if (l_cmd.equals("editneighbor")) {
                l_addCountryID = Integer.parseInt(p_command[2]);
                l_addNeighborCountryID = Integer.parseInt(p_command[3]);
                l_removeCountryID = Integer.parseInt(p_command[5]);
                l_removeNeighborCountryID = Integer.parseInt(p_command[6]);
            } else if (l_cmd.equals("gameplayer")) {
                l_addPlayer = p_command[2];
                l_removePlayer = p_command[4];
                List<String> l_playerList = new ArrayList<>(); // tem list, need to use the one in GameEngine.java
                if (!l_playerList.contains(l_removePlayer)) // need playerlist from gameengine.java
                {
                    throw new InvalidPlayerToRemoveException("player does not exist");
                }
            } else if (l_cmd.equals("deploy")) {
                l_countryID = Integer.parseInt(p_command[1]);
                l_num = Integer.parseInt(p_command[2]);
            }

        } catch (NumberFormatException e) {
            System.out.println(
                    "continentID, continentValue, countryID,neighboringCountryID, number of armies must be numeric values");
        } catch (InvalidPlayerToRemoveException e) {
            System.out.println(e.getMessage());
        }
    }

}
