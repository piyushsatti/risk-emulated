package controller;

import main.java.models.Order;
import main.java.models.Player;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;

public class PlayGame {


    public static void startgame() throws FileNotFoundException {


        //We need list of players here
        ArrayList<Player> l_listOfPlayers = GameEngine.PLAYER_LIST;

        //Assigning Countries
         Player.assignCountriesToPlayers(l_listOfPlayers);

         //View to show that the startup phase is done - View StartUp.

        gameloop(l_listOfPlayers);


    }

    public static void assignCountriesToPlayers(ArrayList<Player> l_listOfPlayers) throws FileNotFoundException {
        map = MapInterface.loadMap("usa8"); //Take map from game engine
        HashMap<Integer, main.java.models.worldmap.Country> listOfCountries = map.getD_countries();
        int total_players = d_Players.size();
        int playerNumber =0;
        for(HashMap.Entry<Integer, main.java.models.worldmap.Country> entry : listOfCountries.entrySet()) {
            if ((playerNumber % total_players == 0) && playerNumber != 0) {
                playerNumber =0;
            }
            Integer key = entry.getKey();
            main.java.models.worldmap.Country value = entry.getValue();
            Player p = d_Players.get(playerNumber);
            p.setAssignedCountries(key);

            playerNumber++;
        }

        for(Player l_player: d_Players){
            l_player.printPlayerDetails();
            System.out.println(" ");
        }
        for(Player l_player: d_Players){
            System.out.println("Number of Countries: " + l_player.getAssignedCountries().size());
            System.out.println(" ");
        }

    }

    //Checking if player has placed all his troops.
    public static boolean allTroopsPlaced(ArrayList<Player> p_Players){
        for(Player l_player : p_Players){
            if(l_player.getReinforcements()!=0){
                return false;
            }
        }
        return true;
    }


    //Checking if all orders of each player have been executed.
    public static boolean allOrdersExecuted(ArrayList<Player> p_Players){
        for(Player l_player : p_Players){
            if(!l_player.d_orderList.isEmpty()){
                return false;
            }
        }
        return true;
    }



    public static void gameloop(ArrayList<Player> l_listOfPlayers ) throws FileNotFoundException{

        System.out.println("Assigning Reinforcements");
        for(Player player : l_listOfPlayers){
            int l_numberOfTroops = Math.max((int) player.getAssignedCountries().size() / 3, 3);
            player.setReinforcements(l_numberOfTroops);
        }
        System.out.println("Reinforcements Assigned");

        System.out.println("Please Start issuing orders");


        int l_totalplayers = l_listOfPlayers.size();
        int l_playerNumber =0;
        while(!Player.allTroopsPlaced(l_listOfPlayers)){
            if((l_playerNumber % l_totalplayers == 0) && l_playerNumber!=0){
                l_playerNumber =0;}
            if(l_listOfPlayers.get(l_playerNumber).getReinforcements()!=0){
                //Issue Order View  -- To which country do you want to deploy and how much
                l_listOfPlayers.get(l_playerNumber).issue_order(1,3);}

            l_playerNumber++;
        }


        //Execute order for all players
        System.out.println("Executing Orders");
        while(!main.java.models.Player.allOrdersExecuted(l_listOfPlayers)){
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

}
