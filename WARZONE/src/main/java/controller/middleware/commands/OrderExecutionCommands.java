package controller.middleware.commands;

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
                        "^deploy\\s+\\w+\\s+\\d+$|"+
                        "^advance\\s+\\w+\\s+\\w+\\s+\\d+$|"+
                        "^bomb\\s+\\w+\\s*$|"+
                        "^blockade\\s+\\w+\\s*$|"+
                        "^negotiate\\s+\\w+\\s*$|"+
                        "^airlift\\s+\\w+\\s+\\w+\\s+\\d+$"
        );
        Matcher matcher = pattern.matcher(d_command);
        return matcher.matches();
    }
}
