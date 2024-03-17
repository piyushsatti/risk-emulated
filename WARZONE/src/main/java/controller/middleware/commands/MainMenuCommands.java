package controller.middleware.commands;

import controller.GameEngine;
import controller.MapInterface;
import helpers.exceptions.*;

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

        if (!this.validateCommandName() ) {
            ge.d_renderer.renderError("InvalidCommandException : Invalid Command.");
            return;
        }
        else if(!this.validateCommand()){
            ge.d_renderer.renderError("InvalidCommandException : Invalid Command Format for: " + this.d_command.split("\\s+")[0]);
            return;
        }

        String[] l_command = d_command.trim().split("\\s+");

        switch (l_command[0]) {
            case "loadmap":
                try {
                    MapInterface.loadMap2(ge, l_command[1]);
                } catch (FileNotFoundException e) {
                    ge.d_renderer.renderError("FileNotFoundException : File does not exist.");
                } catch (NumberFormatException e) {
                    ge.d_renderer.renderError("NumberFormatException : File has incorrect formatting.");
                }  catch (CountryDoesNotExistException e) {
                    throw new RuntimeException(e);
                } catch (ContinentAlreadyExistsException e) {
                    throw new RuntimeException(e);
                } catch (ContinentDoesNotExistException e) {
                    throw new RuntimeException(e);
                } catch (DuplicateCountryException e) {
                    throw new RuntimeException(e);
                }
        }
    }
}
