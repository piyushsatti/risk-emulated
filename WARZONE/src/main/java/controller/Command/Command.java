package controller.Command;

import controller.GameEngine;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Command {

    protected String d_input;
    protected String d_validCommandFormat;
    protected Pattern d_commandPattern;
    protected GameEngine d_ge;
    public String[] d_splitCommand;

    protected Command(String p_input){
        d_input = p_input;
        d_splitCommand = d_input.split("\\s+");
    }


    public boolean validate(){
        if(validateFormat()){
            return validateLogic();
        }else{
            return false;
        }
    }
    public abstract void execute();

    public  boolean validateFormat(){
        Matcher matcher = d_commandPattern.matcher(d_input);
        if(matcher.matches()){
            return true;
        }else{
            invalidFormatMessage();
            return false;
        }
    }
    public abstract boolean validateLogic();

    public void invalidFormatMessage(){
        this.d_ge.d_renderer.renderError("Invalid command! Correct format -> " + d_validCommandFormat);
    }

}
