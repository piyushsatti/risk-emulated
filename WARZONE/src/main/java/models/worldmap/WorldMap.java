package models.worldmap;

import helpers.exceptions.ContinentAlreadyExistsException;
import helpers.exceptions.ContinentDoesNotExistException;
import helpers.exceptions.CountryDoesNotExistException;
import helpers.exceptions.DuplicateCountryException;
import models.LogEntryBuffer;
import view.Logger;

import java.util.HashMap;


/**
 * Class representing the Warzone map
 * Contains d_countries which is a HashMap containing all the countries
 * on the map
 */
public class WorldMap {

    LogEntryBuffer logEntryBuffer = new LogEntryBuffer();
    Logger lw = new Logger(logEntryBuffer);

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
     *
     * @param p_continentID ID of continent to which country will be added
     * @param p_countryName name of new country
     * @throws ContinentDoesNotExistException if continent does not exist
     * @throws DuplicateCountryException if country already exists
     */
    public void addCountry(String p_countryName, int p_continentID, int... p_id) throws ContinentDoesNotExistException, DuplicateCountryException {

        if (!this.containsContinent(p_continentID)) { //does the continent exist?

            throw new ContinentDoesNotExistException(p_continentID);

        } else if (this.containsCountry(p_countryName)) { //duplicate country name

            throw new DuplicateCountryException(p_countryName);

        }

        if (p_id != null) {
            d_countries.put(p_id[0], new Country(p_id[0], p_countryName, d_continents.get(p_continentID)));
        } else {
            d_countries.put(Country.id, new Country(Country.id, p_countryName, d_continents.get(p_continentID)));
            Country.id++;
            logEntryBuffer.setString("added country"+p_countryName+" in continent: "+d_continents.get(p_continentID).getContinentName());
        }
    }

    /**
     * Method which returns list of country in form of a HashMap CountryID, Country
     */
    public  HashMap<Integer, Country> getD_countries() {
        return this.d_countries;
    }


    /**
     *
     * @param p_source id of country to which border will be added
     * @param p_target target country of new border
     * @throws CountryDoesNotExistException if either source or target countries do not exist
     */
    public void addBorder(int p_source, int p_target) throws CountryDoesNotExistException {

        //check that both countries exist
        if (!this.containsCountry(p_source)) {

            throw new CountryDoesNotExistException(p_source);

        } else if (!this.containsCountry(p_target)) {

            throw new CountryDoesNotExistException(p_target);

        }

        this.getCountry(p_source).addBorder(this.getCountry(p_target));
        logEntryBuffer.setString("added border between"+this.getCountry(p_source).getCountryName()+" and "+this.getCountry(p_target).getCountryName());

    }


    /**
     * Method which adds continent to map
     *
     * @param p_continentName new continent name
     * @param p_bonus control bonus of new continent
     * @throws ContinentAlreadyExistsException if a continent with same name or id already exists
     */
    public void addContinent(String p_continentName, int p_bonus) throws ContinentAlreadyExistsException {

        if (this.containsContinent(p_continentName)) { // duplicate continent name
            throw new ContinentAlreadyExistsException(p_continentName);
        }

        d_continents.put(Continent.id, new Continent(Continent.id, p_continentName, p_bonus));
        Continent.id++;

        logEntryBuffer.setString("added continent"+p_continentName);
    }

    /**
     * Method which checks if the Map is a connected graph
     * @return true if fully connected graph, false if not
     */
    public boolean isConnected() {

        boolean l_connected = true;

        for (Country l_c : d_countries.values()) {
            l_connected = l_connected && l_c.canReach(d_countries);
        }

        return l_connected;

    }

    /**
     * Checks if the countries in the provided list are all connected.
     *
     * @param p_countryList The hashmap of countries to check for connectivity.
     * @return true if all countries in the list are connected, false otherwise.
     */
    public boolean isConnected(HashMap<Integer, Country> p_countryList) {

        boolean l_connected = true;

        for (Country l_c : p_countryList.values()) {
            l_connected = l_connected && l_c.canReach(p_countryList);
        }

        return l_connected;

    }

    /**
     * Checks if all countries within each continent are connected.
     *
     * @return true if all countries within each continent are connected, false otherwise.
     */
    public boolean isContinentConnected() {

        boolean l_connected = true;

        for (Continent l_target : this.d_continents.values()) {

            HashMap<Integer,Country> l_tempCont = this.getContinentCountries(l_target);

            l_connected = l_connected && isConnected(l_tempCont);

        }

        return l_connected;

    }

    /**
     * Method which gets list of countries associated with the provided continent
     *
     * @param p_continent Continent object for which countries should be retrieved
     * @return HashMap of countries associated with continent
     */
    public HashMap<Integer, Country> getContinentCountries(Continent p_continent) {

        HashMap<Integer, Country> l_output = new HashMap<>();

        for (Country l_c : this.d_countries.values()) {

            if (l_c.getContinent().equals(p_continent)) {

                l_output.put(l_c.getCountryID(), l_c);

            }

        }

        return l_output;

    }

