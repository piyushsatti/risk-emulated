package main.java.controller;

import exceptions.InvalidCommandException;
import main.java.views.TerminalRenderer;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

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

    List<List<Integer>> d_addContinentIdContinentValList;

    List<List<Integer>> d_addCountryIdContinentIdList;

    List<List<Integer>> d_addCountryIdNeighborCountryIdList;

    List<Integer> d_removeContinentIdList;

    List<Integer> d_removeCountryIdList;

    List<List<Integer>> d_removeCountryIdNeighborCountryIdList;

    List<String> d_playersToAdd;

    List<String> d_playersToRemove;

    List<Integer> d_countryIdNumList;

    String[] d_command_arr = null;


    /**
     * This method adds the command after checking the validity of the command.
     * @param p_entered_command
     * @throws InvalidCommandException
     */
    public void addCommand(String p_entered_command) throws InvalidCommandException {

        this.d_command_arr = checkCommandValidity(p_entered_command);

    }

    /**
     * this method checks if the command entered is of valid format or not.
     * @param p_enteredCommand
     * @return
     * @throws InvalidCommandException
     * @throws NumberFormatException
     */
    private String[] checkCommandValidity(String p_enteredCommand) throws InvalidCommandException, NumberFormatException {

        String[] l_command;

        if (p_enteredCommand.isEmpty()) throw new InvalidCommandException("empty command entered");

        p_enteredCommand = p_enteredCommand.trim();

        l_command = p_enteredCommand.split(" ");

        String l_mainCommand = l_command[0];

        if (!d_validCommandList.contains(l_mainCommand)) {

            throw new InvalidCommandException("invalid command entered");

        } else if (!d_commandGamePhaseMap.get(GameEngine.game_phase.toString()).contains(l_mainCommand)) {

            throw new InvalidCommandException("entered command " + l_mainCommand + "  invalid for this gamephase");

        } else if ((l_mainCommand.equals("savemap") || l_mainCommand.equals("editmap") || l_mainCommand.equals("loadmap")) && l_command.length != 2) {

            throw new InvalidCommandException("incorrect format, enter command followed by filename");

        } else if (l_mainCommand.equals("deploy") && l_command.length != 3) {

            throw new InvalidCommandException("invalid command,enter: deploy countryId num");

        } else if ((l_mainCommand.equals("showmap") || l_mainCommand.equals("validatemap") ||

                l_mainCommand.equals("assigncountries")) && l_command.length != 1) {

            throw new InvalidCommandException("invalid command, must not have options or parameters");

        }

        switch (l_mainCommand) {
            case "editcountry", "editcontinent", "editneighbor" -> this.checkEditCommandsValidity(l_command);
            case "gameplayer" -> this.checkGamePlayerCommandValidity(l_command);
            case "deploy" -> this.checkDeployCommandValidity(l_command);
        }

        return l_command;

    }

    /**
     * this method checks if the deploy command is valid or not
     * @param p_lCommand
     * @throws NumberFormatException
     */
    private void checkDeployCommandValidity(String[] p_lCommand) throws NumberFormatException {

        int a = Integer.parseInt(p_lCommand[1]);

        int b = Integer.parseInt(p_lCommand[2]);

    }

    /**
     * this method checks the validity of gameplayer command
     * @param p_lCommand
     * @throws InvalidCommandException
     */
    private void checkGamePlayerCommandValidity(String[] p_lCommand) throws InvalidCommandException {

        int l_countAddOption = 0;

        int l_countRemoveOption = 0;

        int l_len = p_lCommand.length;

        for (String l_s : p_lCommand) {

            if (l_s.equals("-add")) l_countAddOption++;

            else if (l_s.equals("-remove")) l_countRemoveOption++;

        }

        if (l_countAddOption == 0 && l_countRemoveOption == 0) {

            throw new InvalidCommandException("incorrect command, no options added");

        }

        int l_i=1;

        while (l_i < l_len) {

                String l_currOption = p_lCommand[l_i];

            if (!(l_currOption.equals("-add") || l_currOption.equals("-remove"))) {

                    throw new InvalidCommandException("invalid command format");

            } else if (l_currOption.equals("-add") && l_i + 1 >= l_len) {

                    throw new InvalidCommandException("invalid command format");

            } else if (l_currOption.equals("-add") && (p_lCommand[l_i + 1].equals("-add") || p_lCommand[l_i + 1].equals("-remove"))) {

                    throw new InvalidCommandException("invalid command format");

            } else if (l_currOption.equals("-remove") && (p_lCommand[l_i + 1].equals("-add") || p_lCommand[l_i + 1].equals("-remove"))) {

                    throw new InvalidCommandException("invalid command format");

            }

            l_i+=2;

        }
    }

    /**
     * this method checks the validity of the editcountry, editcontinent and editneighbor commands
     * @param p_lCommand
     * @throws InvalidCommandException
     * @throws NumberFormatException
     */
    private void checkEditCommandsValidity(String[] p_lCommand) throws InvalidCommandException, NumberFormatException {

        int l_countAddOption = 0;

        int l_countRemoveOption = 0;

        int l_len = p_lCommand.length;

        for (String l_s : p_lCommand) {

            if (l_s.equals("-add")) l_countAddOption++;

            else if (l_s.equals("-remove")) l_countRemoveOption++;

        }

        if (l_countAddOption == 0 && l_countRemoveOption == 0) {

            throw new InvalidCommandException("no options added");

        }

        int l_i = 1;

        String mainCommand = p_lCommand[0];

        while (l_i < l_len) {

            if (!(p_lCommand[l_i].equals("-add") || p_lCommand[l_i].equals("-remove"))) {

                throw new InvalidCommandException("invalid command format");

            }

            if (p_lCommand[l_i].equals("-add") && l_i + 2 >= l_len) {

                throw new InvalidCommandException("invalid command format");

            } else if (p_lCommand[l_i].equals("-remove") && (mainCommand.equals("editcontinent") || mainCommand.equals("editcountry")) && l_i + 1 >= l_len) {

                throw new InvalidCommandException("invalid command format");

            } else if (p_lCommand[l_i].equals("-remove") && mainCommand.equals("editneighbor") && l_i + 2 >= l_len) {
                throw new InvalidCommandException("invalid command format");
            }

            if (p_lCommand[l_i].equals("-add")) {

                int p1 = Integer.parseInt(p_lCommand[l_i + 1]);

                int p2 = Integer.parseInt(p_lCommand[l_i + 2]);

                l_i += 3;
            } else if ((mainCommand.equals("editcontinent") || mainCommand.equals("editcountry")) && p_lCommand[l_i].equals("-remove")) {

                int p1 = Integer.parseInt(p_lCommand[l_i + 1]);

                l_i += 2;

            } else if (mainCommand.equals("editneighbor") && p_lCommand[l_i].equals("-remove")) {

                int p1 = Integer.parseInt(p_lCommand[l_i + 1]);

                int p2 = Integer.parseInt(p_lCommand[l_i + 2]);

                l_i += 3;

            }

        }

    }

    /**
     * this method is used to call further methods which would execute the commands
     * @throws NumberFormatException
     * @throws FileNotFoundException
     */
    public void processValidCommand() throws NumberFormatException, FileNotFoundException {

        String[] p_lCommand = this.d_command_arr;

        String l_mainCommand = p_lCommand[0];

        int l_len = p_lCommand.length;

        if (l_mainCommand.equals("showmap") && (GameEngine.game_phase == GameEngine.GAME_PHASE.MAP_EDITOR)) {

            TerminalRenderer.showMap(); //method to show all continents, countries and their neighbors

        } else if (l_mainCommand.equals("showmap") && (GameEngine.game_phase == GameEngine.GAME_PHASE.GAMEPLAY)) {

            //method to show all countries, continents, armies on each country, ownership, connecitvity
            TerminalRenderer.showCurrentGameMap();
        }

        if (l_mainCommand.equals("editcontinent")) {

            d_addContinentIdContinentValList = new ArrayList<>();

            d_removeContinentIdList = new ArrayList<>();

            int l_i = 1;

            while (l_i < l_len) {

                String l_currOption = p_lCommand[l_i];

                if (l_currOption.equals("-add")) {

                    int l_addContinentId = Integer.parseInt(p_lCommand[++l_i]);

                    int l_addContinentVal = Integer.parseInt(p_lCommand[++l_i]);

                    List<Integer> l_pair = new ArrayList<>();

                    l_pair.add(l_addContinentId);

                    l_pair.add(l_addContinentVal);

                    d_addContinentIdContinentValList.add(l_pair);

                } else if (l_currOption.equals("-remove")) {

                    int l_removeContinentId = Integer.parseInt(p_lCommand[++l_i]);

                    d_removeContinentIdList.add(l_removeContinentId);

                }

                l_i++;

            }

            for(int j = 0; j< d_addContinentIdContinentValList.size(); j++)
            {
                List<Integer> pair = d_addContinentIdContinentValList.get(j);
                CommandInterface.addContinentIdContinentVal(pair.get(0), pair.get(1));
            }
            for(int j=0;j<d_removeContinentIdList.size();j++)
            {
                CommandInterface.removeContinentId(d_removeContinentIdList.get(j));
            }

        } else if (l_mainCommand.equals("editcountry")) {

            d_addCountryIdContinentIdList = new ArrayList<>();

            d_removeCountryIdList = new ArrayList<>();

            int l_j = 1;

            while (l_j < l_len) {

                String l_currOption = p_lCommand[l_j];

                if (l_currOption.equals("-add")) {

                    int l_addCountryId = Integer.parseInt(p_lCommand[++l_j]);

                    int l_addContinentId = Integer.parseInt(p_lCommand[++l_j]);

                    List<Integer> l_pair = new ArrayList<>();

                    l_pair.add(l_addCountryId);

                    l_pair.add(l_addContinentId);

                    d_addCountryIdContinentIdList.add(l_pair);

                } else if (l_currOption.equals("-remove")) {

                    int l_removeCountryId = Integer.parseInt(p_lCommand[++l_j]);

                    d_removeCountryIdList.add(l_removeCountryId);

                }

                l_j++;

            }
            for(int i = 0; i< d_addCountryIdContinentIdList.size(); i++)
            {
                List<Integer> pair = d_addCountryIdContinentIdList.get(i);
                CommandInterface.addCountryIdContinentId(pair.get(0), pair.get(1));
            }
            for(int i=0;i<d_removeCountryIdList.size();i++)
            {
                CommandInterface.removeCountryId(d_removeCountryIdList.get(i));
            }

        } else if (l_mainCommand.equals("editneighbor")) {

            d_addCountryIdNeighborCountryIdList = new ArrayList<>();

            d_removeCountryIdNeighborCountryIdList = new ArrayList<>();

            int l_k = 1;

            while (l_k < l_len) {

                String l_currOption = p_lCommand[l_k];

                if (l_currOption.equals("-add")) {

                    int l_addCountryId = Integer.parseInt(p_lCommand[++l_k]);

                    int l_addNeighborCountryId = Integer.parseInt(p_lCommand[++l_k]);

                    List<Integer> l_pair = new ArrayList<>();

                    l_pair.add(l_addCountryId);

                    l_pair.add(l_addNeighborCountryId);

                    d_addCountryIdNeighborCountryIdList.add(l_pair);

                } else if (l_currOption.equals("-remove")) {

                    List<Integer> l_pair = new ArrayList<>();

                    int l_removeCountryId = Integer.parseInt(p_lCommand[++l_k]);

                    int l_removeNeighborCountryId = Integer.parseInt(p_lCommand[++l_k]);

                    l_pair.add(l_removeCountryId);

                    l_pair.add(l_removeNeighborCountryId);

                    d_removeCountryIdNeighborCountryIdList.add(l_pair);

                }

                l_k++;

            }
            for(int i=0;i<d_addCountryIdNeighborCountryIdList.size();i++)
            {
                List<Integer> l_pair = d_addCountryIdNeighborCountryIdList.get(i);
                CommandInterface.addCountryIdNeighborCountryId(l_pair.get(0), l_pair.get(1));
            }
            for(int i=0;i<d_removeCountryIdNeighborCountryIdList.size();i++)
            {
                List<Integer> l_pair = d_removeCountryIdNeighborCountryIdList.get(i);
                CommandInterface.removeCountryIdNeighborCountryId(l_pair.get(0), l_pair.get(1));
            }
        } else if (l_mainCommand.equals("gameplayer")) {

            d_playersToAdd = new ArrayList<>();

            d_playersToRemove = new ArrayList<>();

            int l_z = 1;

            while (l_z < l_len) {

                String l_currOption = p_lCommand[l_z];

                if (l_currOption.equals("-add")) {

                    String l_playerToAdd = p_lCommand[++l_z];

                    d_playersToAdd.add(l_playerToAdd);

                } else if (l_currOption.equals("-remove")) {

                    String l_playerToRemove = p_lCommand[++l_z];

                    d_playersToRemove.add(l_playerToRemove);

                }

                l_z++;

            }
            for(int i=0;i<d_playersToAdd.size();i++)
            {
                CommandInterface.addPlayers(d_playersToAdd.get(i));
            }
            for(int i=0;i<d_playersToRemove.size();i++)
            {
                CommandInterface.removePlayers(d_playersToRemove.get(i));
            }

        } else if (l_mainCommand.equals("deploy")) {

            d_countryIdNumList = new ArrayList<>();

            int l_countryId = Integer.parseInt(p_lCommand[1]);

            int l_numReinforcements = Integer.parseInt(p_lCommand[2]);

            d_countryIdNumList.add(l_countryId);

            d_countryIdNumList.add(l_numReinforcements);

        } else if (l_mainCommand.equals("savemap")) {//method to savemap

        } else if (l_mainCommand.equals("editmap")) {//method to edit map

        } else if (l_mainCommand.equals("validatemap")) {//method to validate map

        } else if (l_mainCommand.equals("loadmap")) {//method to load map

        }
    }

    /**
     * main method
     * @param args
     * @throws InvalidCommandException
     */
    public static void main(String[] args) throws InvalidCommandException {

        System.out.println("enter command:");

        Scanner sc = new Scanner(System.in);

        String l_command = sc.nextLine();

        CommandValidator commandValidator = new CommandValidator();

        commandValidator.addCommand(l_command);

    }

}
