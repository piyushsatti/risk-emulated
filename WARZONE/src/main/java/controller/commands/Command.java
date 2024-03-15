package controller.commands;

import controller.GameEngine;

public abstract class Command {

    GameEngine context;
    String commandName;
    String[] parameters;
    public abstract void validate();
    public abstract void execute();

}
