package controller;


// import controller.middleware.commands.CommandValidator;

import controller.statepattern.End;
import controller.statepattern.MainMenu;
import controller.statepattern.Starting;
import controller.statepattern.Phase;
import models.Player;
import models.worldmap.WorldMap;
import view.TerminalRenderer;

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
        MAIN_MENU, MAP_EDITOR, GAMEPLAY, SETTINGS
    }

    /**
     * The current game phase.
     */
    public GAME_PHASES CURRENT_GAME_PHASE;

    /**
     * The folder where map files are stored.
     */
    public String MAPS_FOLDER;

    private Phase d_currentPhase;

    /**
     * The currently loaded map.
     */
    public WorldMap CURRENT_MAP;

    /**
     * List of players in the game.
     */
    public ArrayList<Player> PLAYER_LIST;

    public TerminalRenderer renderer;
    public WorldMap worldmap;

    public void setCurrentState(Phase p_p){
        this.d_currentPhase = p_p;
    }

    public void runState(){
        while(this.d_currentPhase.getClass() != End.class){
            this.d_currentPhase.run();
        }

    }

    public GameEngine()
    {
        this.d_currentPhase = new Starting(this);
        MAPS_FOLDER = "WARZONE/src/main/resources/maps/";
        renderer = new TerminalRenderer(this);
        CURRENT_MAP = new WorldMap();
        PLAYER_LIST = new ArrayList<>();
    }

    /**
     * Manages the loop for player actions during the gameplay phase.
     **/
//    public void playerLoop() {
//        renderer.renderWelcome();
//
//        String[] menu_options = {"Show Map", "Load Map", "gameplayer to Add/Remove Player", "Assign Countries"};
//
//        renderer.renderMenu("Main Menu", menu_options);
//
//        Scanner in = new Scanner(System.in);
//
//        String user_in;
//
//        while (true)
//        {
//            user_in = in.nextLine();
//
//            if (user_in.strip().toLowerCase().startsWith("showmap"))
//            {
//
//                if (CURRENT_MAP != null)
//                    renderer.showMap(!PLAYER_LIST.isEmpty());
//
//                else
//                    renderer.renderError("No Map is currently loaded in the game!!!");
//
//            }
//            else if (user_in.strip().toLowerCase().startsWith("loadmap"))
//            {
//                CommandValidator command = new CommandValidator();
//                CURRENT_GAME_PHASE = GAME_PHASES.GAMEPLAY;
//
//                try
//                {
//                    command.addCommand(user_in.strip().toLowerCase());
//                    command.processValidCommand();
//                    renderer.renderMessage("Loaded map.");
//                }
//                catch (Exception | InvalidMapException e)
//                {
//                    renderer.renderError(e.toString());
//                }
//
//            }
//            else if (user_in.strip().toLowerCase().startsWith("gameplayer"))
//            {
//                renderer.renderMessage("Please enter command to add and remove players");
//                CURRENT_GAME_PHASE = GAME_PHASES.GAMEPLAY;
//                CommandValidator command = new CommandValidator();
//
//                try
//                {
//                    command.addCommand(user_in.strip().toLowerCase());
//                    command.processValidCommand();
//                    for (Player l_player : PLAYER_LIST)
//                    {
//                        renderer.renderMessage("Current players list: " + l_player.getName());
//                    }
//
//                }
//                catch (Exception | InvalidMapException e)
//                {
//                    renderer.renderError(e.toString());
//                }
//
//            }
//            else if (user_in.strip().toLowerCase().startsWith("assigncountries"))
//            {
//                if (PLAYER_LIST.isEmpty())
//                {
//                    renderer.renderMessage("!!!PLAYER LIST IS EMPTY!!! \n Please add players to the list");
//
//                }
//                else if (!assignCountriesValidator())
//                {
//                    renderer.renderMessage("!!!Players are more than countries!!!");
//
//                }
//                else if (!MapInterface.validateMap(this))
//                {
//                    CURRENT_MAP = null;
//                    renderer.renderMessage("Current map is not a valid map! Please load again");
//                }
//                else
//                {
//                    PlayGame.startGame();
//                }
//
//            }
//            else if (user_in.strip().replace(" ", "").equalsIgnoreCase("exit"))
//            {
//                return;
//            }
//            else
//            {
//                renderer.renderMessage("Not an option. Try again.");
//            }
//        }
//    }

    /**
     * Manages the map editor phase where users can edit maps.
     */
