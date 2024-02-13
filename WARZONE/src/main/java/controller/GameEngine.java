package main.java.controller;

import main.java.models.Player;
import main.java.models.map.Country;

import java.util.ArrayList;

public class GameEngine {
    public static void main(String[] args) {
    	
        System.out.println("Start of the Game");
        

        
        
      //Testing Player
		
      		Player testPlayer  = new Player();
      		Player testPlayer_2  = new Player();
      		
      		Country a = new Country("a",1);
    		Country b = new Country("b",1);
    		Country c = new Country("c",2);
    		Country d = new Country("d",2);
    	
      		
      		System.out.println(testPlayer.getName());
      		System.out.println(testPlayer.getReinforcements());
      		System.out.println(testPlayer.getassignedCountries());
      		
      		
      		testPlayer.setName("Priyanshu");
      		testPlayer.setReinforcements(5);
      		
      		ArrayList<Country> arr = new ArrayList<>();
      		arr.add(a);
      		arr.add(b);
      		
      	    testPlayer.setassignedCountries(arr);
      		
           
      		testPlayer_2.setName("ABC");
      		testPlayer_2.setReinforcements(5);
      		
      		ArrayList<Country> arr1 = new ArrayList<>();
      		arr.add(c);
      		arr.add(d);
      		
      	    testPlayer_2.setassignedCountries(arr1);
      	    
      	    System.out.println(testPlayer_2.getName());
      		System.out.println(testPlayer_2.getReinforcements());
      		System.out.println(testPlayer_2.getassignedCountries());


    }
}