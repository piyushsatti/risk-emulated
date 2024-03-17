package models.worldmap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;


/**
 * Class representing Country to be used within Warzone map
 */
public class Country {

    /**
     * Country integer identifier
     */
    private final int d_countryID;

    /**
     * Country name String
     */
    private final String d_countryName;

    /**
     * The ID of the player who owns the country.
     */
    private int d_country_player_ID;

    /**
     * Count of reinforcements currently deployed in country
     */
    private int d_reinforcements;


    /**
     * HashMap containing the outgoing borders associated with a country
     */
    private final HashMap<Integer, Border> d_borders;

    /**
     * Continent associated with the country
     */
    private final Continent d_continent;

    /**
     * Country constructor
     *
     * @param p_id Identifier integer
     * @param p_name Name of country String
     * @param p_continent Reference to associated continent
     */
    public Country(int p_id, String p_name, Continent p_continent){

        this.d_countryID = p_id;

        this.d_countryName = p_name;

        this.d_borders = new HashMap<>();

        this.d_continent = p_continent;

        this.d_reinforcements = 0;

    }

    /**
     * Returns a string containing details about the country, including its ID, name, and deployed reinforcements.
     *
     * @return A string containing the country details.
     */
    public String returnCountryDetails() {

        return "Country ID: " + this.d_countryID +
                " Country Name: " + this.d_countryName +
                "Deployed Reinforcements: " + this.getReinforcements();

    }

    /**
     * Accessor method for d_countryName attribute
     *
     * @return d_countryName String
     */
    public String getCountryName() {
        return d_countryName;
    }

    /**
     * Accessor method for d_countryID attribute
     *
     * @return d_countryID integer
     */
    public int getCountryID() {
        return d_countryID;
    }

    /**
     * Getter method for Continent object
     *
     * @return Continent object attribute
     */
    public Continent getContinent() {
        return d_continent;
    }

    /**
     * Getter method for a country's owning player
     *
     * @return Country's Player ID
     */
    public int getCountryPlayerID() {
        return d_country_player_ID;
    }

    /**
     * Method which adds Border to d_borders Hashmap
     *
     * @param p_country country which the added border will point to
     */
    public void addBorder(Country p_country){
        this.d_borders.put(p_country.getCountryID(), new Border(p_country));
    }

    /**
     * Method which removes Border to d_borders Hashmap
     *
     * @param p_country country which the border is removed from
     */
    public void removeBorder(Country p_country) {
        this.d_borders.remove(p_country.getCountryID());
    }

    /**
     * Method that returns the HashMap of Border objects associated with a Country
     *
     * @return HashMap of Border objects
     */
    public HashMap<Integer, Border> getBorders(){
        return this.d_borders;
    }

    /**
     * Method which returns HashMap of neighboring countries (outgoing borders)
     *
     * @return HashMap of neighboring countries
     */
    public HashMap<Integer, Country> getBorderCountries(){

        HashMap <Integer, Country> borderCountries = new HashMap<>();

        for (Border l_border : this.d_borders.values()) {

            borderCountries.put(l_border.getTarget().getCountryID(), l_border.getTarget());

        }

        return borderCountries;
    }

    /**
     * Method which checks if a country can reach all the other
     * countries in the list while only moving through countries in the list
     *
     * @param p_countriesToReach HashMap of countries the method is trying to reach
     * @return true if all countries are reached, false if not
     */
    public boolean canReach(HashMap<Integer,Country> p_countriesToReach){


        ArrayList<Country> l_observed = new ArrayList<>();

        Collection<Country> l_countryCollection = p_countriesToReach.values();

        ArrayList<Country> l_notObserved = new ArrayList<>(l_countryCollection);

        l_observed.add(this); //add calling object to observed list (must be present in CountriesToReach)

        while (true){

            int l_observedCount = l_observed.size();

            l_notObserved.removeAll(l_observed); //remove observed countries from not observed list

            if(l_notObserved.isEmpty()) return true; //if notObserved is empty => all countries found => success

            ArrayList<Country> l_observedCopy = new ArrayList<>(l_observed);
            //copy of observed list, needed because we cannot iterate over a list and modify it at the same time

            for (Country l_o: l_observed){  // for each observed country

                HashMap<Integer,Country> l_neighbors = l_o.getBorderCountries(); //get neighbors

                for(Country l_n: l_neighbors.values()){ //for each neighbor found

                    //if not observed and in search list, add to observed list
                    if(!l_observedCopy.contains(l_n) && l_notObserved.contains(l_n)){
                        l_observedCopy.add(l_n);
                    }

                }

            }

            //if size of observed countries hasn't changed => fail

            if(l_observedCount == l_observedCopy.size()) return false;

            l_observed = l_observedCopy; //assign new observed list

        }

    }

    /**
     * Getter method for reinforcement count
     *
     * @return count of reinforcements on country
     */
    public int getReinforcements() {
        return d_reinforcements;
    }

    /**
     * Setter method for reinforcement count of country
     *
     * @param p_reinforcements count of reinforcements on country
     */
    public void setReinforcements(int p_reinforcements) {
        this.d_reinforcements = p_reinforcements;
    }

    /**
     * Set a country's owning player's ID
     *
     * @param p_country_player_ID player id belonging to a country
     */
    public void setCountryPlayerID(int p_country_player_ID) {
        this.d_country_player_ID = p_country_player_ID;
    }

    /**
     * String summary of Country object
     *
     * @return String of Country details (Name, Continent, Borders) to be used in print statements
     */
    @Override
    public String toString() {

        StringBuilder l_outPut = new StringBuilder("Name: " + this.d_countryName + "\n");

        l_outPut.append("Continent: ").append(this.d_continent.d_continentName).append("\n");

        l_outPut.append("Border Countries: ");

        int l_loopIndex = 0;

        for (Country l_c : this.getBorderCountries().values()) {

            l_loopIndex++;

            l_outPut.append(l_c.getCountryName());

            if (l_loopIndex < this.getBorderCountries().size()) l_outPut.append(", ");

        }

        return l_outPut.toString();

    }

}