//    public void mapEditor() {
//
//        if (CURRENT_GAME_PHASE != GAME_PHASES.MAP_EDITOR) return;
//
//        String l_filename;
//
//        while (CURRENT_MAP == null) {
//
//            l_filename = renderer.renderMapEditorMenu();
//
//            try {
//
//                MapInterface.loadMap(this, l_filename);
//
//            } catch (FileNotFoundException | NumberFormatException | InvalidMapException e) {
//
//                renderer.renderError("Invalid File or not found");
//
//            }
//
//        }
//
//        String input_command;
//
//        // CommandValidator command;
//
//        while (true) {
//
//            input_command = renderer.renderMapEditorCommands();
//
//            if (input_command.equals("exit")) {
//
//                CURRENT_GAME_PHASE = GAME_PHASES.MAIN_MENU;
//
//                startingMenu();
//
//                break;
//
//            }
//
//            // command = new CommandValidator();
//
//            try {
//
//                // command.addCommand(input_command);
//
//                // command.processValidCommand();
//
//            } catch (InvalidCommandException | CountryDoesNotExistException | ContinentAlreadyExistsException |
//                     ContinentDoesNotExistException | IOException | PlayerDoesNotExistException | InvalidMapException |
//                     DuplicateCountryException e) {
//
//                renderer.renderError("Invalid Command Entered: " + input_command + "\n" + e);
//
//            }
//
//        }
//
//    }

    /**
     * Checks if the number of countries is greater than number of players
     */
//    public boolean assignCountriesValidator() {
//        return CURRENT_MAP.getD_countries().size() >= PLAYER_LIST.size();
//    }

    /**
     * Displays the starting menu and handles user input to determine the next game phase.
     */
    private void startingMenu() {

        renderer.renderMessage("current game phase: " + CURRENT_GAME_PHASE.toString());

        renderer.renderWelcome();

        String[] menu_options = {"Map Editor", "Play Game"};

        renderer.renderMenu("Starting Menu", menu_options);

        Scanner in = new Scanner(System.in);

        String user_in;

        while (true) {

            user_in = in.nextLine();

            if (user_in.strip().replace(" ", "").equalsIgnoreCase("mapeditor")) {

                CURRENT_GAME_PHASE = GAME_PHASES.MAP_EDITOR;
                renderer.renderMessage("you are entering `mapeditor` menu");
                CURRENT_MAP = null;
                // mapEditor();

                //return;

            } else if (user_in.strip().replace(" ", "").equalsIgnoreCase("playgame")) {

                CURRENT_GAME_PHASE = GAME_PHASES.GAMEPLAY;
                renderer.renderMessage("you are now entering `playgame` phase");
                CURRENT_MAP = null;
                // playerLoop();

                //return;

            } else if (user_in.strip().replace(" ", "").equalsIgnoreCase("exit")) {

                renderer.renderExit();

            } else {

                renderer.renderMessage("Not an option. Try again.");

            }

        }

    }

    /**
     * The main method that starts the game and controls the game loop.
     *
     * @param args command line arguments.
     */
    public void main(String[] args) {

        while (CURRENT_GAME_PHASE == GAME_PHASES.MAIN_MENU) {

            startingMenu();

            if (CURRENT_GAME_PHASE == GAME_PHASES.MAP_EDITOR) {

                // mapEditor();

                CURRENT_GAME_PHASE = GAME_PHASES.MAIN_MENU;

            }

            if (CURRENT_GAME_PHASE == GAME_PHASES.GAMEPLAY) {

                // playerLoop();

                CURRENT_GAME_PHASE = GAME_PHASES.MAIN_MENU;

            }

        }

    }

}
