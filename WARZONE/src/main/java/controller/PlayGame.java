package main.java.controller;

import main.java.models.Order;
import main.java.models.Player;
import main.java.models.worldmap.Country;
import main.java.views.TerminalRenderer;

import java.io.FileNotFoundException;
import java.util.*;


public class PlayGame {


    public static void startGame() throws FileNotFoundException, exceptions.InvalidCommandException {

        //We need list of players here
        ArrayList<Player> l_listOfPlayers = GameEngine.PLAYER_LIST;
        System.out.println("Assigning countries");

         assignCountriesToPlayers(l_listOfPlayers);
         //View to show that the startup phase is done - View StartUp.

        gameLoop(l_listOfPlayers);


    }

    public static void assignCountriesToPlayers(ArrayList<Player> p_listOfPlayers) throws FileNotFoundException {

        HashMap<Integer,Country> map = GameEngine.CURRENT_MAP.getD_countries(); //Take map from game engine


        Set<Integer> keySet = map.keySet();

        // Convert the set of keys into a List for easy removal
        ArrayList<Integer> keysList = new ArrayList<>(keySet);

        // Use a random number generator to generate a random index within the range of the list size
        int total_players = p_listOfPlayers.size();
        int playerNumber =0;
        while (!keysList.isEmpty()) {
            Random rand = new Random();
            int randomIndex = rand.nextInt(keysList.size());
            int l_randomCountryID = keysList.get(randomIndex);
            keysList.remove(randomIndex);
            if ((playerNumber % total_players == 0) && playerNumber != 0) {
                playerNumber =0;
            }
            Country country = map.get(l_randomCountryID);
            country.setD_country_player_ID(p_listOfPlayers.get(playerNumber).getPlayerId());
            p_listOfPlayers.get(playerNumber).setAssignedCountries(l_randomCountryID);
            playerNumber++;
        }

        System.out.println("Assigning of Countries Done");
        for(Player l_player: p_listOfPlayers){
            System.out.println("Number of Countries: " + l_player.getAssignedCountries().size());
            System.out.println("List of Assigned Countries for Player: "+l_player.getName());
            ArrayList<Integer> l_listOfAssignedCountries = l_player.getAssignedCountries();
            for(Integer l_countryID : l_listOfAssignedCountries){
                System.out.println(GameEngine.CURRENT_MAP.getCountry(l_countryID).getD_countryName());
            }
            System.out.println("-----------------------------------------------------------------");


        }

    }
    public static void assignReinforcements(ArrayList<Player> p_listOfPlayers){
        System.out.println("Assigning Reinforcements");
        for(Player player : p_listOfPlayers){
            int l_numberOfTroops = Math.max((int) player.getAssignedCountries().size() / 3, 3);
            player.setReinforcements(l_numberOfTroops);
        }
        System.out.println("Reinforcements Assigned");

    }
    public static void playerOrders(ArrayList<Player> p_listOfPlayers) throws exceptions.InvalidCommandException {

        System.out.println("Please Start issuing orders");
        int l_totalplayers = p_listOfPlayers.size();
        int l_playerNumber =0;
        while(!allTroopsPlaced(p_listOfPlayers)){
            if((l_playerNumber % l_totalplayers == 0) && l_playerNumber!=0){
                l_playerNumber =0;}
            if(p_listOfPlayers.get(l_playerNumber).getReinforcements()!=0) {
                        p_listOfPlayers.get(l_playerNumber).issue_order();
            }
            l_playerNumber++;
        }

    }
    public static void executingOrders(ArrayList<Player> p_listOfPlayers){
        System.out.println("Executing Orders");
        int l_totalplayers = p_listOfPlayers.size();
        int l_playerNumber =0;

        while(!allOrdersExecuted(p_listOfPlayers)){
            if((l_playerNumber % l_totalplayers == 0) && l_playerNumber!=0){
                l_playerNumber =0;
            }
            if (!p_listOfPlayers.get(l_playerNumber).getOrderList().isEmpty()) {
                Order order = p_listOfPlayers.get(l_playerNumber).next_order();
                order.execute();
            }
            l_playerNumber++;
        }
        System.out.println("All Orders Executed Successfully");

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
            if(!l_player.getOrderList().isEmpty()){
                return false;
            }
        }
        return true;
    }

    public static void gameLoop(ArrayList<Player> p_listOfPlayers ) throws exceptions.InvalidCommandException {


        String[] menu_options = {"Show Map","Start Game"};

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



            } else if (user_in.strip().replace(" ", "").equalsIgnoreCase("startgame")) {

                break;

            }

            else if (user_in.strip().replace(" ", "").equalsIgnoreCase("exit")) {

                TerminalRenderer.renderExit();

            } else {

                TerminalRenderer.renderMessage("Not an option. Try again.");

            }

        }


        assignReinforcements(p_listOfPlayers);
        playerOrders(p_listOfPlayers);
        executingOrders(p_listOfPlayers);

        //PostExecution View -> current state map. Current Players //Turn Information.




    }

}
