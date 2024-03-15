package controller.middleware.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GamePlayCommands extends Commands{
    public GamePlayCommands(String p_command) {
        super(p_command, new String[]{
                "loadmap",
                "gameplayer",
                "assigncountries",
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
                "^loadmap\\s\\w+\\.map$|"+
                        "^assigncountries$|"+
                        "^gameplayer(?:\\s+-add\\s+\\w+)*(?:\\s+-remove\\s+\\w+)*$|"+
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
