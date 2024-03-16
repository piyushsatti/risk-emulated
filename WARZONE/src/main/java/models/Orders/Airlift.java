package models.Orders;

import controller.GameEngine;
import models.Player;
import org.apache.commons.compress.utils.OsgiUtils;

public class Airlift implements Order{

    Player d_sourcePlayer;


    int d_playerOrderID;


    String d_playerOrderName;

    private final int d_fromCountryID;

    private final int d_toCountryID;

    private final int d_airliftedTroops;


    public Airlift(Player p_sourcePlayer, String p_playerOrderName, int p_playerOrderID, int p_fromCountryID, int p_toCountryID, int p_airliftedTroops) {
        this.d_sourcePlayer = p_sourcePlayer;

        this.d_playerOrderName = p_playerOrderName;

        this.d_playerOrderID = p_playerOrderID;

        this.d_fromCountryID = p_fromCountryID;

        this.d_toCountryID = p_toCountryID;

        this.d_airliftedTroops = p_airliftedTroops ;
    }

    @Override
    public void execute() {
        if (!d_sourcePlayer.getAssignedCountries().contains(d_fromCountryID)) {
            System.out.println("Player does not own the source country");
            return;
        }
        if(!d_sourcePlayer.getAssignedCountries().contains(d_toCountryID)){
            System.out.println("Player does not own the target country");
            return;
        }
        int l_currentReinforcementsFromCountry = GameEngine.CURRENT_MAP.getCountry(this.d_fromCountryID).getReinforcements();
        if(this.d_airliftedTroops <= l_currentReinforcementsFromCountry && this.d_airliftedTroops >0){
            int l_currentReinforcementsToCountry = GameEngine.CURRENT_MAP.getCountry(this.d_toCountryID).getReinforcements();
            GameEngine.CURRENT_MAP.getCountry(this.d_fromCountryID).setReinforcements(l_currentReinforcementsFromCountry - this.d_airliftedTroops);
            GameEngine.CURRENT_MAP.getCountry(this.d_toCountryID).setReinforcements(l_currentReinforcementsToCountry + this.d_airliftedTroops);
            return;
        }else{
            System.out.println("Invalid troops. Either troops are not enough or in negative.");
        }


    }

}
