package main.java.controller;

import exceptions.InvalidCommandException;
import main.java.views.TerminalRenderer;

import java.io.FileNotFoundException;
import java.util.*;

public class CommandValidator {

    private static final HashMap<String, List<String>> d_commandGamePhaseMap;

    private static final List<String> d_validCommandList;

    static {

        d_commandGamePhaseMap = new HashMap<>();

        List<String> l_mapEditorCommands = new ArrayList<>(
                Arrays.asList("editcontinent", "editcountry", "editneighbor", "savemap",
                        "editmap", "validatemap", "showmap"));

        d_commandGamePhaseMap.put("mapEditor", l_mapEditorCommands);

        List<String> l_gamePlayCommands = new ArrayList<>();

        l_gamePlayCommands.add("showmap");

        d_commandGamePhaseMap.put("gamePlay", l_gamePlayCommands);

        List<String> l_startUpCommands = new ArrayList<>();

        l_startUpCommands.add("loadmap");

        l_startUpCommands.add("gameplayer");

        d_commandGamePhaseMap.put("startUp", l_startUpCommands);

        List<String> l_mainGameLoopCommands = new ArrayList<>();

        l_mainGameLoopCommands.add("deploy");

        d_commandGamePhaseMap.put("mainGameLoop", l_mainGameLoopCommands);

        d_validCommandList = new ArrayList<>(Arrays.asList("editcontinent", "editcountry", "editneighbor", "showmap",
                "savemap", "editmap", "validatemap", "loadmap", "gameplayer", "deploy"));

    }

    List<List<Integer>> addContinentIdContinentValList;

    List<List<Integer>> addCountryIdContinentIdList;

    List<List<Integer>> addCountryIdNeighborCountryIdList;

    List<Integer> removeContinentIdList;

    List<Integer> removeCountryIdList;

    List<List<Integer>> removeCountryIdNeighborCountryIdList;

    List<String> playersToAdd;

    List<String> playersToRemove;

    List<Integer> countryIdNumList;

    String[] d_command_arr = null;

    public void addCommand(String p_entered_command) throws InvalidCommandException {

        this.d_command_arr = checkCommandValidity(p_entered_command);

    }

    private String[] checkCommandValidity(String p_enteredCommand) throws InvalidCommandException, NumberFormatException {

        String[] l_command = new String[0];

        if (p_enteredCommand.isEmpty()) throw new InvalidCommandException("empty command entered");

        p_enteredCommand = p_enteredCommand.trim();

        l_command = p_enteredCommand.split(" ");

        String mainCommand = l_command[0];

        if (!d_validCommandList.contains(mainCommand)) {

            throw new InvalidCommandException("invalid command entered");

        } else if (!d_commandGamePhaseMap.get(GameEngine.game_phase.toString()).contains(mainCommand)) {

            throw new InvalidCommandException("entered command " + mainCommand + "  invalid for this gamephase");

        } else if ((mainCommand.equals("savemap") || mainCommand.equals("editmap") || mainCommand.equals("loadmap")) && l_command.length != 2) {

            throw new InvalidCommandException("incorrect format, enter command followed by filename");

        } else if (mainCommand.equals("deploy") && l_command.length != 3) {

            throw new InvalidCommandException("invalid command,enter: deploy countryId num");

        } else if ((mainCommand.equals("showmap") || mainCommand.equals("validatemap") ||

                mainCommand.equals("assigncountries")) && l_command.length != 1) {

            throw new InvalidCommandException("invalid command, must not have options or parameters");

        }

        switch (mainCommand) {
            case "editcountry", "editcontinent", "editneighbor" -> this.checkEditCommandsValidity(l_command);
            case "gameplayer" -> this.checkGamePlayerCommandValidity(l_command);
            case "deploy" -> this.checkDeployCommandValidity(l_command);
        }

        return l_command;

    }

    private void checkDeployCommandValidity(String[] p_lCommand) throws NumberFormatException {

        int a = Integer.parseInt(p_lCommand[1]);

        int b = Integer.parseInt(p_lCommand[2]);

    }

    private void checkGamePlayerCommandValidity(String[] p_lCommand) throws InvalidCommandException {

        int l_countAddOption = 0;

        int l_countRemoveOption = 0;

        int len = p_lCommand.length;

        for (String s : p_lCommand) {

            if (s.equals("-add")) l_countAddOption++;

            else if (s.equals("-remove")) l_countRemoveOption++;

        }

        if (l_countAddOption == 0 && l_countRemoveOption == 0) {

            throw new InvalidCommandException("incorrect command, no options added");

        }

        int i=1;

        while (i < len) {

                String currOption = p_lCommand[i];

            if (!(currOption.equals("-add") || currOption.equals("-remove"))) {

                    throw new InvalidCommandException("invalid command format");

            } else if (currOption.equals("-add") && i + 1 >= len) {

                    throw new InvalidCommandException("invalid command format");

            } else if (currOption.equals("-add") && (p_lCommand[i + 1].equals("-add") || p_lCommand[i + 1].equals("-remove"))) {

                    throw new InvalidCommandException("invalid command format");

            } else if (currOption.equals("-remove") && (p_lCommand[i + 1].equals("-add") || p_lCommand[i + 1].equals("-remove"))) {

                    throw new InvalidCommandException("invalid command format");

            }

            i+=2;

        }
    }

