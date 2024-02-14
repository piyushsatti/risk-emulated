package main.java.models.map;

import java.util.HashMap;
import java.util.ArrayList;

/**
 * Class representing the Warzone map
 * Contains d_countryList which is a list containing all the countries
 * on the map
 */
public class Map {

    /**
     * HashMap containing all countries.
     * Key is the Country name and Value is the Country object
     */
    private HashMap<Integer,Country> d_countries;

    /**
     * HashMap containing the all continents on the map.
     * Key is the Continent name and Value is the Continent object
     */
    private HashMap<Integer, Continent> d_continents;

    /**
     * Constructor that initializes the Country and Continent HashMaps
     */
    public Map(){
        this.d_continents = new HashMap<>();
        this.d_countries = new HashMap<>();
    }

    /**
     * This method adds a country to the map. It is necessary to have an existing Continent to
     * associate to the new country
     * @param countryName Name of the country to be added
     * @param continentID Continent name which will be associated to new country (if present)
     */
    public void addCountry(int countryID, int continentID, String countryName){
        if(!this.containsContinent(continentID)) { //does the continent exist?
            System.out.println("Continent doesn't exist!");
            return;
        }

        d_countries.put(countryID, new Country(countryID, countryName, d_continents.get(continentID)));
    }

    /**
     * Method which adds a border between a source and target country.
     * This method is called from the map object but the border is created within
     * the Country object
     * @param source Country which will contain the new Border
     * @param target Country which border will "point" to
     */
    public void addBorder(int source, int target){
        //check that both countries exist
        if(!(this.containsCountry(source) && this.containsCountry(target))){
            System.out.println("Country doesn't exist!");
            return;
        }

        this.getCountry(source).addBorder(this.getCountry(target));

    }

    public void addBorders(int source, ArrayList<Integer> borderList){
        //check that both countries exist
        for(Integer i : borderList){
            if(!this.containsCountry(i)){
                System.out.println("Country " + i + " is missing! Abort!");
                return;
            }
        }

        for(Integer i : borderList){
            this.addBorder(source,i);
        }

    }


    /**
     * Method which add continent object ot continents HashMaps
     * @param continentName name of Continent object which will be added to Map
     */
    public void addContinent(int id, String continentName){
        d_continents.put(id, new Continent(id,continentName));
    }

    /**
     * Method which checks if the Map is a connected graph
     * @return true if fully connected graph, false if not
     */
    public boolean isConnected(){
        boolean connected = true;
        for (Country c : d_countries.values()){
            connected = connected && c.canReach(d_countries);
        }
        return connected;
    }

    public boolean isConnected(HashMap<Integer, Country> countryList){
        boolean connected = true;
        for (Country c : countryList.values()){
            connected = connected && c.canReach(countryList);
        }
        return connected;
    }

    public boolean isContinentConnected(){
        boolean connected = true;
        for(Continent target: this.d_continents.values()){
            HashMap<Integer,Country> tempCont = this.getContinentCountries(target);
            connected = connected && isConnected(tempCont);
        }

        return connected;
    }

    public HashMap<Integer, Country> getContinentCountries(Continent continent){
        HashMap<Integer, Country> output = new HashMap<>();
        for(Country c: this.d_countries.values()){
            if(c.getD_continent().equals(continent)){
                output.put(c.getD_countryID(),c);
            }
        }
        return output;
    }

    public boolean containsCountry(int countryID){
        return this.d_countries.containsKey(countryID);
    }

    public boolean containsContinent(int continentID){
        return this.d_continents.containsKey(continentID);
    }

    public Country getCountry(int countryID){
        return this.d_countries.get(countryID);
    }
}
