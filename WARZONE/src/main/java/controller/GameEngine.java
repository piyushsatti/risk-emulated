package main.java.controller;

import main.java.controller.commands.CommandValidator;
import main.java.models.Player;
import main.java.models.worldmap.WorldMap;
import main.java.utils.exceptions.*;
import main.java.views.TerminalRenderer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * The GameEngine class manages the main logic of the game, including handling game phases, user input, and game loops.
 * It coordinates between the map editor, gameplay, and other settings.
 */
public class GameEngine {

    /**
     * Enum representing different phases of the game.
     */
    public enum GAME_PHASES {
        MAIN_MENU,
        MAP_EDITOR,
        GAMEPLAY,
        SETTINGS
    }

    /**
     * The current game phase.
     */
    public static GAME_PHASES CURRENT_GAME_PHASE = GAME_PHASES.MAIN_MENU;

    /**
     * The folder where map files are stored.
     */
    public static String MAPS_FOLDER = "WARZONE/src/main/resources/maps/";

    /**
     * The currently loaded map.
     */
    public static WorldMap CURRENT_MAP;

    /**
     * List of players in the game.
     */
    public static ArrayList<Player> PLAYER_LIST = new ArrayList<>();

    static {

        // Load a default map when the class is loaded

        try {

            CURRENT_MAP = MapInterface.loadMap("usa9.map");

        } catch (FileNotFoundException e) {
            System.out.println("Error");
            throw new RuntimeException(e);

        }

    }


    /**
     * Manages the loop for player actions during the gameplay phase.
     *
     * @throws IOException                      if an I/O error occurs.
     * @throws InvalidCommandException if an invalid command is entered.
     * @throws CountryDoesNotExistException     if the country does not exist.
     * @throws ContinentDoesNotExistException   if the continent does not exist.
     * @throws ContinentAlreadyExistsException if the continent already exists.
     * @throws PlayerDoesNotExistException     if the player does not exist.
     */
    public static void playerLoop() throws IOException, InvalidCommandException, CountryDoesNotExistException, ContinentDoesNotExistException, ContinentAlreadyExistsException, PlayerDoesNotExistException {


        TerminalRenderer.renderWelcome();

        String[] menu_options = {"Show Map", "Load Map", "Add/RemovePlayer","Assign Countries"};

        TerminalRenderer.renderMenu(
                "Main Menu",
                menu_options
        );

        Scanner in = new Scanner(System.in);

        String user_in;

        while (true) {

            user_in = in.nextLine();

            if (user_in.strip().replace(" ", "").equalsIgnoreCase("showmap")) {
                TerminalRenderer.showMap(!GameEngine.PLAYER_LIST.isEmpty());
            } else if (user_in.strip().replace(" ", "").equalsIgnoreCase("loadmap")) {
                System.out.println("Enter command-> loadmap followed by filename.map");
                String input = in.nextLine();
                CommandValidator command = new CommandValidator();
                CURRENT_GAME_PHASE = GAME_PHASES.GAMEPLAY;
                command.addCommand(input);
                command.processValidCommand();
            } else if (user_in.strip().replace(" ", "").equalsIgnoreCase("Add/RemovePlayer")) {
                System.out.println("Please enter command to add and remove players");
                String input = in.nextLine();
                CURRENT_GAME_PHASE = GAME_PHASES.GAMEPLAY;
                CommandValidator command = new CommandValidator();
                command.addCommand(input);
                command.processValidCommand();
            } else if (user_in.strip().replace(" ", "").equalsIgnoreCase("assigncountries")) {

                if (PLAYER_LIST.isEmpty()) {
                    TerminalRenderer.renderMessage("!!!PLAYER LIST IS EMPTY!!! \n Please add players to the list");
                } else if (CURRENT_MAP.getD_countries().size() >= PLAYER_LIST.size()) {
                    PlayGame.startGame();
                } else {
                    TerminalRenderer.renderMessage("!!!Players are more than countries!!!");
                }


            } else if (user_in.strip().replace(" ", "").equalsIgnoreCase("exit")) {

                TerminalRenderer.renderExit();

            } else {

                TerminalRenderer.renderMessage("Not an option. Try again.");

            }

        }
    }

    /**
     * Displays the starting menu and handles user input to determine the next game phase.
     */
    private static void startingMenu() {

        TerminalRenderer.renderWelcome();

        String[] menu_options = {"Map Editor", "Play Game"};

        TerminalRenderer.renderMenu(
                "Starting Menu",
                menu_options
        );

        Scanner in = new Scanner(System.in);

        String user_in;

        while (true) {

            user_in = in.nextLine();

            if (user_in.strip().replace(" ", "").equalsIgnoreCase("settings")) {

                CURRENT_GAME_PHASE = GAME_PHASES.SETTINGS;

                return;

            } else if (user_in.strip().replace(" ", "").equalsIgnoreCase("mapeditor")) {

                CURRENT_GAME_PHASE = GAME_PHASES.MAP_EDITOR;

                return;

            } else if (user_in.strip().replace(" ", "").equalsIgnoreCase("playgame")) {

                CURRENT_GAME_PHASE = GAME_PHASES.GAMEPLAY;

                return;

            } else if (user_in.strip().replace(" ", "").equalsIgnoreCase("exit")) {

                TerminalRenderer.renderExit();

            } else {

                TerminalRenderer.renderMessage("Not an option. Try again.");

            }

        }

    }
    /**
     * Manages the map editor phase where users can edit maps.
     *
     * @throws FileNotFoundException if the map file is not found.
     */
    public static void mapEditor() throws FileNotFoundException {

        if (GameEngine.CURRENT_GAME_PHASE != GAME_PHASES.MAP_EDITOR) return;

        GameEngine.CURRENT_MAP = MapInterface.loadMap(
                TerminalRenderer.renderMapEditorMenu()
        );

        String input_command;

        while (true) {

            // render for help command and list of commands that can be used for mapeditor
            CommandValidator command = new CommandValidator();

            input_command = TerminalRenderer.renderMapEditorCommands();

            if (input_command.equals("exit")) {

                TerminalRenderer.renderExit();

                break;

            }

            try {

                command.addCommand(input_command);

                command.processValidCommand();

            } catch (InvalidCommandException | CountryDoesNotExistException |
                     ContinentAlreadyExistsException | ContinentDoesNotExistException | IOException | PlayerDoesNotExistException e) {

                TerminalRenderer.renderError("Invalid Command Entered: " + input_command + "\n" + e);

            }
        }

    }
    /**
     * The main method that starts the game and controls the game loop.
     *
     * @param args command line arguments.
     * @throws FileNotFoundException if a map file is not found.
     */
    public static void main(String[] args) throws FileNotFoundException {

        while (CURRENT_GAME_PHASE == GAME_PHASES.MAIN_MENU) {

            startingMenu();

            if (CURRENT_GAME_PHASE == GAME_PHASES.MAP_EDITOR) {

                try {

                    mapEditor();

                } catch (FileNotFoundException e) {

                    TerminalRenderer.renderError("Map file entered does not exist" + e);

                    CURRENT_GAME_PHASE = GAME_PHASES.MAIN_MENU;

                }

            }

            if (CURRENT_GAME_PHASE == GAME_PHASES.GAMEPLAY) {

                try {

                    playerLoop();

                } catch (Exception e) {

                    TerminalRenderer.renderError("Error in GameLoop" + e);

                    CURRENT_GAME_PHASE = GAME_PHASES.MAIN_MENU;

                }

            }

        }

    }

}
