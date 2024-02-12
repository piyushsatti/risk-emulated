package models;

import java.util.ArrayList;

/**
 * Class representing the Warzone map
 * Contains d_countryList which is a list containing all the countries
 * on the map
 */
public class Map {

    public ArrayList<Country> d_countryList;

    public Map(){
        this.d_countryList = new ArrayList<Country>();
    }

    public void addCountry(Country c){
        d_countryList.add(c);
    }

    public boolean isConnected(){
        boolean connected = true;
        for (Country c : d_countryList){
            connected = connected && c.canReach(d_countryList);
        }
        return connected;
    }



}
