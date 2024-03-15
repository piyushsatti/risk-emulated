package controller.middleware.commands;

public abstract class Commands {
    String d_command;
    String[] d_valid_commands;

    public Commands(String p_command,String[] p_valid_commands)
    {
        d_command = p_command;
        d_valid_commands = p_valid_commands;
    }
    abstract boolean validateCommand();
}
