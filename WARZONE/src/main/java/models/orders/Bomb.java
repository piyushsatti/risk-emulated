package models.orders;

import controller.GameEngine;
import models.Player;

public class Bomb  implements  Order{
    Player d_sourcePlayer;

    int d_playerOrderID;

    String d_playerOrderName;

    GameEngine d_gameEngine;



    private final int d_bombCountryID;

    public Bomb(Player p_sourcePlayer, int p_playerOrderID, String p_playerOrderName,  int p_bombCountryID, GameEngine p_gameEngine) {
        this.d_sourcePlayer = p_sourcePlayer;
        this.d_playerOrderID = p_playerOrderID;
        this.d_playerOrderName = p_playerOrderName;
        this.d_bombCountryID = p_bombCountryID;
        this.d_gameEngine = p_gameEngine;

    }
   public boolean checkIfNeighbour(){
        for(int countryIDs: this.d_sourcePlayer.getAssignedCountries()){
            if(this.d_gameEngine.d_worldmap.getCountry(countryIDs).getBorderCountries().containsKey(this.d_bombCountryID)){
                return true;
            }
        }
        return false;
   }

    @Override
    public boolean validateCommand(){

        if(this.d_sourcePlayer.getAssignedCountries().contains(d_bombCountryID)){
            System.out.println("Cannot bomb own country!");
            return false;
        }
        if(!checkIfNeighbour()) {
            System.out.println("Cannot Bomb non neighbouring countries");
            return false;
        }

        return true;
    }

    @Override
    public void execute(){

            int l_currentReinforcementsBombCountry = this.d_gameEngine.d_worldmap.getCountry(this.d_bombCountryID).getReinforcements();
            this.d_gameEngine.d_worldmap.getCountry(this.d_bombCountryID).setReinforcements(l_currentReinforcementsBombCountry/2);

    }



}