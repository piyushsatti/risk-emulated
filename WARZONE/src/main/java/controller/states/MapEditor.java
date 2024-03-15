package controller.states;

import controller.GameEngine;
import controller.MapInterface;
import controller.commands.CommandValidator;
import helpers.exceptions.*;
import views.TerminalRenderer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class MapEditor extends State {
    public MapEditor(GameEngine g) {
        super(g);
        this.d_stateName = "Map Editor";
    }

    @Override
    public void displayMenu() {
        TerminalRenderer.renderMessage("current game phase: " + this.d_stateName);
    }

    @Override
    public String userInput() {
        return null;
    }

    @Override
    public void next() {

    }

    @Override
    public void endGame() {

    }

    @Override
    public void run() {
        displayMenu();
        processInput();
    }

    @Override
    public void processInput() {
        if (GameEngine.CURRENT_MAP == null) {
            promptUserForMap();
        } else {
            promptUserForEditorCommand();
        }

    }

    public void promptUserForMap() {

        String l_filename;

        l_filename = TerminalRenderer.renderMapEditorMenu();

        try {

            GameEngine.CURRENT_MAP = MapInterface.loadMap(l_filename);

        } catch (FileNotFoundException | NumberFormatException | InvalidMapException e) {

            TerminalRenderer.renderError("Invalid File or not found");

        }
    }


    public void promptUserForEditorCommand() {

        String input_command;

        CommandValidator command;

        input_command = TerminalRenderer.renderMapEditorCommands();

        if (input_command.equals("exit")) {

            ge.setCurrentState(new Starting(ge));
            return;
        }

        command = new CommandValidator();

        try {

            command.addCommand(input_command);

            command.processValidCommand();

        } catch (InvalidCommandException | CountryDoesNotExistException | ContinentAlreadyExistsException |
                 ContinentDoesNotExistException | IOException | PlayerDoesNotExistException |
                 InvalidMapException |
                 DuplicateCountryException e) {

            TerminalRenderer.renderError("Invalid Command Entered: " + input_command + "\n" + e);

        }
    }


}







