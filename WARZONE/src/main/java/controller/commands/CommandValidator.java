package controller.commands;

import controller.GameEngine;
import helpers.exceptions.*;
import view.TerminalRenderer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

/**
 * The CommandValidator class validates user commands and processes valid commands
 * based on the current game phase. It ensures that the commands entered by the user
 * are valid and appropriate for the current phase of the game. It also processes
 * the valid commands by calling appropriate methods to execute them.
 */
public class CommandValidator {

    /**
     * Map that stores valid commands for each game phase.
     */
    private static final HashMap<String, List<String>> d_commandGamePhaseMap;  //saves phase of game as key and valid command for that game phase as value

    private static final HashMap<String,String> cmdErrorMap;

    private static final HashMap<String,Integer> cmdLenMap;

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

        cmdLenMap = new HashMap<>();
        cmdLenMap.put("showmap",1);
        cmdLenMap.put("validatemap",1);
        cmdLenMap.put("assigncountries",1);
        cmdLenMap.put("savemap",2);
        cmdLenMap.put("editmap",2);
        cmdLenMap.put("loadmap",2);
        cmdLenMap.put("bomb",2);
        cmdLenMap.put("blockade",2);
        cmdLenMap.put("negotiate",2);
        cmdLenMap.put("deploy",3);
        cmdLenMap.put("advance",4);
        cmdLenMap.put("airlift",4);

