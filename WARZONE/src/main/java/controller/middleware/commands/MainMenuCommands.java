package controller.middleware.commands;

import controller.GameEngine;
import controller.MapInterface;
import helpers.exceptions.InvalidMapException;

import java.io.FileNotFoundException;
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
                "^loadmap\\s\\w+\\.map(\\s)*$"
        );
        Matcher matcher = pattern.matcher(d_command);
        return matcher.matches();
    }

    @Override
    void execute(GameEngine ge) {

        if (!this.validateCommand()) {
            ge.renderer.renderError("InvalidCommandException : Invalid Command Format.");
        }

        String[] l_command = d_command.trim().split("\\s+");

        switch (l_command[0]) {
            case "loadmap":
                try {
                    MapInterface.loadMap(ge, l_command[1]);
                } catch (FileNotFoundException e) {
                    ge.renderer.renderError("FileNotFoundException : File does not exist.");
                } catch (NumberFormatException e) {
                    ge.renderer.renderError("NumberFormatException : File has incorrect formatting.");
                } catch (InvalidMapException e) {
                    ge.renderer.renderError("InvalidMapException : Map is disjoint or incorrect.");
                }
        }
    }
}