    private void checkEditCommandsValidity(String[] p_lCommand) throws InvalidCommandException, NumberFormatException {

        int l_countAddOption = 0;

        int l_countRemoveOption = 0;

        int len = p_lCommand.length;

        for (String s : p_lCommand) {

            if (s.equals("-add")) l_countAddOption++;

            else if (s.equals("-remove")) l_countRemoveOption++;

        }

        if (l_countAddOption == 0 && l_countRemoveOption == 0) {

            throw new InvalidCommandException("no options added");

        }

        int i = 1;

        String mainCommand = p_lCommand[0];

        while (i < len) {

            if (!(p_lCommand[i].equals("-add") || p_lCommand[i].equals("-remove"))) {

                throw new InvalidCommandException("invalid command format");

            }

            if (p_lCommand[i].equals("-add") && i + 2 >= len) {

                throw new InvalidCommandException("invalid command format");

            } else if (p_lCommand[i].equals("-remove") && (mainCommand.equals("editcontinent") || mainCommand.equals("editcountry")) && i + 1 >= len) {

                throw new InvalidCommandException("invalid command format");

            } else if (p_lCommand[i].equals("-remove") && mainCommand.equals("editneighbor") && i + 2 >= len) {
                throw new InvalidCommandException("invalid command format");
            }

            if (p_lCommand[i].equals("-add")) {

                int p1 = Integer.parseInt(p_lCommand[i + 1]);

                int p2 = Integer.parseInt(p_lCommand[i + 2]);

                i += 3;
            } else if ((mainCommand.equals("editcontinent") || mainCommand.equals("editcountry")) && p_lCommand[i].equals("-remove")) {

                int p1 = Integer.parseInt(p_lCommand[i + 1]);

                i += 2;

            } else if (mainCommand.equals("editneighbor") && p_lCommand[i].equals("-remove")) {

                int p1 = Integer.parseInt(p_lCommand[i + 1]);

                int p2 = Integer.parseInt(p_lCommand[i + 2]);

                i += 3;

            }

        }

    }

    public void processValidCommand() throws NumberFormatException, FileNotFoundException {

        String[] p_lCommand = this.d_command_arr;

        String mainCommand = p_lCommand[0];

        int len = p_lCommand.length;

        if (mainCommand.equals("showmap") && (GameEngine.game_phase == GameEngine.GAME_PHASE.MAP_EDITOR)) {

            TerminalRenderer.showMap(); //method to show all continents, countries and their neighbors

        } else if (mainCommand.equals("showmap") && (GameEngine.game_phase == GameEngine.GAME_PHASE.GAMEPLAY)) {

            //showmapGamePlay(); method to show all countries, continents, armies on each country, ownership, connecitvity
            TerminalRenderer.showCurrentGameMap();
        }

        if (mainCommand.equals("editcontinent")) {

            addContinentIdContinentValList = new ArrayList<>();

            removeContinentIdList = new ArrayList<>();

            int i = 1;

            while (i < len) {

                String currOption = p_lCommand[i];

                if (currOption.equals("-add")) {

                    int addContinentId = Integer.parseInt(p_lCommand[++i]);

                    int addContinentVal = Integer.parseInt(p_lCommand[++i]);

                    List<Integer> pair = new ArrayList<>();

                    pair.add(addContinentId);

                    pair.add(addContinentVal);

                    addContinentIdContinentValList.add(pair);

                } else if (currOption.equals("-remove")) {

                    int removeContinentId = Integer.parseInt(p_lCommand[++i]);

                    removeContinentIdList.add(removeContinentId);

                }

                i++;

            }

            if (!addContinentIdContinentValList.isEmpty()) {
                for (List<Integer> pair : addContinentIdContinentValList) {
                    CommandInterface.addContinent(pair.get(0), pair.get(1));
                }

            }
            if (!removeContinentIdList.isEmpty()) {
                for (int continentId : removeContinentIdList) {
                    CommandInterface.removeContinentId(continentId);
                }

            }

            //some method to process the command with data saved in the lists

        } else if (mainCommand.equals("editcountry")) {

            addCountryIdContinentIdList = new ArrayList<>();

            removeCountryIdList = new ArrayList<>();

            int j = 1;

            while (j < len) {

                String currOption = p_lCommand[j];

                if (currOption.equals("-add")) {

                    int addCountryId = Integer.parseInt(p_lCommand[++j]);

                    int addContinentId = Integer.parseInt(p_lCommand[++j]);

                    List<Integer> pair = new ArrayList<>();

                    pair.add(addCountryId);

                    pair.add(addContinentId);

                    addCountryIdContinentIdList.add(pair);

                } else if (currOption.equals("-remove")) {

                    int removeCountryId = Integer.parseInt(p_lCommand[++j]);

                    removeCountryIdList.add(removeCountryId);

                }

                j++;

            }
            if (!addCountryIdContinentIdList.isEmpty()) {
                for (List<Integer> pair : addCountryIdContinentIdList) {
                    CommandInterface.addContinent(pair.get(0), pair.get(1));
                }

            }
            if (!removeCountryIdList.isEmpty()) {
                for (int continentId : removeContinentIdList) {
                    CommandInterface.removeContinentId(continentId);
                }

            }

            //some method to process the command with data saved in the lists

        } else if (mainCommand.equals("editneighbor")) {

            addCountryIdNeighborCountryIdList = new ArrayList<>();

            removeCountryIdNeighborCountryIdList = new ArrayList<>();

            int k = 1;

            while (k < len) {

                String currOption = p_lCommand[k];

                if (currOption.equals("-add")) {

                    int addCountryId = Integer.parseInt(p_lCommand[++k]);

                    int addNeighborCountryId = Integer.parseInt(p_lCommand[++k]);

                    List<Integer> pair = new ArrayList<>();

                    pair.add(addCountryId);

                    pair.add(addNeighborCountryId);

                    addCountryIdNeighborCountryIdList.add(pair);

                } else if (currOption.equals("-remove")) {

                    List<Integer> pair = new ArrayList<>();

                    int removeCountryId = Integer.parseInt(p_lCommand[++k]);

                    int removeNeighborCountryId = Integer.parseInt(p_lCommand[++k]);

                    pair.add(removeCountryId);

                    pair.add(removeNeighborCountryId);

                    removeCountryIdNeighborCountryIdList.add(pair);

                }

                k++;

            }

            //some method to process the command with data saved in the lists

        } else if (mainCommand.equals("gameplayer")) {

            playersToAdd = new ArrayList<>();

            playersToRemove = new ArrayList<>();

            int z = 1;

            while (z < len) {

                String currOption = p_lCommand[z];

                if (currOption.equals("-add")) {

                    String playerToAdd = p_lCommand[++z];

                    playersToAdd.add(playerToAdd);

                } else if (currOption.equals("-remove")) {

                    String playerToRemove = p_lCommand[++z];

                    playersToRemove.add(playerToRemove);

                }

                z++;

            }

            //some method to process the command with data saved in the lists

        } else if (mainCommand.equals("deploy")) {

            countryIdNumList = new ArrayList<>();

            int countryId = Integer.parseInt(p_lCommand[1]);

            int numReinforcements = Integer.parseInt(p_lCommand[2]);

            countryIdNumList.add(countryId);

            countryIdNumList.add(numReinforcements);

        } else if (mainCommand.equals("savemap")) {//method to savemap

        } else if (mainCommand.equals("editmap")) {//method to edit map

        } else if (mainCommand.equals("validatemap")) {//method to validate map

        } else if (mainCommand.equals("loadmap")) {//method to load map

        } else if (mainCommand.equals("assigncountries")) {//method to assign countries

        }

    }

