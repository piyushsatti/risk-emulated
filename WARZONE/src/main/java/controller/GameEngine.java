package controller;

import controller.commands.CommandValidator;
import helpers.exceptions.*;
import models.Player;
import models.worldmap.WorldMap;
import views.TerminalRenderer;

import java.io.File;
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
    public static String MAPS_FOLDER = "WARZONE\\src\\main\\resources\\maps\\";

    /**
     * The currently loaded map.
     */
    public static WorldMap CURRENT_MAP;

    /**
     * List of players in the game.
     */
    public static ArrayList<Player> PLAYER_LIST = new ArrayList<>();

    /**
     * Manages the loop for player actions during the gameplay phase.
     **/
    public static void playerLoop() {


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

                if(CURRENT_MAP!=null)
                    TerminalRenderer.showMap(!GameEngine.PLAYER_LIST.isEmpty());
                else
                    TerminalRenderer.renderError("No Map is currently loaded in the game!!!");


            } else if (user_in.strip().replace(" ", "").equalsIgnoreCase("loadmap")) {

                System.out.println("Enter command-> loadmap followed by filename.map");

                String input = in.nextLine();

                CommandValidator command = new CommandValidator();

                CURRENT_GAME_PHASE = GAME_PHASES.GAMEPLAY;

                try {

                    command.addCommand(input);

                    command.processValidCommand();

                } catch (Exception | InvalidMapException e) {

                    TerminalRenderer.renderError(e.toString());

                }

            } else if (user_in.strip().replace(" ", "").equalsIgnoreCase("add/removeplayer")) {

                System.out.println("Please enter command to add and remove players");

                String input = in.nextLine();

                CURRENT_GAME_PHASE = GAME_PHASES.GAMEPLAY;

                CommandValidator command = new CommandValidator();

                try {

                    command.addCommand(input);

                    command.processValidCommand();

                } catch (Exception | InvalidMapException e) {

                    TerminalRenderer.renderError(e.toString());

                }

            } else if (user_in.strip().replace(" ", "").equalsIgnoreCase("assigncountries")) {

                if (PLAYER_LIST.isEmpty()) {

                    TerminalRenderer.renderMessage("!!!PLAYER LIST IS EMPTY!!! \n Please add players to the list");

                } else if (!assignCountriesValidator()) {

                    TerminalRenderer.renderMessage("!!!Players are more than countries!!!");

                } else if (!MapInterface.validateMap(CURRENT_MAP)) {

                    CURRENT_MAP = null;
                    TerminalRenderer.renderMessage("Current map is not a valid map! Please load again");

                }
                else {

                    PlayGame.startGame();
                }

            } else if (user_in.strip().replace(" ", "").equalsIgnoreCase("exit")) {

                TerminalRenderer.renderExit();

            } else {

                TerminalRenderer.renderMessage("Not an option. Try again.");

            }


        }

    }

    /**
     * Manages the map editor phase where users can edit maps.
     */
    public static void mapEditor() {

        if (GameEngine.CURRENT_GAME_PHASE != GAME_PHASES.MAP_EDITOR) return;

        String l_filename;

        while (GameEngine.CURRENT_MAP == null) {

            l_filename = TerminalRenderer.renderMapEditorMenu();

            try {

                GameEngine.CURRENT_MAP = MapInterface.loadMap(l_filename);

            } catch (FileNotFoundException | NumberFormatException | InvalidMapException e) {

                TerminalRenderer.renderError("Invalid File or not found");

            }
        }

        String input_command;

        CommandValidator command;

        while (true) {

            input_command = TerminalRenderer.renderMapEditorCommands();

            if (input_command.equals("exit")) {

                GameEngine.CURRENT_GAME_PHASE = GAME_PHASES.MAIN_MENU;
                startingMenu();
                //TerminalRenderer.renderExit();

                break;

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

    /**
     * Checks if the number of countries is greater than number of players
     */
    public static boolean assignCountriesValidator() {
        return CURRENT_MAP.getD_countries().size() >= PLAYER_LIST.size();
    }

    /**
     * Configure default settings for GameEngine
     */
    private static void settings() {

        TerminalRenderer.renderMessage("Please enter map folder path: ");

        Scanner in = new Scanner(System.in);

        File in_file;

        String inp_file_path;

        while (true) {

            inp_file_path = in.nextLine().strip();

            in_file = new File(inp_file_path);

            if (in_file.isDirectory()) {

                GameEngine.MAPS_FOLDER = inp_file_path;

                TerminalRenderer.renderMessage("Directory set: " + inp_file_path);

                return;

            } else {

                TerminalRenderer.renderError("File path not a directory.");

            }

        }

    }

    /**
     * Displays the starting menu and handles user input to determine the next game phase.
     */
    private static void startingMenu() {

        TerminalRenderer.renderMessage("current game phase: " + CURRENT_GAME_PHASE.toString());

        TerminalRenderer.renderWelcome();

        String[] menu_options = {"Map Editor", "Play Game", "Settings"};

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
                TerminalRenderer.renderMessage("you are now entering settings phase");
                GameEngine.CURRENT_MAP = null;
                settings();
                //return;

            } else if (user_in.strip().replace(" ", "").equalsIgnoreCase("mapeditor")) {

                CURRENT_GAME_PHASE = GAME_PHASES.MAP_EDITOR;
                TerminalRenderer.renderMessage("you are entering map editor menu");
                GameEngine.CURRENT_MAP = null;
                mapEditor();

                //return;

            } else if (user_in.strip().replace(" ", "").equalsIgnoreCase("playgame")) {

                CURRENT_GAME_PHASE = GAME_PHASES.GAMEPLAY;
                TerminalRenderer.renderMessage("you are now entering settings phase");
                GameEngine.CURRENT_MAP = null;
                playerLoop();

                //return;

            } else if (user_in.strip().replace(" ", "").equalsIgnoreCase("exit")) {

                TerminalRenderer.renderExit();

            } else {

                TerminalRenderer.renderMessage("Not an option. Try again.");

            }

        }

    }

    /**
     * The main method that starts the game and controls the game loop.
     *
     * @param args command line arguments.
     */
    public static void main(String[] args) {

            while (CURRENT_GAME_PHASE == GAME_PHASES.MAIN_MENU) {

                startingMenu();

                if (CURRENT_GAME_PHASE == GAME_PHASES.MAP_EDITOR) {

                    mapEditor();

                }

                if (CURRENT_GAME_PHASE == GAME_PHASES.GAMEPLAY) {

                    playerLoop();

                }

                if (CURRENT_GAME_PHASE == GAME_PHASES.SETTINGS) {

                    settings();

                }

            }

    }

}