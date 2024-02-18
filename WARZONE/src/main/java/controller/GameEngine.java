package main.java.controller;


import main.java.controller.commands.CommandValidator;
import main.java.models.Player;
import main.java.models.worldmap.WorldMap;
import main.java.utils.exceptions.ContinentAlreadyExistsException;
import main.java.utils.exceptions.ContinentDoesNotExistException;
import main.java.utils.exceptions.CountryDoesNotExistException;
import main.java.utils.exceptions.PlayerDoesNotExistException;
import main.java.views.TerminalRenderer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class GameEngine {

    public enum GAME_PHASES {
        MAIN_MENU,
        MAP_EDITOR,
        GAMEPLAY,
        SETTINGS
    }

    public static GAME_PHASES CURRENT_GAME_PHASE = GAME_PHASES.MAIN_MENU;

    public static String MAPS_FOLDER = "WARZONE/src/main/resources/maps/";

    public static WorldMap CURRENT_MAP;

    public static ArrayList<Player> PLAYER_LIST = new ArrayList<Player>();

    static {


        try {

            CURRENT_MAP = MapInterface.loadMap("usa9.map");

        } catch (FileNotFoundException e) {
            System.out.println("Error");
            throw new RuntimeException(e);

        }

    }


    public static void playerLoop() throws FileNotFoundException, exceptions.InvalidCommandException {


        //Need A view for what needs to be done;


        TerminalRenderer.renderWelcome();

        String[] menu_options = {"Show Map", "Load Map", "Add/RemovePlayer","Assign Countries"};

        TerminalRenderer.renderMenu(
                "Main Menu",
                menu_options
        );

        Scanner in = new Scanner(System.in);

        String user_in = "";

        while (true) {

            user_in = in.nextLine();


            if (user_in.strip().replace(" ", "").equalsIgnoreCase("showmap")) {

                TerminalRenderer.showMap(true);
                return;

            } else if (user_in.strip().replace(" ", "").equalsIgnoreCase("loadmap")) {

                MapInterface.loadMap(
                        TerminalRenderer.renderRequestMapFileName()
                );

                return;

            } else if (user_in.strip().replace(" ", "").equalsIgnoreCase("Add/RemovePlayer")) {

                CURRENT_GAME_PHASE = GAME_PHASES.GAMEPLAY;

                return;

            } else if (user_in.strip().replace(" ", "").equalsIgnoreCase("assigncountries")) {

                if(PLAYER_LIST.isEmpty()){
                    TerminalRenderer.renderMessage("!!!PLAYER LIST IS EMPTY!!! \n Please add players to the list" );
                }
                else if(CURRENT_MAP.getD_countries().size()>=PLAYER_LIST.size()) {
                    PlayGame.startGame();
                }
                else{
                    TerminalRenderer.renderMessage("!!!Players are more than countries!!!");
                }
                return;

            }

            else if (user_in.strip().replace(" ", "").equalsIgnoreCase("exit")) {

                TerminalRenderer.renderExit();

            } else {

                TerminalRenderer.renderMessage("Not an option. Try again.");

            }

        }
    }


    private static void startingMenu() {

        TerminalRenderer.renderWelcome();

        String[] menu_options = {"Map Editor", "Play Game"};

        TerminalRenderer.renderMenu(
                "Main Menu",
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

            } catch (exceptions.InvalidCommandException | CountryDoesNotExistException |
                     ContinentAlreadyExistsException | ContinentDoesNotExistException | IOException | PlayerDoesNotExistException e) {

                TerminalRenderer.renderError("Invalid Command Entered: " + input_command + "\n" + e.toString());

            }
        }

    }

    public static void main(String[] args) throws FileNotFoundException {

     Player p = new Player("Priyanshu");
     Player d = new Player("Dev");
     PLAYER_LIST.add(p);
     PLAYER_LIST.add(d);




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
