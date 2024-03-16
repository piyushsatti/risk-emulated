package models.Orders;

import controller.GameEngine;
import models.Player;

public class Bomb  implements  Order{
    Player d_sourcePlayer;

    int d_playerOrderID;

    String d_playerOrderName;



    private final int d_bombCountryID;

    public Bomb(Player p_sourcePlayer, int p_playerOrderID, String p_playerOrderName,  int p_bombCountryID) {
        this.d_sourcePlayer = p_sourcePlayer;
        this.d_playerOrderID = p_playerOrderID;
        this.d_playerOrderName = p_playerOrderName;
        this.d_bombCountryID = p_bombCountryID;

    }
   public boolean checkIfNeighbour(){
        for(int countryIDs: this.d_sourcePlayer.getAssignedCountries()){
            if(GameEngine.CURRENT_MAP.getCountry(countryIDs).getBorderCountries().containsKey(this.d_bombCountryID)){
                return true;
            }
        }
        return false;
   }

    @Override
    public void execute(){
        if(this.d_sourcePlayer.getAssignedCountries().contains(d_bombCountryID)){
            System.out.println("Cannot bomb own country!");
            return;
        }

        if(!checkIfNeighbour()){
            System.out.println("Cannot Bomb non neighbouring countries");
        }else{
            int l_currentReinforcementsBombCountry = GameEngine.CURRENT_MAP.getCountry(this.d_bombCountryID).getReinforcements();
            GameEngine.CURRENT_MAP.getCountry(this.d_bombCountryID).setReinforcements(l_currentReinforcementsBombCountry/2);
            return;
        }




    }



}
