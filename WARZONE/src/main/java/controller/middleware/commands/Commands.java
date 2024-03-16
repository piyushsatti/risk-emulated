package controller.middleware.commands;

import controller.GameEngine;

public abstract class Commands {
    public String[] d_valid_commands;
    final String d_command;

    public Commands(String p_command,String[] p_valid_commands)
    {
        d_command = p_command;
        d_valid_commands = p_valid_commands;
    }

    abstract boolean validateCommand();

    abstract void execute(GameEngine ge);


    /**
     * This method will verify that the command name is valid
     * checking that the entered string's first token is one of the
     * entries in the d_valid_commands
     * @return true if one of the options, false if it is not
     */
    boolean validCommand(){
        for(String s : d_valid_commands){ //loop through valid commands
            if(s.split(" ")[0].equals(d_command)){ //if you have a match
                return true;
            }
        }
        return false;
    }

}
