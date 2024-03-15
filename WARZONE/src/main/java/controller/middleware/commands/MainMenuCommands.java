package controller.middleware.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainMenuCommands extends Commands {
    public MainMenuCommands(String p_command) {
        super(p_command, new String[]{
                "loadmap"
        });
    }

    @Override
    public boolean validateCommand() {
        Pattern pattern = Pattern.compile(
                "^loadmap\\s\\w+\\.map$"
        );
        Matcher matcher = pattern.matcher(d_command);
        return matcher.matches();
    }
}