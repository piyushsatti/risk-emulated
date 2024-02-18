package controller.commands;

import controller.GameEngine;
import utils.exceptions.*;
import views.TerminalRenderer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class CommandValidator {

    private static final HashMap<String, List<String>> d_commandGamePhaseMap;  //saves phase of game as key and valid command for that game phase as value

    private static final List<String> d_validCommandList; //list of valid commands

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
                                "showmap", "gameplayer","deploy","loadmap"
                        )
                )
        );

        d_commandGamePhaseMap.put(
                "MAIN_MENU",
                new ArrayList<>(
                        List.of(
                                "loadmap"
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
     * This method acts as an entry point to check the validity of the command entered by the user
     * @param p_entered_command : command entered by user
     * @throws InvalidCommandException : when entered command is not valid
     */
    public void addCommand(String p_entered_command) throws InvalidCommandException {

        checkCommandValidity(p_entered_command);

    }
    /**
     * this method checks if the command entered is of valid format or not.
     * @param p_enteredCommand : command entered by the user
     * @throws InvalidCommandException : when entered command is invalid
     * @throws NumberFormatException : when any required integer parameter is found to be of another data type
     */
    private void checkCommandValidity(String p_enteredCommand) throws InvalidCommandException, NumberFormatException {


        if (p_enteredCommand.isEmpty()) throw new InvalidCommandException("empty command entered");

        p_enteredCommand = p_enteredCommand.trim();

        d_command = p_enteredCommand.split("\\s+");

        String l_mainCommand = d_command[0];

        if (!d_validCommandList.contains(l_mainCommand)) { //checking if the entered command is not present in the valid commands list

            throw new InvalidCommandException("invalid command entered");

        } else if (!d_commandGamePhaseMap.get(GameEngine.CURRENT_GAME_PHASE.toString()).contains(l_mainCommand)) { //checking if the entered command is valid for the current game phase

            throw new InvalidCommandException("entered command " + l_mainCommand + "  invalid for this gamephase");

        } else if ((l_mainCommand.equals("savemap") || l_mainCommand.equals("editmap") || l_mainCommand.equals("loadmap")) && d_command.length != 2) { //checking if the command is one of samemap, editmap and loadmap and if it is of length 2

            throw new InvalidCommandException("incorrect format, enter command followed by filename");

        } else if (l_mainCommand.equals("deploy") && d_command.length != 3) { //checking if the command is deploy and the length is 3

            throw new InvalidCommandException("invalid command,enter: deploy countryId num");

        } else if ((l_mainCommand.equals("showmap") || l_mainCommand.equals("validatemap")) && d_command.length != 1) { //checking if the command entered us showmap,validatemap and of length 1

            throw new InvalidCommandException("invalid command, must not have options or parameters");

        }
        //after we have checked if the command is valid for current gamephase and of proper length, we further check its validity
        switch (l_mainCommand) {
            case "editcountry", "editcontinent", "editneighbor" -> this.checkEditCommandsValidity(d_command);
            case "gameplayer" -> this.checkGamePlayerCommandValidity(d_command);
            case "deploy" -> this.checkDeployCommandValidity(d_command);
            default -> throw new IllegalStateException("Unexpected value: " + l_mainCommand);
        }
    }

    /**
     * this method checks if the deploy command is valid or not, no exception generated means command is valid
     * @param p_lCommand : the command array
     * @throws NumberFormatException : when the number of reinforcements is not an integer
     */
    private void checkDeployCommandValidity(String[] p_lCommand) throws NumberFormatException {
        final int i = Integer.parseInt(p_lCommand[2]);
    }

    /**
     * this method checks the validity of gameplayer command, no exception means that the command is valid
     * @param p_lCommand : command array
     * @throws InvalidCommandException : when entered command is invalid
     */
    private void checkGamePlayerCommandValidity(String[] p_lCommand) throws InvalidCommandException {

        int l_countAddOption = 0;

        int l_countRemoveOption = 0;

        int l_len = p_lCommand.length;

        for (String l_s : p_lCommand) {

            if (l_s.equals("-add")) l_countAddOption++;

            else if (l_s.equals("-remove")) l_countRemoveOption++;

        }
        //we first checked if the command has add or remove options, now if both add or remove options were not entered by the user, the command is invalid

        if (l_countAddOption == 0 && l_countRemoveOption == 0) {

            throw new InvalidCommandException("incorrect command, no options added");

        }

        int l_i=1;

        while (l_i < l_len) {

            String l_currOption = p_lCommand[l_i];

            if (!(l_currOption.equals("-add") || l_currOption.equals("-remove"))) { //checking whether gameplayer keyword is followed by an add/remove option

                throw new InvalidCommandException("invalid command format");

            } else if (l_currOption.equals("-add") && l_i + 1 >= l_len || l_currOption.equals("-remove") && l_i + 1 >= l_len) { //checking if there is one parameter value after add/remove option

                throw new InvalidCommandException("invalid command format");

            } else if (l_currOption.equals("-add") && (p_lCommand[l_i + 1].equals("-add") || p_lCommand[l_i + 1].equals("-remove"))) { //checking if add option is followed by another add/remove options

                throw new InvalidCommandException("invalid command format");

            } else if (l_currOption.equals("-remove") && (p_lCommand[l_i + 1].equals("-add") || p_lCommand[l_i + 1].equals("-remove"))) { //checking if checking if remove options are followed by another add/remove options

                throw new InvalidCommandException("invalid command format");
            }

            l_i+=2;

        }
    }

    /**
     * this method checks the validity of the editcountry, editcontinent and editneighbor commands, if there is no exception generated, that means the entered command is valid
     * @param p_lCommand :entered command
     * @throws InvalidCommandException : when entered command is not valid
     */
    private void checkEditCommandsValidity(String[] p_lCommand) throws InvalidCommandException {

        int l_countAddOption = 0;

        int l_countRemoveOption = 0;

        int l_len = p_lCommand.length;
        //checking how many add/remove options are there in the command
        for (String l_s : p_lCommand) {

            if (l_s.equals("-add")) l_countAddOption++;

            else if (l_s.equals("-remove")) l_countRemoveOption++;

        }
        //if there are no add/remove options in the command, the entered command is invalid
        if (l_countAddOption == 0 && l_countRemoveOption == 0) {

            throw new InvalidCommandException("no options added");

        }

        int l_i = 1;

        String l_mainCommand = p_lCommand[0];
        //iterating over the command array to check the validity of the entered command
        while (l_i < l_len) {
            //if the edit command is not immediately followed by an add or remove option, the entered command is invalid
            if (!(p_lCommand[l_i].equals("-add") || p_lCommand[l_i].equals("-remove"))) {

                throw new InvalidCommandException("invalid command format");

            }
            //if after add option, there are not two parameters present, entered command is invalid
            if (p_lCommand[l_i].equals("-add") && l_i + 2 >= l_len) {

                throw new InvalidCommandException("invalid command format");

            } else if (p_lCommand[l_i].equals("-remove") && (l_mainCommand.equals("editcontinent") || l_mainCommand.equals("editcountry")) && l_i + 1 >= l_len) {

                throw new InvalidCommandException("invalid command format"); //in case of editcontinent and editcountry commands, there must be one parameter after after remove option, unless the command is invalid

            } else if (p_lCommand[l_i].equals("-remove") && l_mainCommand.equals("editneighbor") && l_i + 2 >= l_len) {
                throw new InvalidCommandException("invalid command format"); //in case of editneighbor commands, there must be two parameters after after remove option, unless the command is invalid
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
     * this method is used to call further methods which would execute the valid commands
     * @throws NumberFormatException : when number of reinforcements in deploy command is not of integer type
     * @throws FileNotFoundException : when map file is not found
     */
    public void processValidCommand() throws NumberFormatException, IOException, ContinentDoesNotExistException, CountryDoesNotExistException, PlayerDoesNotExistException, ContinentAlreadyExistsException, InvalidMapException {

        String[] p_lCommand = d_command;

        String l_mainCommand = p_lCommand[0];

        int l_len = p_lCommand.length;

        if (l_mainCommand.equals("showmap") && (GameEngine.CURRENT_GAME_PHASE == GameEngine.GAME_PHASES.MAP_EDITOR)) {

            TerminalRenderer.showMap(true); // calling method to show all continents, countries and their neighbors

        } else if (l_mainCommand.equals("showmap") && (GameEngine.CURRENT_GAME_PHASE == GameEngine.GAME_PHASES.GAMEPLAY)) {
            TerminalRenderer.showCurrentGameMap(); //calling method to show all countries, continents, armies on each country, ownership, connecitvity
        }

        switch (l_mainCommand) {

            case "editcontinent" -> {

                d_addContinentIdContinentValList = new ArrayList<>(); //list to store continent id and continent val which need to be added to the map


                d_removeContinentIdList = new ArrayList<>(); //list to store the continent ids of the continents which are to be removed from the map.


                int l_i = 1;
                //we are going to iterate over the command array to store the option parameters in the required lists
                while (l_i < l_len) {

                    String l_currOption = p_lCommand[l_i];

                    if (l_currOption.equals("-add")) {

                        String l_addContinentId = p_lCommand[++l_i];

                        String l_addContinentVal = p_lCommand[++l_i];

                        List<String> l_pair = new ArrayList<>();

                        l_pair.add(l_addContinentId);

                        l_pair.add(l_addContinentVal);

                        d_addContinentIdContinentValList.add(l_pair); //adding the two parameters of the add option in the d_addContinentIdContinentValList

                    } else if (l_currOption.equals("-remove")) {

                        String l_removeContinentId = p_lCommand[++l_i];

                        d_removeContinentIdList.add(l_removeContinentId); //adding the parameter of the remove option to d_removeContinentIdList list

                    }

                    l_i++;

                }
                //now we iterate over the addContinentIdContinentVal and d_removeContinentIdList lists and call methods in the CommandInterface class to process the add/remove parameters
                for (List<String> pair : d_addContinentIdContinentValList) {
                    CommandInterface.addContinentIdContinentVal(pair.get(0), pair.get(1)); //calling method to add new continent and its bonus army value to the map
                }
                for (String s : d_removeContinentIdList) {
                    CommandInterface.removeContinentId(s); //calling method to remove a continent from the map
                }
            }
            case "editcountry" -> {

                d_addCountryIdContinentIdList = new ArrayList<>(); //list to store country id and continent id given as add option parameters


                d_removeCountryIdList = new ArrayList<>(); //list to store country id as remove option parameter


                int l_j = 1;
                //we are now iterating over the command array and adding parameters of add/remove options to the lists d_addCountryIdContinentIdList and d_removeCountryIdList respectively
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
                //iterating over the d_addCountryIdContinentIdList and d_removeCountryIdList lists and calling corresponding methods to process add/remove option parameters
                for (List<String> pair : d_addCountryIdContinentIdList) {
                    CommandInterface.addCountryIdContinentId(pair.get(0), pair.get(1)); //calling method to add country to a particular continent in the map
                }
                for (String s : d_removeCountryIdList) {
                    CommandInterface.removeCountryId(s); //calling method to remove a country from the map
                }
            }
            case "editneighbor" -> {

                d_addCountryIdNeighborCountryIdList = new ArrayList<>(); //list to store add option parameters of the editneighbor command


                d_removeCountryIdNeighborCountryIdList = new ArrayList<>(); //list to store remove option parameters of the editneighbor command


                int l_k = 1;
                //we are now iterating over the command array and adding parameters of add/remove options to the lists d_addCountryIdNeighborCountryIdList and d_removeCountryIdNeighborCountryIdList respectively
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
                //iterating over d_addCountryIdNeighborCountryIdList and d_removeCountryIdNeighborCountryIdList lists to call addCountryIdNeighborCountryId() and d_removeCountryIdNeighborCountryIdList() methods
                for (List<String> l_pair : d_addCountryIdNeighborCountryIdList) {
                    CommandInterface.addCountryIdNeighborCountryId(l_pair.get(0), l_pair.get(1)); //calling method to add country and its neighbor to the map
                }
                for (List<String> l_pair : d_removeCountryIdNeighborCountryIdList) {
                    CommandInterface.removeCountryIdNeighborCountryId(l_pair.get(0), l_pair.get(1)); //calling method to remove country and neighboring country from the map
                }
            }
            case "gameplayer" -> {

                d_playersToAdd = new ArrayList<>(); //list to store names of players to finally add in the player list

                d_playersToRemove = new ArrayList<>(); //list to store names of players to finally remove from the player list


                int l_z = 1;
                //iterating over the command to add parameters of add/remove options to d_playersToAdd and d_playersToRemove lists respectively
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
                for (String s : d_playersToAdd) {
                    CommandInterface.addPlayers(s); //for each player name, calling method to add this player to the player list in the game engine class
                }
                int l_size = d_playersToRemove.size();
                List<String> l_copyList = new ArrayList<>(d_playersToRemove);
                for (int i = 0; i < l_size; i++) {
                    CommandInterface.removePlayers(l_copyList.get(i)); //for each player name, calling method to remove this player from the player list in the game engine class
                }
            }
            case "deploy" -> {

                d_countryIdNumList = new ArrayList<>(); //list to store country id at 0th index and corresponding number of reinforcement at 1st index


                String l_countryId = p_lCommand[1];

                int l_numReinforcements = Integer.parseInt(p_lCommand[2]); //if the number of reinforcements is not an integer, NumberFormatException will be thrown


                d_countryIdNumList.add(l_countryId);

                d_countryIdNumList.add(p_lCommand[2]);
            }
            case "savemap" -> CommandInterface.saveMap(p_lCommand[1]); //calling method to savemap
            case "editmap" ->  //calling method to edit map
                    CommandInterface.editMap(p_lCommand[1]);
            case "validatemap" ->  //calling method to validate map
                    CommandInterface.validateMap();
            case "loadmap" ->  //calling method to load map
                    CommandInterface.loadCurrentMap(p_lCommand[1]);
        }
    }

    /**
     * main method
     * @param args Terminal args.
     * @throws InvalidCommandException Throws InvalidCommandException
     */
    public static void main(String[] args) throws InvalidCommandException {

        System.out.println("enter command:");

        Scanner sc = new Scanner(System.in);

        String l_command = sc.nextLine();

        CommandValidator commandValidator = new CommandValidator();

        commandValidator.addCommand(l_command);

    }

}