    private void checkEditCommandsValidity(String[] p_lCommand) throws InvalidCommandException,NumberFormatException {

        int l_countAddOption = 0;

        int l_countRemoveOption = 0;

        int len = p_lCommand.length;

        for (String s : p_lCommand) {

            if (s.equals("-add")) l_countAddOption++;

            else if (s.equals("-remove")) l_countRemoveOption++;

        }

        if (l_countAddOption == 0 && l_countRemoveOption == 0) {

                throw new InvalidCommandException("no options added");

        }

        int i=1;

        String mainCommand = p_lCommand[0];

        while (i < len) {

            if (!(p_lCommand[i].equals("-add") || p_lCommand[i].equals("-remove"))) {

                    throw new InvalidCommandException("invalid command format");

            }

            if (p_lCommand[i].equals("-add") && i + 2 >= len) {

                    throw new InvalidCommandException("invalid command format");

            } else if (p_lCommand[i].equals("-remove") && (mainCommand.equals("editcontinent") || mainCommand.equals("editcountry")) && i + 1 >= len) {

                    throw new InvalidCommandException("invalid command format");

            } else if (p_lCommand[i].equals("-remove") && mainCommand.equals("editneighbor") && i + 2 >= len) {
                    throw new InvalidCommandException("invalid command format");
                }

            if (p_lCommand[i].equals("-add")) {

                    int p1 = Integer.parseInt(p_lCommand[i+1]);

                int p2 = Integer.parseInt(p_lCommand[i+2]);

                i+=3;
                } else if ((mainCommand.equals("editcontinent") || mainCommand.equals("editcountry")) && p_lCommand[i].equals("-remove")) {

                    int p1 = Integer.parseInt(p_lCommand[i+1]);

                i+=2;

            } else if (mainCommand.equals("editneighbor") && p_lCommand[i].equals("-remove")) {

                    int p1 = Integer.parseInt(p_lCommand[i+1]);

                int p2 = Integer.parseInt(p_lCommand[i+2]);

                i+=3;

            }

        }

    }

    public static void main(String[] args) throws InvalidCommandException {

        System.out.println("enter command:");

        Scanner sc = new Scanner(System.in);

        String command = sc.nextLine();

        CommandValidator obj = new CommandValidator();

        obj.addCommand(command, "mapEditor");

    }

}
