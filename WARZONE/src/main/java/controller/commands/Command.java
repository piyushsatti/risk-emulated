package controller.commands;

import controller.states.State;

public abstract class Command {

    State commandState;
    String d_commandName;
    public abstract void validate();
    public abstract void execute();



}
