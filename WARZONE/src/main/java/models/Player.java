package main.java.models;

import main.java.models.map.Country;

import java.util.ArrayList;

public class Player{

    private String d_playerName;
    private int d_reinforcements;
    private ArrayList<Country> d_assignedCountries;
    
    //Getters
    public String getName() {
        return d_playerName;
    }
    public int getReinforcements() {
        return d_reinforcements;
    }
    public ArrayList<Country> getassignedCountries() {
        return d_assignedCountries;
    }
    
    //Setters
    public void setName(String p_name) {
        this.d_playerName= p_name;
    }
    public void setReinforcements(int p_reinforcements) {
        this.d_reinforcements= p_reinforcements;
    }
    public void setassignedCountries(ArrayList<Country> d_assignedCountries) {
        this.d_assignedCountries= d_assignedCountries;
    }
    
    //Constructors
    public Player(){
     d_playerName= "";
     d_reinforcements= 0;
     d_assignedCountries= new ArrayList<>(); 	
    }
    
    public Player(String d_playerName,int d_reinforcements,ArrayList<Country> d_assignedCountries){
        this.d_playerName= "";
        this.d_reinforcements= 0;
        this.d_assignedCountries= d_assignedCountries; 	
       }


}