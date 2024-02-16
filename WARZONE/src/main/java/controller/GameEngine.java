package main.java.controller;

import main.java.models.Order;
import main.java.models.Player;
import main.java.utils.TerminalColors;
import main.java.views.TerminalRenderer;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class GameEngine {
    enum GAME_PHASE {
        MAIN_MENU,
        MAP_EDITOR,
        GAMEPLAY
    }

    private static GAME_PHASE game_phase = GAME_PHASE.MAIN_MENU;

    public static final String MAPS_FOLDER = "WARZONE/src/main/resources/maps/";

    public static void playerLoop() throws FileNotFoundException {

        System.out.println("Start of the Game");

        //Show Map
        //LoadMap


        /********************************************************* Adding Players and Assigncountries *************/
        //Add and remove player - Player arraylist
        //Example of how Add Player works
        ArrayList<String> names = new ArrayList<>();
        names.add("Dev");
        names.add("Priyanshu");
        names.add("Piyush");
         //NOTE TO PIYUSH: need to get ArrayList of Players to be added or removed from user through command prompt
        Player.addPlayer(names);
        System.out.println("Displaying.........");
//         Player.displayPlayers();
        // Assigning Countries to each player
        Player.assignCountriesToPlayers();
        ArrayList<Player> d_listOfPlayers = Player.getPlayers();
        //************************************** MAIN LOOP **************************************//
        for(Player player : d_listOfPlayers){
            int l_numberOfTroops = Math.max((int) player.getAssignedCountries().size() / 3, 3);
            player.setReinforcements(l_numberOfTroops);
//            player.printPlayerDetails();
//            System.out.println(" ");

        }

        //****************issue order ***************************//

        int totalplayers = d_listOfPlayers.size();

        int playerNumber =0;

        for(int i=0;i<3;i++){

            //NOTE isse_order should not have parameters according to project requirements it is just for testing.

            d_listOfPlayers.get(0).issue_order(1,4);

            d_listOfPlayers.get(1).issue_order(1,2);

            d_listOfPlayers.get(2).issue_order(1,3);

        }

        while(!Player.allOrdersExecuted(d_listOfPlayers)){

            if((playerNumber % totalplayers == 0) && playerNumber!=0){

                playerNumber =0;

            }

            if (!d_listOfPlayers.get(playerNumber).getOrderList().isEmpty()) {

                Order order = d_listOfPlayers.get(playerNumber).next_order();

                order.execute_order();
            }

            playerNumber++;

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

            game_phase = GAME_PHASE.MAP_EDITOR;

        } else if (menu_options[user_in].equals("Play Game")) {

            game_phase = GAME_PHASE.GAMEPLAY;

        }

    }

    public static void mapEditor() {
        // trigger sub-routines for map editor
    }

    public static void main(String[] args) throws FileNotFoundException {

        startingMenu();

        if (game_phase == GAME_PHASE.MAP_EDITOR) {

            mapEditor();

        } else if (game_phase == GAME_PHASE.GAMEPLAY) {

            playerLoop();

        }
    }
}
