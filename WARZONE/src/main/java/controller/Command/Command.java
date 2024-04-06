package controller.Command;

import controller.GameEngine;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Command represents an abstract class for all commands in the game.
 * It provides methods for command validation and execution.
 */
public abstract class Command {

    /**
     * The input string for the command.
     */
    protected String d_input;
    /**
     * The valid format of the command.
     */
    protected String d_validCommandFormat;

    /**
     * The pattern to validate the command format.
     */
    protected Pattern d_commandPattern;

    /**
     * The game engine associated with the command.
     */
    protected GameEngine d_ge;

    /**
     * The valid phases in which the command can be executed.
     */
    protected Class[] d_validPhases;
    /**
     * The array containing split parts of the command input.
     */
    public String[] d_splitCommand;

    /**
     * Constructs a Command object with specified input and game engine.
     * @param p_input The input string for the command.
     * @param p_ge The game engine to operate on.
     */
    protected Command(String p_input, GameEngine p_ge){
        d_input = p_input;
        d_ge = p_ge;
        d_splitCommand = d_input.split("\\s+");
    }

    /**
     * Validates the command by checking its phase, format, and logic.
     * @return true if the command is valid, false otherwise.
     */
    public boolean validate(){
        if(validatePhase() && validateFormat()){
            return validateLogic();
        }else{
            return false;
        }
    }
    /**
     * Executes the command.
     */
    public abstract void execute();

    /**
     * Validates the format of the command based on its pattern.
     * @return true if the format is valid, false otherwise.
     */
    public  boolean validateFormat(){
        Matcher matcher = d_commandPattern.matcher(d_input);
        if(matcher.matches()){
            return true;
        }else{
            invalidFormatMessage();
            return false;
        }
    }
    /**
     * Validates the logic of the command.
     * @return true if the logic is valid, false otherwise.
     */
    public abstract boolean validateLogic();

    /**
     * Validates the phase of the command based on the current game state.
     * @return true if the command is valid for the current phase, false otherwise.
     */
    public boolean validatePhase(){
        for(Class l_c : d_validPhases){
            if(l_c == d_ge.getCurrentState().getClass()) return true;
        }
        this.d_ge.d_renderer.renderError("Invalid command in this phase!");
        return false;
    }

    /**
     * Displays an error message for invalid command format.
     */
    public void invalidFormatMessage(){
        this.d_ge.d_renderer.renderError("Invalid command! Correct format -> " + d_validCommandFormat);
    }


}
