package controller.middleware.commands;

import controller.GameEngine;
import controller.MapInterface;
import helpers.exceptions.*;

import java.io.FileNotFoundException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The MainMenuCommands class represents commands related to the main menu of the game.
 * It extends the abstract class Commands and provides functionality to handle various main menu commands.
 */
public class MainMenuCommands extends Commands {
    /**
     * Constructs a MainMenuCommands object with the given command string.
     *
     * @param p_command The command string.
     */
    public MainMenuCommands(String p_command) {
        super(p_command, new String[]{
                "loadmap"
        });
    }

    /**
     * Validates the command format for the main menu commands.
     *
     * @return True if the command format is valid, false otherwise.
     */
    @Override
    public boolean validateCommand() {
        Pattern pattern = Pattern.compile(
                "^loadmap\\s\\w+\\.map(\\s)*$"
        );
        Matcher matcher = pattern.matcher(d_command);
        return matcher.matches();
    }

    /**
     * Executes the command using the provided GameEngine.
     *
     * @param p_gameEngine The GameEngine object used to execute the command.
     */
    @Override
    void execute(GameEngine p_gameEngine) {

        if (!this.validateCommandName() ) {
            p_gameEngine.d_renderer.renderError("InvalidCommandException : Invalid Command.");
            return;
        }
        else if(!this.validateCommand()){
            p_gameEngine.d_renderer.renderError("InvalidCommandException : Invalid Command Format for: " + this.d_command.split("\\s+")[0]);
            return;
        }

        String[] l_command = d_command.trim().split("\\s+");

        switch (l_command[0]) {
            case "loadmap":
                try {
                    MapInterface.loadMap2(p_gameEngine, l_command[1]);
                }
                catch (FileNotFoundException e) {
                    p_gameEngine.d_renderer.renderError("FileNotFoundException : File does not exist.");
                }
                catch (NumberFormatException e) {
                    p_gameEngine.d_renderer.renderError("NumberFormatException : File has incorrect formatting.");
                }
                catch (CountryDoesNotExistException e) {
                    throw new RuntimeException(e);
                }
                catch (ContinentAlreadyExistsException e) {
                    throw new RuntimeException(e);
                }
                catch (ContinentDoesNotExistException e) {
                    throw new RuntimeException(e);
                }
                catch (DuplicateCountryException e) {
                    throw new RuntimeException(e);
                }
        }
    }
}
