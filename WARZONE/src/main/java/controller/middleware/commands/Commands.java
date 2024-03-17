package controller.middleware.commands;

import controller.GameEngine;

public abstract class Commands {
    private String[] d_valid_commands;

    public String[] splitCommand;
    final String d_command;

    public Commands(String p_command,String[] p_valid_commands)
    {
        d_command = p_command;
        splitCommand = p_command.split(" ");
        d_valid_commands = p_valid_commands;
    }

    abstract boolean validateCommand();

    abstract void execute(GameEngine ge);

    /**
     * This validation method only checks if the first token
     * is a valid command name (doesn't check the rest of the command)
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
