package main.java.models;

import main.java.controller.GameEngine;



public class Order {
    int d_playerOrderID;

    private final int d_fromCountryID;
    private final int d_toCountryID;
    private final int d_reinforcementsDeployed;

    public Order(int p_playerOrderID,int p_fromCountryID,int p_reinforcementsDeployed){
        this.d_playerOrderID = p_playerOrderID;
        this.d_fromCountryID = p_fromCountryID;
        this.d_toCountryID = -1;
        this.d_reinforcementsDeployed = p_reinforcementsDeployed;
    }
//    public void printOrder(){
//        System.out.println("Name: " + this.d_PlayerName + " Order Type: " + this.d_orderType + " Country ID: " + this.d_countryID + "Troops to Be Deployed: " + this.d_deployedTroops);
//    }
//    public void printExecutedOrder(Country p_selectedCountry){
//        System.out.println("Name: " + this.d_PlayerName + " Order Type: " + this.d_orderType + " Country ID: " + this.d_countryID + "Troops Deployed on Country: " + p_selectedCountry.getD_deployedReinforcements());
//    }
    public void execute(){
        GameEngine.CURRENT_MAP.getCountry(this.d_fromCountryID).setReinforcements(this.d_reinforcementsDeployed);
        System.out.println("Order Executed");
        

    }
}

