package models;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class representing the Warzone map
 * Contains d_countryList which is a list containing all the countries
 * on the map
 */
public class Map {

    public HashMap<String,Country> d_countries;
    public HashMap<String, Continent> d_continents;

    public Map(){
        this.d_continents = new HashMap<>();
        this.d_countries = new HashMap<>();
    }

    public void addCountry(Country country){
        d_countries.put(country.getCountryName(),country);
    }

    public void addCountry(Country country, Continent continent){
        if(!d_continents.containsKey(continent.d_continentName)) {
            System.out.println("Continent doesn't exist!");
            return;
        }
        country.d_continent = continent;
        d_countries.put(country.getCountryName(),country);
    }

    public void addCountry(String countryName, Continent continent){
        if(!d_continents.containsKey(continent.d_continentName)) {
            System.out.println("Continent doesn't exist!");
            return;
        }

        d_countries.put(countryName, new Country(countryName, continent));
    }

    public void addBorder(String source, String target){
        if(!(this.containsCountry(source) && this.containsCountry(target))){
            System.out.println("Country doesn't exist!");
            return;
        }

        this.getCountry(source).addBorder(this.getCountry(target));

    }

    public void addContinent(Continent continent){
        d_continents.put(continent.d_continentName, continent);
    }

    public boolean isConnected(){
        boolean connected = true;
        for (Country c : d_countries.values()){
            connected = connected && c.canReach(d_countries);
        }
        return connected;
    }

    public boolean isConnected(HashMap<String, Country> countryList){
        boolean connected = true;
        for (Country c : countryList.values()){
            connected = connected && c.canReach(countryList);
        }
        return connected;
    }

    public boolean isContinentConnected(){
        boolean connected = true;
        for(Continent target: this.d_continents.values()){
            HashMap<String,Country> tempCont = this.getContinentCountries(target);
            connected = connected && isConnected(tempCont);
        }

        return connected;
    }

    public HashMap<String, Country> getContinentCountries(Continent continent){
        HashMap<String, Country> output = new HashMap<>();
        for(Country c: this.d_countries.values()){
            if(c.d_continent.equals(continent)){
                output.put(c.getCountryName(),c);
            }
        }
        return output;
    }

    public boolean containsCountry(String c){
        return this.d_countries.containsKey(c);
    }

    public boolean containsContinent(String c){
        return this.d_continents.containsKey(c);
    }

    public Country getCountry(String countryName){
        return this.d_countries.get(countryName);
    }
}
