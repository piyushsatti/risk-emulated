package models;

import models.map.Country;

import java.util.Map;

public class Order {
    String d_PlayerName;
    private String d_orderType;
    private int d_countryID;
    private int d_deployedTroops;
    public Order(String p_PlayerName,String p_orderType,int p_countryID, int p_deployedTroops){
        this.d_PlayerName = p_PlayerName;
        this.d_orderType = p_orderType;
        this.d_countryID = p_countryID;
        this.d_deployedTroops = p_deployedTroops;
    }
    public void printOrder(){
        System.out.print("Name: "+this.d_PlayerName +" Order Type: "+ this.d_orderType+" Country ID: "+this.d_countryID+ "Troops to Be Deployed: " +this.d_deployedTroops);
        System.out.println("");
    }
    public void printExecutedOrder(Country p_selectedCountry){
        System.out.print("Name: "+this.d_PlayerName +" Order Type: "+ this.d_orderType+" Country ID: "+this.d_countryID+ "Troops Deployed on Country: " +p_selectedCountry.getD_deployedReinforcements());
        System.out.println("");
    }
    public  void execute_order(){
        Map<Integer, Country> listOfCountries = Player.getMap().getD_countries();
        Country l_selectedCountry = listOfCountries.get(this.d_countryID);
        l_selectedCountry.setD_deployedReinforcements(l_selectedCountry.getD_deployedReinforcements() + this.d_deployedTroops);
        System.out.println("Order Executed");
        this.printExecutedOrder(l_selectedCountry);

    }
}
