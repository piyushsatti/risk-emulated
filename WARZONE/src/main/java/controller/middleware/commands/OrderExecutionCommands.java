package controller.middleware.commands;

import controller.GameEngine;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OrderExecutionCommands extends Commands{
    public OrderExecutionCommands(String p_command) {
        super(p_command, new String[]{
                "deploy",
                "advance",
                "bomb",
                "blockade",
                "airlift",
                "negotiate"
        });
    }
    @Override
    public boolean validateCommand()
    {
        Pattern pattern = Pattern.compile(
                "^deploy\\s+\\w+\\s+\\d+(\\s)*$|"+
                        "^advance\\s+\\w+\\s+\\w+\\s+\\d+(\\s)*$|"+
                        "^bomb\\s+\\w+\\s*(\\s)*$|"+
                        "^blockade\\s+\\w+\\s*(\\s)*$|"+
                        "^negotiate\\s+\\w+\\s*(\\s)*$|"+
                        "^airlift\\s+\\w+\\s+\\w+\\s+\\d+(\\s)*$"
        );
        Matcher matcher = pattern.matcher(d_command);
        return matcher.matches();
    }

    @Override
    void execute(GameEngine ge) {
        if (!this.validateCommand()) {
            ge.d_renderer.renderError("InvalidCommandException : Invalid Command Format.");
        }
    }
}
