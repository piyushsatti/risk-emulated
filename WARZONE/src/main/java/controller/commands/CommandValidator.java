package main.java.controller.commands;

import exceptions.InvalidCommandException;
import main.java.controller.GameEngine;
import main.java.utils.exceptions.ContinentDoesNotExistException;
import main.java.utils.exceptions.CountryDoesNotExistException;
import main.java.utils.exceptions.PlayerDoesNotExistException;
import main.java.views.TerminalRenderer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class CommandValidator {

    private static final HashMap<String, List<String>> d_commandGamePhaseMap;

    private static final List<String> d_validCommandList;

    static {

        d_commandGamePhaseMap = new HashMap<>();

        d_commandGamePhaseMap.put(
                "MAP_EDITOR",
                new ArrayList<>(
                        Arrays.asList(
                                "editcontinent", "editcountry", "editneighbor", "savemap",
                                "editmap", "validatemap", "showmap"
                        )
                )
        );

        d_commandGamePhaseMap.put(
                "GAMEPLAY",
                new ArrayList<>(
                        List.of(
                                "showmap"
                        )
                )
        );

        d_commandGamePhaseMap.put(
                "MAIN_MENU",
                new ArrayList<>(
                        List.of(
                                "loadmap", "gameplayer"
                        )
                )
        );

        d_commandGamePhaseMap.put(
                "GAMEPLAY",
                new ArrayList<>(
                        List.of(
                                "deploy"
                        )
                )
        );

        d_validCommandList = new ArrayList<>(
                Arrays.asList(
                        "editcontinent", "editcountry", "editneighbor", "showmap",
                        "savemap", "editmap", "validatemap", "loadmap", "gameplayer", "deploy"
                )
        );

    }

    List<List<String>> d_addContinentIdContinentValList;

    List<List<String>> d_addCountryIdContinentIdList;

    List<List<String>> d_addCountryIdNeighborCountryIdList;

    List<String> d_removeContinentIdList;

    List<String> d_removeCountryIdList;

    List<List<String>> d_removeCountryIdNeighborCountryIdList;

    List<String> d_playersToAdd;

    List<String> d_playersToRemove;

    List<String> d_countryIdNumList;

    private  String[] d_command;


    /**
     * This method adds the command after checking the validity of the command.
     * @param p_entered_command
     * @throws InvalidCommandException
     */
    public void addCommand(String p_entered_command) throws InvalidCommandException {

        checkCommandValidity(p_entered_command);

    }
    /**
     * this method checks if the command entered is of valid format or not.
     * @param p_enteredCommand
     * @return
     * @throws InvalidCommandException
     * @throws NumberFormatException
     */
    private void checkCommandValidity(String p_enteredCommand) throws InvalidCommandException, NumberFormatException {


        if (p_enteredCommand.isEmpty()) throw new InvalidCommandException("empty command entered");

        p_enteredCommand = p_enteredCommand.trim();

        d_command = p_enteredCommand.split("\\s+");

        String l_mainCommand = d_command[0];

        if (!d_validCommandList.contains(l_mainCommand)) {

            throw new InvalidCommandException("invalid command entered");

        } else if (!d_commandGamePhaseMap.get(GameEngine.CURRENT_GAME_PHASE.toString()).contains(l_mainCommand)) {

            throw new InvalidCommandException("entered command " + l_mainCommand + "  invalid for this gamephase");

        } else if ((l_mainCommand.equals("savemap") || l_mainCommand.equals("editmap") || l_mainCommand.equals("loadmap")) && d_command.length != 2) {

            throw new InvalidCommandException("incorrect format, enter command followed by filename");

        } else if (l_mainCommand.equals("deploy") && d_command.length != 3) {

            throw new InvalidCommandException("invalid command,enter: deploy countryId num");

        } else if ((l_mainCommand.equals("showmap") || l_mainCommand.equals("validatemap") ||

                l_mainCommand.equals("assigncountries")) && d_command.length != 1) {

            throw new InvalidCommandException("invalid command, must not have options or parameters");

        }

        switch (l_mainCommand) {
            case "editcountry", "editcontinent", "editneighbor" -> {
                this.checkEditCommandsValidity(d_command);
                break;
            }
            case "gameplayer" -> {
                this.checkGamePlayerCommandValidity(d_command);
                break;
            }
            case "deploy" -> {
                System.out.println("I am in deploy check");
                this.checkDeployCommandValidity(d_command);

                break;
            }
        }
    }

    /**
     * this method checks if the deploy command is valid or not
     * @param p_lCommand
     * @throws NumberFormatException
     */
    private void checkDeployCommandValidity(String[] p_lCommand) throws NumberFormatException {
        int b = Integer.parseInt(p_lCommand[2]);
        System.out.println("Inside main check");
        return;
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

        String l_mainCommand = p_lCommand[0];

        while (l_i < l_len) {

            if (!(p_lCommand[l_i].equals("-add") || p_lCommand[l_i].equals("-remove"))) {

                throw new InvalidCommandException("invalid command format");

            }

            if (p_lCommand[l_i].equals("-add") && l_i + 2 >= l_len) {

                throw new InvalidCommandException("invalid command format");

            } else if (p_lCommand[l_i].equals("-remove") && (l_mainCommand.equals("editcontinent") || l_mainCommand.equals("editcountry")) && l_i + 1 >= l_len) {

                throw new InvalidCommandException("invalid command format");

            } else if (p_lCommand[l_i].equals("-remove") && l_mainCommand.equals("editneighbor") && l_i + 2 >= l_len) {
                throw new InvalidCommandException("invalid command format");
            }

            if (p_lCommand[l_i].equals("-add")) {

                l_i += 3;

            } else if ((l_mainCommand.equals("editcontinent") || l_mainCommand.equals("editcountry")) && p_lCommand[l_i].equals("-remove")) {

                l_i += 2;

            } else if (l_mainCommand.equals("editneighbor") && p_lCommand[l_i].equals("-remove")) {

                l_i += 3;

            }

        }

    }

    /**
     * this method is used to call further methods which would execute the commands
     * @throws NumberFormatException
     * @throws FileNotFoundException
     */
    public void processValidCommand() throws NumberFormatException, IOException, ContinentDoesNotExistException, CountryDoesNotExistException, PlayerDoesNotExistException {

        String[] p_lCommand = d_command;

        String l_mainCommand = p_lCommand[0];

        int l_len = p_lCommand.length;

        if (l_mainCommand.equals("showmap") && (GameEngine.CURRENT_GAME_PHASE == GameEngine.GAME_PHASES.MAP_EDITOR)) {

            TerminalRenderer.showMap(true); //method to show all continents, countries and their neighbors

        } else if (l_mainCommand.equals("showmap") && (GameEngine.CURRENT_GAME_PHASE == GameEngine.GAME_PHASES.GAMEPLAY)) {

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

                    String l_addContinentId = p_lCommand[++l_i];

                    String l_addContinentVal = p_lCommand[++l_i];

                    List<String> l_pair = new ArrayList<>();

                    l_pair.add(l_addContinentId);

                    l_pair.add(l_addContinentVal);

                    d_addContinentIdContinentValList.add(l_pair);

                } else if (l_currOption.equals("-remove")) {

                    String l_removeContinentId = p_lCommand[++l_i];

                    d_removeContinentIdList.add(l_removeContinentId);

                }

                l_i++;

            }

            for(int j = 0; j< d_addContinentIdContinentValList.size(); j++)
            {
                List<String> pair = d_addContinentIdContinentValList.get(j);
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

                    String l_addCountryId = p_lCommand[++l_j];

                    String l_addContinentId = p_lCommand[++l_j];

                    List<String> l_pair = new ArrayList<>();

                    l_pair.add(l_addCountryId);

                    l_pair.add(l_addContinentId);

                    d_addCountryIdContinentIdList.add(l_pair);

                } else if (l_currOption.equals("-remove")) {

                    String l_removeCountryId = p_lCommand[++l_j];

                    d_removeCountryIdList.add(l_removeCountryId);

                }

                l_j++;

            }
            for(int i = 0; i< d_addCountryIdContinentIdList.size(); i++)
            {
                List<String> pair = d_addCountryIdContinentIdList.get(i);
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

                    String l_addCountryId = p_lCommand[++l_k];

                    String l_addNeighborCountryId = p_lCommand[++l_k];

                    List<String> l_pair = new ArrayList<>();

                    l_pair.add(l_addCountryId);

                    l_pair.add(l_addNeighborCountryId);

                    d_addCountryIdNeighborCountryIdList.add(l_pair);

                } else if (l_currOption.equals("-remove")) {

                    List<String> l_pair = new ArrayList<>();

                    String l_removeCountryId = p_lCommand[++l_k];

                    String l_removeNeighborCountryId = p_lCommand[++l_k];

                    l_pair.add(l_removeCountryId);

                    l_pair.add(l_removeNeighborCountryId);

                    d_removeCountryIdNeighborCountryIdList.add(l_pair);

                }

                l_k++;

            }
            for(int i=0;i<d_addCountryIdNeighborCountryIdList.size();i++)
            {
                List<String> l_pair = d_addCountryIdNeighborCountryIdList.get(i);
                CommandInterface.addCountryIdNeighborCountryId(l_pair.get(0), l_pair.get(1));
            }
            for(int i=0;i<d_removeCountryIdNeighborCountryIdList.size();i++)
            {
                List<String> l_pair = d_removeCountryIdNeighborCountryIdList.get(i);
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

            String l_countryId = p_lCommand[1];

            int l_numReinforcements = Integer.parseInt(p_lCommand[2]);

            d_countryIdNumList.add(l_countryId);

            d_countryIdNumList.add(p_lCommand[2]);

        } else if (l_mainCommand.equals("savemap")) {//method to savemap
            CommandInterface.saveMap(p_lCommand[1]);

        } else if (l_mainCommand.equals("editmap")) {//method to edit map
            CommandInterface.editMap();

        } else if (l_mainCommand.equals("validatemap")) {//method to validate map
            CommandInterface.validateMap();

        } else if (l_mainCommand.equals("loadmap")) {//method to load map
            CommandInterface.loadCurrentMap(p_lCommand[1]);
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
