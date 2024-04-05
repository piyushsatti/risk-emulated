package strategy;

import controller.GameEngine;
import models.Player;
import models.orders.Deploy;
import models.orders.Order;
import models.worldmap.Country;

import java.util.ArrayList;
import java.util.Random;

public class CheaterStrategy implements Strategy{

    private Player d_player;

    private GameEngine d_gameEngine;
    public CheaterStrategy(Player p_player, GameEngine p_gameEngine) {
        this.d_player = p_player;
        this.d_gameEngine = p_gameEngine;
    }
    public int getSourceCountry() {
        return this.d_player.getAssignedCountries().get(0);
    }

    @Override
    public int getTargetCountry(int p_sourceCountryId) {
        return 0;
    }

    public ArrayList<Integer> getNeighbouringCountry(int p_sourceCountry){

        ArrayList<Integer> l_listOfAllBorderCountriesIDs = new ArrayList<>();
        for(Integer id : d_gameEngine.d_worldmap.getCountry(p_sourceCountry).getAllBorderCountriesIDs()){
            if(!this.d_player.getAssignedCountries().contains(id)){
            l_listOfAllBorderCountriesIDs.add(id);}
        }
        return l_listOfAllBorderCountriesIDs;
    }


    public Order createOrder(){

        int l_sourceCountry = getSourceCountry();
        ArrayList<Integer> l_neighbourCountries=getNeighbouringCountry(l_sourceCountry);

        for(int l_countryId:l_neighbourCountries){
            boolean l_isNeutral = true;

            for(Player l_player:this.d_gameEngine.d_players){

                if(l_player.getAssignedCountries().contains(l_countryId)){
                    l_isNeutral = false;
                 l_player.removeAssignedCountries(l_countryId);
                 this.d_player.setAssignedCountries(l_countryId);
                 int l_armies=this.d_gameEngine.d_worldmap.getCountry(l_countryId).getReinforcements();
                 this.d_gameEngine.d_worldmap.getCountry(l_countryId).setReinforcements(l_armies*2);
                 break;
                }

            }

            if(l_isNeutral){
                this.d_player.setAssignedCountries(l_countryId);
                int l_armies=this.d_gameEngine.d_worldmap.getCountry(l_countryId).getReinforcements();
                this.d_gameEngine.d_worldmap.getCountry(l_countryId).setReinforcements(l_armies*2);
            }
        }


        return null;
    }
}
