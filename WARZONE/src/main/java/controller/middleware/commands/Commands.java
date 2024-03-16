package controller.middleware.commands;

import controller.GameEngine;

public abstract class Commands {
    private static String[] d_valid_commands;
    final String d_command;

    public Commands(String p_command,String[] p_valid_commands)
    {
        d_command = p_command;
        d_valid_commands = p_valid_commands;
    }

    abstract boolean validateCommand();

    abstract void execute(GameEngine ge);
}
