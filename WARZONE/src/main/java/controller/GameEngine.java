package controller;

import models.Player;

import java.util.ArrayList;

public class GameEngine {
    public static void main(String[] args) {
    	
        System.out.println("Start of the Game");

        

       //Show Map
       //LoadMap

       // Instance of Map -List of countries(deployed armies),continent,neighbours,

       //Add and remove player - Player arraylist
       //Example of how Add Player works
		ArrayList<String> names = new ArrayList<>();
		names.add("Dev");
		names.add("Priyanshu");
		names.add("Piyush");
        Player.addPlayer(names);
        //Player.displayPlayers();


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
