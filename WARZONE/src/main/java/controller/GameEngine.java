package controller;
import java.util.*;

import models.Continent;
import models.Country;
import models.Player;

public class GameEngine {
    public static void main(String[] args) {
    	
        System.out.println("Start of the Game");
        

        
        
      //Testing Player

      		Player testPlayer  = new Player();
      		Player testPlayer_2  = new Player();

			Continent a1 = new Continent("a1", 1);
			Continent a2 = new Continent("a2", 1);
      		Country a = new Country("a",a1);
    		Country b = new Country("b",a1);
    		Country c = new Country("c",a2);
    		Country d = new Country("d",a2);
    	
      		
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