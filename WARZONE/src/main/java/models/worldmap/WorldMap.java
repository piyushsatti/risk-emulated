package main.java.models.worldmap;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class representing the Warzone map
 * Contains d_countries which is a HashMap containing all the countries
 * on the map
 */
public class WorldMap {

    /**
     * HashMap containing all countries.
     * Key is the Country name and Value is the Country object
     */
    private final HashMap<Integer, Country> d_countries;

    /**
     * HashMap containing the all continents on the map.
     * Key is the Continent name and Value is the Continent object
     */
    private final HashMap<Integer, Continent> d_continents;

    /**
     * Retrieves the map of countries.
     *
     * @return The map of countries, where the key is the country ID and the value is the corresponding country object.
     */
    public HashMap<Integer, Country> getCountries() {
        return d_countries;
    }

    /**
     * Retrieves the map of continents.
     *
     * @return The map of continents, where the key is the continent ID and the value is the corresponding continent object.
     */
    public HashMap<Integer, Continent> getContinents() {
        return d_continents;
    }

    /**
     * Constructor that initializes the Country and Continent HashMaps
     */
    public WorldMap() {

        this.d_continents = new HashMap<>();

        this.d_countries = new HashMap<>();

    }

    /**
     * Adds a country to the world map.
     *
     * @param countryID   The ID of the country to be added.
     * @param continentID The ID of the continent to which the country belongs.
     * @param countryName The name of the country to be added.
     */
    public void addCountry(int countryID, int continentID, String countryName){

        if (!this.containsContinent(continentID)) { //does the continent exist?

            //throw exception
            return;

        } else if (this.containsCountry(countryID)) { //duplicate country id

            //throw exception
            return;

        } else if (this.containsCountry(countryName)) { //duplicate country name

            //throw exception
            return;

        }

        d_countries.put(countryID, new Country(countryID, countryName, d_continents.get(continentID)));

    }

    /**
     * Method which returns list of country in form of a HashMap <CountryID,Country>
     */
    public  HashMap<Integer, Country> getD_countries() {
        return this.d_countries;
    }

    /**
     * Method which adds a border between a source and target country.
     * This method is called from the map object but the border is created within
     * the Country object
     *
     * @param source Country which will contain the new Border
     * @param target Country which border will "point" to
     */
    public void addBorder(int source, int target) {

        //check that both countries exist
        if (!this.containsCountry(source)) {

            //throw exception
            return;

        } else if (!this.containsCountry(target)) {

            //throw exception

            return;

        }

        this.getCountry(source).addBorder(this.getCountry(target));

    }

    /**
     * Adds borders between the source country and a list of target countries.
     *
     * @param source     The ID of the source country.
     * @param borderList The list of IDs of target countries to add borders with.
     */
    public void addBorders(int source, ArrayList<Integer> borderList){

        //check that both countries exist
        for (Integer i : borderList) {

            if (!this.containsCountry(i)) {

                //throw exception
                return;

            }

        }

        for (Integer i : borderList) {
            this.addBorder(source, i);
        }

    }

    /**
     * Method which adds continent to map
     *
     * @param id new continent identifier
     * @param continentName new continent name
     */
    public void addContinent(int id, String continentName, int bonus) {

        if (this.containsContinent(id)) { //duplicate continent

            //throw error
            return;

        } else if (this.containsContinent(continentName)) { //duplicate name

            //throw error
            return;

        }

        d_continents.put(id, new Continent(id, continentName, bonus));

    }

    /**
     * Method which checks if the Map is a connected graph
     * @return true if fully connected graph, false if not
     */
    public boolean isConnected() {

        boolean connected = true;

        for (Country c : d_countries.values()) {
            connected = connected && c.canReach(d_countries);
        }

        return connected;

    }

    /**
     * Checks if the countries in the provided list are all connected.
     *
     * @param countryList The list of countries to check for connectivity.
     * @return true if all countries in the list are connected, false otherwise.
     */
    public boolean isConnected(HashMap<Integer, Country> countryList) {

        boolean connected = true;

        for (Country c : countryList.values()) {
            connected = connected && c.canReach(countryList);
        }

        return connected;

    }

