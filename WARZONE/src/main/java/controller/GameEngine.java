package controller;

import controller.states.End;
import controller.states.StartingMenu;
import controller.states.State;
import controller.commands.CommandValidator;
import helpers.exceptions.*;
import models.Player;
import models.worldmap.WorldMap;
import views.TerminalRenderer;

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
     * The folder where map files are stored.
     */
    public  String d_mapsFolder = "WARZONE/src/main/resources/maps/";

    private State d_currentState;

    /**
     * The currently loaded map.
     */
    public  WorldMap d_currentMap;

    /**
     * List of players in the game.
     */
    public  ArrayList<Player> d_playerList;

    public GameEngine(){
        d_currentState = new StartingMenu(this);
        d_playerList = new ArrayList<Player>();
    }

    public void setCurrentState(State s){
        this.d_currentState = s;
    }

    public void runState(){
        while(d_currentState.getClass() != End.class) {
            d_currentState.run();
        }
    }

    /**
     * Manages the loop for player actions during the gameplay phase.
     **/
    public  void playerLoop() {


        TerminalRenderer.renderWelcome();

        String[] menu_options = {"Show Map", "Load Map", "gameplayer to Add/Remove Player", "Assign Countries"};

        TerminalRenderer.renderMenu(
                "Main Menu",
                menu_options
        );

        Scanner in = new Scanner(System.in);

        String user_in;

        while (true) {

            user_in = in.nextLine();

            if (user_in.strip().toLowerCase().startsWith("showmap")) {

                if(this.d_currentMap!=null)

                    TerminalRenderer.showMap(!this.d_playerList.isEmpty());

                else

                    TerminalRenderer.renderError("No Map is currently loaded in the game!!!");

            } else if (user_in.strip().toLowerCase().startsWith("loadmap")) {

                CommandValidator command = new CommandValidator();

                this.d_currentState = new ;

                try {

                    command.addCommand(user_in.strip().toLowerCase());

                    command.processValidCommand();

                    TerminalRenderer.renderMessage("Loaded map.");

                } catch (Exception | InvalidMapException e) {

                    TerminalRenderer.renderError(e.toString());

                }

            } else if (user_in.strip().toLowerCase().startsWith("gameplayer")) {

                TerminalRenderer.renderMessage("Please enter command to add and remove players");

                CURRENT_GAME_PHASE = GAME_PHASES.GAMEPLAY;

                CommandValidator command = new CommandValidator();

                try {

                    command.addCommand(user_in.strip().toLowerCase());

                    command.processValidCommand();

                    for (Player l_player : PLAYER_LIST) {

                        TerminalRenderer.renderMessage("Current players list: " + l_player.getName());

                    }

                } catch (Exception | InvalidMapException e) {

                    TerminalRenderer.renderError(e.toString());

                }

            } else if (user_in.strip().toLowerCase().startsWith("assigncountries")) {

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

                return;

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
     * Displays the starting menu and handles user input to determine the next game phase.
     */
    private static void startingMenu() {

        TerminalRenderer.renderMessage("current game phase: " + CURRENT_GAME_PHASE.toString());

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

            if (user_in.strip().replace(" ", "").equalsIgnoreCase("mapeditor")) {

                CURRENT_GAME_PHASE = GAME_PHASES.MAP_EDITOR;
                TerminalRenderer.renderMessage("you are entering `mapeditor` menu");
                GameEngine.CURRENT_MAP = null;
                mapEditor();

                //return;

            } else if (user_in.strip().replace(" ", "").equalsIgnoreCase("playgame")) {

                CURRENT_GAME_PHASE = GAME_PHASES.GAMEPLAY;
                TerminalRenderer.renderMessage("you are now entering `playgame` phase");
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

                    CURRENT_GAME_PHASE = GAME_PHASES.MAIN_MENU;

                }

                if (CURRENT_GAME_PHASE == GAME_PHASES.GAMEPLAY) {

                    playerLoop();

                    CURRENT_GAME_PHASE = GAME_PHASES.MAIN_MENU;

                }

            }

    }

}