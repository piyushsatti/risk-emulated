package controller.middleware.commands;

import controller.GameEngine;
import controller.MapInterface;
import helpers.exceptions.ContinentAlreadyExistsException;
import helpers.exceptions.ContinentDoesNotExistException;
import helpers.exceptions.CountryDoesNotExistException;
import helpers.exceptions.DuplicateCountryException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StartupCommands extends Commands {

    public StartupCommands(String p_command) {
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

        if (!this.validateCommandName()) {
            ge.d_renderer.renderError("InvalidCommandException : Invalid Command: " + this.d_command.split(" ")[0]);
        }

        String[] l_command = d_command.split(" ");

        switch (l_command[0]) {
            case "assigncountries":
                ge.d_renderer.showMap(false);
                break;
        }
    }

    private void assignCountries(GameEngine ge){

    }
}
