package main.java.models;

import main.java.controller.GameEngine;



public class Order {
    int d_playerOrderID;
    String d_playerOrderName;
    private final int d_fromCountryID;
    private final int d_toCountryID;
    private final int d_reinforcementsDeployed;

    public Order(String p_playerOrderName, int p_playerOrderID,int p_fromCountryID,int p_reinforcementsDeployed){
        this.d_playerOrderName = p_playerOrderName;
        this.d_playerOrderID = p_playerOrderID;
        this.d_fromCountryID = p_fromCountryID;
        this.d_toCountryID = -1;
        this.d_reinforcementsDeployed = p_reinforcementsDeployed;
    }

    public void execute(){
        int l_currentReinforcements = GameEngine.CURRENT_MAP.getCountry(this.d_fromCountryID).getReinforcements();

        GameEngine.CURRENT_MAP.getCountry(this.d_fromCountryID).setReinforcements(this.d_reinforcementsDeployed + l_currentReinforcements);
        System.out.print("Order Executed: ");
        System.out.println(this.d_reinforcementsDeployed +" troops deployed on "+ GameEngine.CURRENT_MAP.getCountry(this.d_fromCountryID).getD_countryName() + " by " +this.d_playerOrderName);
        

    }
}