    /**
     * Checks if all countries within each continent are connected.
     *
     * @return true if all countries within each continent are connected, false otherwise.
     */
    public boolean isContinentConnected() {

        boolean connected = true;

        for (Continent target : this.d_continents.values()) {

            HashMap<Integer,Country> tempCont = this.getContinentCountries(target);

            connected = connected && isConnected(tempCont);

        }

        return connected;

    }

    /**
     * Method which gets list of countries associated with the provided continent
     *
     * @param continent Continent object for which countries should be retrieved
     * @return HashMap of countries associated with continent
     */
    public HashMap<Integer, Country> getContinentCountries(Continent continent) {

        HashMap<Integer, Country> output = new HashMap<>();

        for (Country c : this.d_countries.values()) {

            if (c.getContinent().equals(continent)) {

                output.put(c.getCountryID(), c);

            }

        }

        return output;

    }

    /**
     * Method to remove a continent from the map.
     *
     * @param continentID The identifier of the continent to be removed.
     */
    public void removeContinent(int continentID) {

        Continent continent = d_continents.get(continentID);

        for (Country country : getContinentCountries(continent).values()) {

            this.removeCountry(country.getCountryID());

        }

        d_continents.remove(continentID);

    }

    /**
     * This method removes a country from the map.
     *
     * @param countryID The unique identifier of the country to be removed.
     */
    public void removeCountry(int countryID) {

        Country country = d_countries.get(countryID);

        for (Country countryToCheck : this.d_countries.values()) {

            countryToCheck.removeBorder(country);

        }

        d_countries.remove(countryID);

    }

    /**
     * Method that checks if country exists in map
     *
     * @param countryID Country identifier integer
     * @return true if found false if not found
     */
    public boolean containsCountry(int countryID){
        return this.d_countries.containsKey(countryID);
    }

    /**
     * Checks if the world map contains a country with the specified name.
     *
     * @param countryName The name of the country to check.
     * @return true if the world map contains a country with the specified name, false otherwise.
     */
    public boolean containsCountry(String countryName){
        return this.containsCountry(this.getCountryID(countryName));
    }

    /**
     * Method that checks if continent exists in map
     *
     * @param continentID Continent identifier integer
     * @return true if found false if not found
     */
    public boolean containsContinent(int continentID){
        return this.d_continents.containsKey(continentID);
    }

    /**
     * Checks if the world map contains a continent with the specified name.
     *
     * @param continentName The name of the continent to check.
     * @return true if the world map contains a continent with the specified name, false otherwise.
     */
    public boolean containsContinent(String continentName){

        return this.containsContinent(this.getContinentID(continentName));

    }

    /**
     * Retrieve country object based on country ID
     *
     * @param countryID Identifier of country to search for
     * @return Country object with matching identifier
     */
    public Country getCountry(int countryID){
        return this.d_countries.get(countryID);
    }

    /**
     * Removes a border between two countries.
     *
     * @param source The ID of the source country.
     * @param target The ID of the target country.
     * @throws IllegalArgumentException if either the source or target country does not exist.
     */
    public void removeBorder(int source, int target){

        //check that both countries exist
        if (!(this.containsCountry(source) && this.containsCountry(target))) {

            //throw exception
            return;

        }

        this.getCountry(source).removeBorder(this.getCountry(target));

    }

    /**
     * Gets the ID of a country based on its name.
     *
     * @param countryName The name of the country.
     * @return The ID of the country, or 0 if not found.
     */
    public int getCountryID(String countryName){

        for (Country c : this.getCountries().values()){

            if (c.getCountryName().equals(countryName)) {

                return c.getCountryID();

            }

        }

        return 0;

    }

    /**
     * Gets the ID of a continent based on its name.
     *
     * @param continentName The name of the continent.
     * @return The ID of the continent, or 0 if not found.
     */
    public int getContinentID(String continentName){

        for (Continent c : this.d_continents.values()) {

            if (c.getContinentName().equals(continentName)) {

                return c.getContinentID();

            }

        }

        return 0;

    }

}