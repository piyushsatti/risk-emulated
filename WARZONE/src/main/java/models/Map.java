package models;

import java.util.ArrayList;

/**
 * Class representing the Warzone map
 * Contains d_countryList which is a list containing all the countries
 * on the map
 */
public class Map {

    public ArrayList<Country> d_countryList;
    public ArrayList<Continent> d_continentList;

    public Map(){
        this.d_continentList = new ArrayList<Continent>();
        this.d_countryList = new ArrayList<Country>();
    }

    public void addCountry(Country c){
        d_countryList.add(c);
    }

    public void addCountry(Country country, Continent continent){
        if(!d_continentList.contains(continent)) {
            System.out.println("Continent doesn't exist!");
            return;
        }
        country.d_continent = continent;
        d_countryList.add(country);
    }

    public void addCountry(String countryName, Continent continent){
        if(!d_continentList.contains(continent)) {
            System.out.println("Continent doesn't exist!");
            return;
        }
        d_countryList.add(new Country(countryName, continent));
    }

    public void addBorder(String source, String target){
        if(!(this.containsCountry(source) && this.containsCountry(target))){
            System.out.println("Country doesn't exist!");
            return;
        }

        this.getCountry(source).addBorder(this.getCountry(target));

    }

    public void addContinent(Continent c){
        d_continentList.add(c);
    }

    public boolean isConnected(){
        boolean connected = true;
        for (Country c : d_countryList){
            connected = connected && c.canReach(d_countryList);
        }
        return connected;
    }

    public boolean isConnected(ArrayList<Country> countryList){
        boolean connected = true;
        for (Country c : countryList){
            connected = connected && c.canReach(countryList);
        }
        return connected;
    }

    public boolean isContinentConnected(){
        boolean connected = true;
        for(Continent target: this.d_continentList){
            ArrayList<Country> tempContList = this.getContinentCountries(target);
            connected = connected && isConnected(tempContList);
        }

        return connected;
    }

    public ArrayList<Country> getContinentCountries(Continent continent){
        ArrayList<Country> output = new ArrayList<>();
        for(Country c: this.d_countryList){
            if(c.d_continent.equals(continent)){
                output.add(c);
            }
        }
        return output;
    }

    public boolean containsCountry(String c){
        for(Country cont: this.d_countryList){
            if(cont.getCountryName().equals(c)) return true;
        }
        return false;
    }

    public boolean containsContinent(String c){
        for(Continent cont: this.d_continentList){
            if(cont.d_continentName.equals(c)) return true;
        }
        return false;
    }

    public Country getCountry(String countryName){
        for(Country c: this.d_countryList){
            if(c.getCountryName().equals(countryName)){
                return c;
            }
        }
        return null;
    }
}
