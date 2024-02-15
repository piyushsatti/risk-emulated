package controller;

import models.Player;
import models.Order;
import java.util.ArrayList;
import java.util.List;
public class GameEngine {
    public static void main(String[] args) {

        System.out.println("Start of the Game");



       //Show Map
       //LoadMap

       // Instance of Map -List of countries(deployed armies),continent,neighbours,
     /********************************************************* Adding Players and Assigncountries *************/
       //Add and remove player - Player arraylist
       //Example of how Add Player works
		ArrayList<String> names = new ArrayList<>();
		names.add("Dev");
		names.add("Priyanshu");
		names.add("Piyush");
        Player.addPlayer(names);
        System.out.println("Displaying.........");
//         Player.displayPlayers();
        //Assigning Countries to each player
        Player.assignCountriesToPlayers();
        List<Player> d_listOfPlayers = Player.getD_Players();
        //************************************** MAIN LOOP **************************************//
	      //****************ASSIGN REINFORCEMENTS ***************************//
        for(Player player : d_listOfPlayers){
            int l_numberOfTroops = Math.max((int)player.getassignedCountries().size()/3,3);
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
//        while(!Player.allTroopsPlaced(d_listOfPlayers)){
//            if((playerNumber % totalplayers == 0) && playerNumber!=0){
//                playerNumber =0;
//            }
//            if(d_listOfPlayers.get(playerNumber).getReinforcements()!=0){
//                d_listOfPlayers.get(playerNumber).issue_order();
//            }
//
//        }
     //****************EXECUTE order ***************************//
        while(!Player.allOrdersExecuted(d_listOfPlayers)){
            if((playerNumber % totalplayers == 0) && playerNumber!=0){
                playerNumber =0;
            }
            if(!d_listOfPlayers.get(playerNumber).getD_orderList().isEmpty()){
                Order order = d_listOfPlayers.get(playerNumber).next_order();
                order.execute_order();
            }
            playerNumber++;

        }

////////////////////////// Only Notes BELOW IT IGNORE //////////////////////////////////////////////////////////////////////////////////


//        while(true){
//            for(Player player : d_listOfPlayers){
//                int l_numberOfTroops = Math.max((int)player.getassignedCountries().size()/3,3);
//                player.setReinforcements(l_numberOfTroops);
//                player.printPlayerDetails();
//            }
//
//        }



       //Testing Player

//            ArrayList<Player> playerList = new ArrayList<>();
//      		Player testPlayer  = new Player();
//      		Player testPlayer_2  = new Player();
//      		testPlayer.setName("Dev");
//      		testPlayer_2.setName("Priyanshu");
//
//      		playerList.add(testPlayer_2);
//      		playerList.add(testPlayer);
//
//      		ArrayList<Country> countryList = new ArrayList<>();   //To be given by Map Team
//      		Country a = new Country(1,"a",new Continent(1,"a"));
//    		Country b = new Country(2,"b",new Continent(1,"a"));
//    		Country c = new Country(3,"c",new Continent(1,"a"));
//    		Country d = new Country(4,"d",new Continent(1,"a"));
//
//    		countryList.add(a);
//    		countryList.add(b);
//    		countryList.add(c);
//    		countryList.add(d);



        // Implementation of add Players assuming the function gets a list of player names from Command Line


           //Start up phase  AssignCountry - Country objects that are assigned to player basis of id,continent and deployed_forces

//      	   int player_no=0;
//      	   for(int i=0;i<countryList.size();i++){
//
//      		   playerList.get(player_no).setassignedCountries(countryList.get(i));
//
//      		   player_no++;
//      		   if(player_no>2)player_no=0;
//      	   }
//      	   //Player gets intial reinforments 10
//
//      	 for(int i=0;i<playerList.size();i++){
//
//      		 int army= (int)playerList.get(player_no).getassignedCountries().size()/3;
//      		 playerList.get(player_no).setReinforcements(Math.max(army,3));
//      	 }
//





      	   //Deployment - only owned countries can allow deployment --Done by player

      		//{
      		//issue orders - player is assigning armies to countries
      		//next order - execute order

      		//execute methods-assigning to map instance player order execute method
      		//}

      		//Attack
    }
}