        cmdErrorMap = new HashMap<>();
        cmdErrorMap.put("editcontinent","invalid command format. Enter: editcontinent -add continentID continentvalue -remove continentID" +
                "\n Note: enter atleast one -add/-remove option & each can be used multiple times");
        cmdErrorMap.put("editcountry","invalid command format. Enter: editcountry -add countryID continentID -remove countryID"+"" +
                "\n Note: enter atleast one -add/-remove option & each can be used multiple times");
        cmdErrorMap.put("editneighbor","invalid command format. Enter: editneighbor -add countryID neighborcountryID -remove countryID neighborcountryIDshowmap"+
                "\n Note: enter atleast one -add/-remove option & each can be used multiple times");
        cmdErrorMap.put("gameplayer","invalid command format. Enter: gameplayer -add playername -remove playername"+
                "\n Note: enter atleast one -add/-remove option & each can be used multiple times");
        cmdErrorMap.put("showmap","invalid command format. Enter: showmap");
        cmdErrorMap.put("savemap","invalid command format. Enter: savemap filename");
        cmdErrorMap.put("editmap","invalid command format. Enter: editmap filename");
        cmdErrorMap.put("validatemap","invalid command format. Enter: validatemap");
        cmdErrorMap.put("loadmap","invalid command format. Enter: loadmap filename");
        cmdErrorMap.put("assigncountries","invalid command format. Enter: assigncountries");
        cmdErrorMap.put("deploy","invalid command format. Enter: deploy countryID numarmies");
        cmdErrorMap.put("advance","invalid command format. Enter: advance countrynamefrom countynameto numarmies ");
        cmdErrorMap.put("bomb","invalid command format. Enter: deploy bomb countryID ");
        cmdErrorMap.put("blockade","invalid command format. Enter: blockade countryID ");
        cmdErrorMap.put("airlift","invalid command format. Enter: airlift sourcecountryID targetcountryID numarmies ");
        cmdErrorMap.put("negotiate","invalid command format. Enter: negotiate playerID");

    }

    /**
     * List to store country ID and number of reinforcements pairs for deployment.
     */
    List<String> d_countryIdNumList;

    /**
     * Array to store the entered command split by whitespaces.
     */
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

        int l_len = d_command.length;

        if (!cmdErrorMap.containsKey(l_mainCommand)) { //checking if the entered command is not present in the valid commands list
            throw new InvalidCommandException("entered command is invalid!, try with a valid command");
        } else if (!d_commandGamePhaseMap.get(GameEngine.CURRENT_GAME_PHASE.toString()).contains(l_mainCommand)) { //checking if the entered command is valid for the current game phase
            throw new InvalidCommandException("entered command " + l_mainCommand + "  invalid for this gamephase");
        } else if (cmdLenMap.containsKey(l_mainCommand) && l_len!=cmdLenMap.get(l_mainCommand)) {
            throw new InvalidCommandException(cmdErrorMap.get(l_mainCommand));
        }
        //after we have checked if the command is valid for current gamephase and of proper length, we further check its validity
        switch (l_mainCommand) {
            case "editcountry", "editcontinent", "editneighbor" -> this.checkEditCommandsValidity(d_command);
            case "gameplayer" -> this.checkGamePlayerCommandValidity(d_command);
            case "deploy","advance","airlift" -> this.checkNumArmiesType(d_command[l_len-1]);
            case "showmap","savemap", "editmap", "loadmap","validatemap","assigncountries","bomb","blockade","negotiate" -> {
                break;
            }
            default -> throw new InvalidCommandException(l_mainCommand+"is not a valid command");
        }
    }

    protected void checkNumArmiesType(String p_numArmies) throws NumberFormatException {
        try {
            final int l_i = Integer.parseInt(p_numArmies);
        }
        catch (NumberFormatException e)
        {
            throw new NumberFormatException("invalid command format, number of armies/bonus armies must be an integer");
        }
        int l_j = Integer.parseInt(p_numArmies);
        if(l_j<0 || l_j==0) throw new NumberFormatException("cannot enter negative numbers/zero for number of armies/bonus armies");
    }

    /**
     * this method checks the validity of gameplayer command, no exception means that the command is valid
     * @param p_lCommand : command array
     * @throws InvalidCommandException : when entered command is invalid
     */
    protected void checkGamePlayerCommandValidity(String[] p_lCommand) throws InvalidCommandException {
        int l_len = p_lCommand.length;
        int l_i=1;
        while (l_i < l_len) {
            String l_currOption = p_lCommand[l_i];
            if (!(l_currOption.equals("-add") || l_currOption.equals("-remove"))) { //checking whether gameplayer keyword is followed by an add/remove option
                throw new InvalidCommandException(cmdErrorMap.get("gameplayer"));
            }
            else if (l_currOption.equals("-add") && l_i + 1 >= l_len || l_currOption.equals("-remove") && l_i + 1 >= l_len) { //checking if there is one parameter value after add/remove option

                throw new InvalidCommandException("invalid command format, -add/-remove should be followed by a player name");

            } else if (l_currOption.equals("-add") && (p_lCommand[l_i + 1].equals("-add") || p_lCommand[l_i + 1].equals("-remove"))) { //checking if add option is followed by another add/remove options

                throw new InvalidCommandException("invalid command format. -add should not be immediately followed by -add/-remove");

            } else if (l_currOption.equals("-remove") && (p_lCommand[l_i + 1].equals("-add") || p_lCommand[l_i + 1].equals("-remove"))) { //checking if checking if remove options are followed by another add/remove options

                throw new InvalidCommandException("invalid command format, -remove should not be immediately followed by -add/-remove");
            }
            l_i+=2;
        }
    }

    /**
     *this method checks if the editcountry,editcontinent and editneighbor commands are valid wrt parameters added after the '-add' option
     * @param p_lCommand  entered command
     * @param p_currIndex current index
     * @throws InvalidCommandException when the command entered is of invalid format
     * @throws NumberFormatException when the continentval in the editcontinent command is not an integer
     */
    public void checkEditCommandAddValidity(String[] p_lCommand,int p_currIndex) throws InvalidCommandException,NumberFormatException
    {
        int l_len = p_lCommand.length;
        String l_mainCommand = p_lCommand[0];
        //if after add option, there are not two parameters present, entered command is invalid
        if (p_lCommand[p_currIndex].equals("-add") && p_currIndex + 2 >= l_len) {
            throw new InvalidCommandException("invalid command format, -add should be followed by two parameters");
        } //checking if -add is followed by another -add/-remove
        else if(p_lCommand[p_currIndex].equals("-add") && (p_lCommand[p_currIndex+1].equals("-add") || p_lCommand[p_currIndex+1].equals("-remove")))
        {
            throw new InvalidCommandException("invalid command format, -add cannot be immediately followed by another -add/-remove");
        }
        else if(p_lCommand[p_currIndex].equals("-add") && l_mainCommand.equals("editcontinent")) //checking if continent value is an integer
        {
            String continentVal = p_lCommand[p_currIndex + 2];
            checkNumArmiesType(continentVal);
        }
    }

    /**
     * this method checks if the editcountry,editcontinent and editneighbor commands are valid wrt parameters added after the '-remove' option
     * @param p_lCommand
     * @param p_currIndex
     * @throws InvalidCommandException
     * @throws NumberFormatException
     */
    public void checkEditCommandRemoveValidity(String[] p_lCommand,int p_currIndex) throws InvalidCommandException,NumberFormatException
    {
        int l_len = p_lCommand.length;
        String l_mainCommand = p_lCommand[0];
        if (p_lCommand[p_currIndex].equals("-remove") && (l_mainCommand.equals("editcontinent") || l_mainCommand.equals("editcountry")) && p_currIndex + 1 >= l_len) {
            //in case of editcontinent and editcountry commands, there must be one parameter after -remove option
            throw new InvalidCommandException("invalid command format, -remove in editcontinent/editcountry commands should be followed by one parameters");

        }
        else if(p_lCommand[p_currIndex].equals("-remove") && (p_lCommand[p_currIndex].equals("-remove") || p_lCommand[p_currIndex].equals("-add")))
        {
            throw new InvalidCommandException("invalid command format, -remove cannot be immediately followed by another -add/-remove");
        }
        else if (p_lCommand[p_currIndex].equals("-remove") && l_mainCommand.equals("editneighbor") && p_currIndex + 2 >= l_len) {
            //in case of editneighbor commands, there must be two parameters after -remove option
            throw new InvalidCommandException("invalid command format,-remove in editneighbor command should be followed by two parameters");
        }
    }
    /**
     * this method checks the validity of the editcountry, editcontinent and editneighbor commands, if there is no exception generated, that means the entered command is valid
     * @param p_lCommand :entered command
     * @throws InvalidCommandException : when entered command is not valid
     */
    protected void checkEditCommandsValidity(String[] p_lCommand) throws InvalidCommandException,NumberFormatException {
        int l_len = p_lCommand.length;
        int l_currIndex = 1;
        String l_mainCommand = p_lCommand[0];
        //iterating over the command array to check the validity of the entered command
        while (l_currIndex < l_len) {

            if (!(p_lCommand[l_currIndex].equals("-add") || p_lCommand[l_currIndex].equals("-remove"))) {

                throw new InvalidCommandException(cmdErrorMap.get(l_mainCommand));

            }
            checkEditCommandAddValidity(p_lCommand,l_currIndex);
            checkEditCommandRemoveValidity(p_lCommand,l_currIndex);

            if (p_lCommand[l_currIndex].equals("-add")) {

                l_currIndex += 3;

            } else if ((l_mainCommand.equals("editcontinent") || l_mainCommand.equals("editcountry")) && p_lCommand[l_currIndex].equals("-remove")) {

                l_currIndex += 2;

            } else if (l_mainCommand.equals("editneighbor") && p_lCommand[l_currIndex].equals("-remove")) {

                l_currIndex += 3;

            }
        }
    }

    List<List<String>> d_addPair;
    List<String> d_removeList;
    List<List<String>> d_removePair;
    List<String> d_addList;

    protected void genAddRemoveLists(String[] p_lCommand)
    {
        String l_mainCommand = p_lCommand[0];

        if(l_mainCommand.equals("gameplayer")) d_addList = new ArrayList<>();
        else d_addPair = new ArrayList<>();

        if(l_mainCommand.equals("editneighbor")) d_removePair = new ArrayList<>();
        else d_removeList = new ArrayList<>();

        int l_len = p_lCommand.length;
        int l_i = 1;
        //we are going to iterate over the command array to store the option parameters in the required lists
        while (l_i < l_len) {
            String l_currOption = p_lCommand[l_i];
            if (l_currOption.equals("-add")) {
                String l_parameter1 = p_lCommand[++l_i];
                if(l_mainCommand.equals("gameplayer") && !d_addList.contains(l_parameter1))
                {
                    d_addList.add(l_parameter1);
                }
                else {
                    String l_parameter2 = p_lCommand[++l_i];
                    List<String> l_pair = new ArrayList<>();
                    l_pair.add(l_parameter1);
                    l_pair.add(l_parameter2);

                    d_addPair.add(l_pair); //adding the two parameters of the add option in the d_addContinentIdContinentValList
                }
            }
            else
            {
                String l_parameter1 = p_lCommand[++l_i];
                if(l_mainCommand.equals("editneighbor"))
                {
                    String l_parameter2 = p_lCommand[++l_i];
                    List<String> pair = new ArrayList<>();
                    pair.add(l_parameter1);
                    pair.add(l_parameter2);
                    d_removePair.add(pair);
                }
                else if(l_mainCommand.equals("gameplayer") && !d_removeList.contains(l_parameter1)) d_removeList.add(l_parameter1);
                else d_removeList.add(l_parameter1); //adding the parameter of the remove option to d_removeContinentIdList list
            }
            l_i++;
        }
    }
    /**
     * this method is used to call further methods which would execute the valid commands
     * @throws NumberFormatException : when number of reinforcements in deploy command is not of integer type
     * @throws FileNotFoundException : when map file is not found
     */
    public void processValidCommand() throws NumberFormatException, IOException, ContinentDoesNotExistException, CountryDoesNotExistException, PlayerDoesNotExistException, ContinentAlreadyExistsException, InvalidMapException, DuplicateCountryException {

        String[] p_lCommand = d_command;

        String l_mainCommand = p_lCommand[0];

        if (l_mainCommand.equals("showmap") && (GameEngine.CURRENT_GAME_PHASE == GameEngine.GAME_PHASES.MAP_EDITOR)) {

            TerminalRenderer.showMap(true); // calling method to show all continents, countries and their neighbors

        } else if (l_mainCommand.equals("showmap") && (GameEngine.CURRENT_GAME_PHASE == GameEngine.GAME_PHASES.GAMEPLAY)) {
            TerminalRenderer.showCurrentGameMap(); //calling method to show all countries, continents, armies on each country, ownership, connecitvity
        }
        switch (l_mainCommand) {
            case "editcontinent" -> {
                genAddRemoveLists(p_lCommand);
                for (List<String> pair : d_addPair) {
                    CommandInterface.addContinentIdContinentVal(pair.get(0), pair.get(1)); //calling method to add new continent and its bonus army value to the map
                }
                for (String s : d_removeList) {
                    CommandInterface.removeContinentId(s); //calling method to remove a continent from the map
                }
            }
            case "editcountry" -> {
                genAddRemoveLists(p_lCommand);
                for (List<String> pair : d_addPair) {
                    CommandInterface.addCountryIdContinentId(pair.get(0), pair.get(1)); //calling method to add country to a particular continent in the map
                }
                for (String s : d_removeList) {
                    CommandInterface.removeCountryId(s); //calling method to remove a country from the map
                }
            }
            case "editneighbor" -> {
                genAddRemoveLists(p_lCommand);
                for (List<String> l_pair : d_addPair) {
                    CommandInterface.addCountryIdNeighborCountryId(l_pair.get(0), l_pair.get(1)); //calling method to add country and its neighbor to the map
                }
                for (List<String> l_pair : d_removePair) {
                    CommandInterface.removeCountryIdNeighborCountryId(l_pair.get(0), l_pair.get(1)); //calling method to remove country and neighboring country from the map
                }
            }
            case "gameplayer" -> {
                genAddRemoveLists(p_lCommand);
                CommandInterface.addPlayers(d_addList);
                CommandInterface.removePlayers(d_removeList);
            }
            case "deploy" -> {
                d_countryIdNumList = new ArrayList<>(); //list to store country id at 0th index and corresponding number of reinforcement at 1st index
                d_countryIdNumList.add(p_lCommand[1]); //adding country id
                d_countryIdNumList.add(p_lCommand[2]); //adding number of armies
            }
            case "savemap" -> CommandInterface.saveMap(p_lCommand[1]); //calling method to savemap
            case "editmap" ->  //calling method to edit map
                    CommandInterface.editMap(p_lCommand[1]);
            case "validatemap" ->  //calling method to validate map
                    CommandInterface.validateMap();
            case "loadmap" ->  //calling method to load map
                    CommandInterface.loadCurrentMap(p_lCommand[1]);
            case "advance" -> System.out.println("need method call");
            case "bomb" -> System.out.println("need method call");
            case "blockade" -> System.out.println("need method call");
            case "airlift" -> System.out.println("need method call");
            case "negotiate" -> System.out.println("need method call");
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
