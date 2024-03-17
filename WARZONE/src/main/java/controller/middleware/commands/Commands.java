package controller.middleware.commands;

import controller.GameEngine;
import helpers.exceptions.CountryDoesNotExistException;
import helpers.exceptions.InvalidCommandException;

/**
 * The abstract class Commands represents a command with its associated valid commands.
 *
 */
public abstract class Commands {
    private String[] d_valid_commands;

    public String[] splitCommand;
    final String d_command;

    /**
     * Constructs object Commands with the given command string and valid commands.
     *
     * @param p_command The command string.
     * @param p_valid_commands An array containing valid commands.
     */
    public Commands(String p_command,String[] p_valid_commands)
    {
        d_command = p_command;
        splitCommand = p_command.split(" ");
        d_valid_commands = p_valid_commands;
    }

    /**
     * Validates the command
     *
     * @return True if the command is valid, false otherwise.
     */
    abstract boolean validateCommand();

    /**
     * Executes the command using the provided GameEngine.
     *
     * @param ge The GameEngine object used to execute the command.
     * @throws CountryDoesNotExistException If a country referenced in the command does not exist.
     * @throws InvalidCommandException     If the command is invalid.
     */
    abstract void execute(GameEngine ge) throws CountryDoesNotExistException, InvalidCommandException;

    /**
     * This validation method only checks if the first token
     * is a valid command name (doesn't check the rest of the command)
     *
     * @return true if valid name, false if not
     */
    boolean validateCommandName(){
        for(String l_cmd : d_valid_commands){
            if(d_command.split("\\s+")[0].equals(l_cmd)){
                return true;
            }
        }
        return false;
    }
}
