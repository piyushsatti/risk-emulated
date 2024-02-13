package models;
import java.util.HashMap;

/**
 * Class representing the Warzone map
 */
public class Map {

    /**
     * HashMap containing all countries.
     * Key is the Country name and Value is the Country object
     */
    private HashMap<String,Country> d_countries;

    /**
     * HashMap containing the all continents on the map.
     * Key is the Continent name and Value is the Continent object
     */
    private HashMap<String, Continent> d_continents;

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
     * @param continent Continent object which will be associated to new country
     */
    public void addCountry(String countryName, Continent continent){
        if(!this.containsContinent(continent.d_continentName)) { //does the continent exist?
            System.out.println("Continent doesn't exist!");
            return;
        }

        d_countries.put(countryName, new Country(countryName, continent));
    }

    /**
     * Method which adds a border between a source and target country.
     * This method is called from the map object but the border is created within
     * the Country object
     * @param source Country which will contain the new Border
     * @param target Country which border will "point" to
     */
    public void addBorder(String source, String target){
        //check that both countries exist
        if(!(this.containsCountry(source) && this.containsCountry(target))){
            System.out.println("Country doesn't exist!");
            return;
        }

        this.getCountry(source).addBorder(this.getCountry(target));

    }

    /**
     * Method which add continent object ot continents HashMaps
     * @param continent object which will be added to Map
     */
    public void addContinent(Continent continent){
        d_continents.put(continent.d_continentName, continent);
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
            if(c.getD_continent().equals(continent)){
                output.put(c.getD_countryName(),c);
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
