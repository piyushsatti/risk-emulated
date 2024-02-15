package main.java.controller;

import main.java.models.Order;
import main.java.models.Player;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class GameEngine {
    public static void main(String[] args) throws FileNotFoundException {

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
}
