package controller.middleware.commands;

import controller.GameEngine;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GamePlayCommands extends Commands {

    public GamePlayCommands(String p_command) {
        super(p_command, new String[]{
                "loadmap",
                "gameplayer",
                "assigncountries"
        });
    }

    @Override
    public boolean validateCommand()
    {
        Pattern pattern = Pattern.compile(
                "^loadmap\\s\\w+\\.map(\\s)*$|"+
                        "^assigncountries(\\s)*$|"+
                        "^gameplayer(?:(?:\\s+-add\\s+\\w+)*(?:\\s+-remove\\s+\\w+)*)*(\\s)*$"
        );
        Matcher matcher = pattern.matcher(d_command);
        return matcher.matches();
    }

    @Override
    void execute(GameEngine ge) {

    }
}