    /**
     * Method used to remove continent from map
     * @param p_continentID id of continent to be removed
     * @throws ContinentDoesNotExistException if continent does not exist
     */
    public void removeContinent(int p_continentID) throws ContinentDoesNotExistException {

        if(!this.containsContinent(p_continentID)) throw new ContinentDoesNotExistException(p_continentID);
        Continent l_continent = d_continents.get(p_continentID);

        for (Country l_country : getContinentCountries(l_continent).values()) {
            try {
                this.removeCountry(l_country.getCountryID());
            } catch (CountryDoesNotExistException e) {
                continue;
            }

        }

        d_continents.remove(p_continentID);
        logEntryBuffer.setString("removed continent"+l_continent.getContinentName());
    }

    /**
     * This method removes a country from the map
     * @param p_countryID Integer identifier of country to be removed
     * @throws CountryDoesNotExistException if country does not exist
     */
    public void removeCountry(int p_countryID) throws CountryDoesNotExistException {

        if(!this.containsCountry(p_countryID)) throw new CountryDoesNotExistException(p_countryID);
        Country l_country = d_countries.get(p_countryID);

        for (Country l_countryToCheck : this.d_countries.values()) {

            l_countryToCheck.removeBorder(l_country);

        }

        d_countries.remove(p_countryID);
        logEntryBuffer.setString("removed country"+d_countries.get(p_countryID).getCountryName());

    }

    /**
     * Method that checks if country exists in map
     *
     * @param p_countryID Country identifier integer
     * @return true if found false if not found
     */
    public boolean containsCountry(int p_countryID){
        return this.d_countries.containsKey(p_countryID);
    }

    /**
     * Checks if the world map contains a country with the specified name.
     *
     * @param p_countryName The name of the country to check.
     * @return true if the world map contains a country with the specified name, false otherwise.
     */
    public boolean containsCountry(String p_countryName) {
        try {
            return this.containsCountry(this.getCountryID(p_countryName));
        } catch (CountryDoesNotExistException e) {
            return false;
        }
    }

    /**
     * Method that checks if continent exists in map
     *
     * @param p_continentID Continent identifier integer
     * @return true if found false if not found
     */
    public boolean containsContinent(int p_continentID){

        return this.d_continents.containsKey(p_continentID);
    }

    /**
     * Checks if the world map contains a continent with the specified name.
     *
     * @param p_continentName The name of the continent to check.
     * @return true if the world map contains a continent with the specified name, false otherwise.
     */
    public boolean containsContinent(String p_continentName) {
        try {
            return this.containsContinent(this.getContinentID(p_continentName));
        } catch (ContinentDoesNotExistException e) {
            return false;
        }
    }

    /**
     * Retrieve country object based on country ID
     *
     * @param p_countryID Identifier of country to search for
     * @return Country object with matching identifier
     */
    public Country getCountry(int p_countryID){
        return this.d_countries.get(p_countryID);
    }

    /**
     * Removes a border between two countries.
     *
     * @param p_source The ID of the source country.
     * @param p_target The ID of the target country.
     * @throws CountryDoesNotExistException if either the source or target country does not exist.
     */
    public void removeBorder(int p_source, int p_target) throws CountryDoesNotExistException {

        //check that both countries exist
        if (!this.containsCountry(p_source)){
            throw new CountryDoesNotExistException(p_source);
        }
        else if(!this.containsCountry(p_target)) {
            throw new CountryDoesNotExistException(p_target);
        }

        this.getCountry(p_source).removeBorder(this.getCountry(p_target));
        logEntryBuffer.setString("removed border between"+this.getCountry(p_source).getCountryName()+" and "+this.getCountry(p_target).getCountryName());

    }

    /**
     * Gets the ID of a country based on its name.
     *
     * @param p_countryName The name of the country.
     * @return The ID of the country, or -1 if not found.
     */
    public int getCountryID(String p_countryName) throws CountryDoesNotExistException {

        for (Country l_c : this.getCountries().values()){

            if (l_c.getCountryName().equals(p_countryName)) {

                return l_c.getCountryID();

            }

        }

        throw new CountryDoesNotExistException("Country " + p_countryName + " does not exist.");
    }

    /**
     * Gets the ID of a continent based on its name.
     *
     * @param p_continentName The name of the continent.
     * @return The ID of the continent, or -1 if not found.
     */
    public int getContinentID(String p_continentName) throws ContinentDoesNotExistException {

        for (Continent l_c : this.d_continents.values()) {

            if (l_c.getContinentName().equals(p_continentName)) {

                return l_c.getContinentID();

            }

        }

        throw new ContinentDoesNotExistException("Continent " + p_continentName + "does not exist.");

    }

}