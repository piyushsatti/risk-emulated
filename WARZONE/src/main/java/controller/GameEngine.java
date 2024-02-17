package main.java.controller;

import main.java.models.Order;
import main.java.models.Player;
import main.java.models.worldmap.WorldMap;
import main.java.utils.TerminalColors;
import main.java.views.TerminalRenderer;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class GameEngine {

    public enum GAME_PHASES {
        MAIN_MENU,
        MAP_EDITOR,
        GAMEPLAY
    }

    public static GAME_PHASES CURRENT_GAME_PHASE = GAME_PHASES.MAIN_MENU;

    public static String MAPS_FOLDER = "WARZONE/src/main/resources/maps/";

    public static WorldMap CURRENT_MAP;

    public static ArrayList<Player> PLAYER_LIST = new ArrayList<Player>();

    static {


        try {

            CURRENT_MAP = MapInterface.loadMap("usa9.map");

        } catch (FileNotFoundException e) {

            throw new RuntimeException(e);

        }

    }

    public static void startgame() throws FileNotFoundException{


        //We need list of players here
        ArrayList<Player> l_listOfPlayers = Player.getPlayers();


        Player.assignCountriesToPlayers();



       System.out.println("Assigning Reinforcements");
        for(Player player : l_listOfPlayers){
            int l_numberOfTroops = Math.max((int) player.getAssignedCountries().size() / 3, 3);
            player.setReinforcements(l_numberOfTroops);
        }
        System.out.println("Reinforcements Assigned");



        //Input the parameters for issue orders
        System.out.println("Please Start issuing orders");
        int l_totalplayers = l_listOfPlayers.size();
        int l_playerNumber =0;
        while(!Player.allTroopsPlaced(l_listOfPlayers)){
            if((l_playerNumber % l_totalplayers == 0) && l_playerNumber!=0){
                l_playerNumber =0;}
            if(l_listOfPlayers.get(l_playerNumber).getReinforcements()!=0){
                l_listOfPlayers.get(l_playerNumber).issue_order(1,3);}

            l_playerNumber++;
        }

        //Execute order for all players
        System.out.println("Executing Orders");
        while(!Player.allOrdersExecuted(l_listOfPlayers)){
            if((l_playerNumber % l_totalplayers == 0) && l_playerNumber!=0){
                l_playerNumber =0;
            }
            if (!l_listOfPlayers.get(l_playerNumber).getOrderList().isEmpty()) {
                Order order = l_listOfPlayers.get(l_playerNumber).next_order();
                order.execute_order();
            }
            l_playerNumber++;
        }
        System.out.println("Orders Executed");


    }
    public static void playerLoop() throws FileNotFoundException {

        //System.out.println(TerminalRenderer.renderWelcome()); Welcome to the game screen

        String[] menu_options = {"Show Map","Load Map","Add/Remove Player", "Start Game"};

        System.out.println(TerminalRenderer.renderMenu("Main Menu", menu_options));

        Scanner in = new Scanner(System.in);

        String input = in.nextLine();

        //Validate Command
        while(!input.equals("Exit")){

            switch(input){
                case "Show Map" : //Show Map;
                case "Load Map" :  //Load Map;
                case"Add/Remove Player": //We get the final Arraylist of names by addition and removal of player;
                case "Start Game" : //Call Start Map;
                     startgame();

                default: //exit
                    System.exit(0);
             }

            //What is the next input
            input = in.nextLine();
        }

    }

    private static void startingMenu() {

        System.out.println(TerminalRenderer.renderWelcome());

        String[] menu_options = {"Map Editor", "Play Game"};

        System.out.println(TerminalRenderer.renderMenu("Main Menu", menu_options));

        Scanner in = new Scanner(System.in);

        int user_in = Integer.parseInt(in.nextLine()) - 1;

        if ((user_in >= menu_options.length) || (user_in < 0)) {

            System.out.println(TerminalColors.ANSI_PURPLE + "Thank you for playing!" + TerminalColors.ANSI_RESET);

            System.exit(0);

        } else if (menu_options[user_in].equals("Map Editor")) {

            CURRENT_GAME_PHASE = GAME_PHASES.MAP_EDITOR;

        } else if (menu_options[user_in].equals("Play Game")) {

            CURRENT_GAME_PHASE = GAME_PHASES.GAMEPLAY;

        }

    }

    public static void mapEditor() throws FileNotFoundException {

        if (GameEngine.CURRENT_GAME_PHASE != GAME_PHASES.MAP_EDITOR) return;

        try {
            WorldMap map = MapInterface.loadMap(
                    TerminalRenderer.renderMapEditorMenu()
            );
        } catch (FileNotFoundException e) {
            TerminalRenderer.renderError("Map file entered does not exist" + e.toString());
        }

        String input_command;

        while (true) {

            // render for help command and list of commands that can be used for mapeditor\
            CommandValidator command = new CommandValidator();

            input_command = TerminalRenderer.renderMapEditorCommands();

            if (input_command.equals("exit")) break;

            try {

                command.addCommand(input_command);

            } catch (exceptions.InvalidCommandException e) {

                TerminalRenderer.renderError("Invalid Command Entered: " + input_command + "\n" + e.toString());

            }

            command.processValidCommand();

        }

    }

    public static void main(String[] args) throws FileNotFoundException {

        startingMenu();

        if (CURRENT_GAME_PHASE == GAME_PHASES.MAP_EDITOR) {

            mapEditor();

        } else if (CURRENT_GAME_PHASE == GAME_PHASES.GAMEPLAY) {

            playerLoop();

        }
    }

}
