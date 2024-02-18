package main.java.models.worldmap;

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
     * @param id Identifier integer
     * @param name Name of country String
     * @param continent Reference to associated continent
     */
    public Country(int id, String name, Continent continent){

        this.d_countryID = id;

        this.d_countryName = name;

        this.d_borders = new HashMap<>();

        this.d_continent = continent;

        this.d_reinforcements = 0;

    }

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
     * @param country country which the added border will point to
     */
    public void addBorder(Country country){
        this.d_borders.put(country.getCountryID(), new Border(country));
    }

    /**
     * Method which removes Border to d_borders Hashmap
     *
     * @param country country which the border is removed from
     */
    public void removeBorder(Country country) {
        this.d_borders.remove(country.getCountryID());
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

        for (Border b : this.d_borders.values()) {

            borderCountries.put(b.getTarget().getCountryID(), b.getTarget());

        }

        return borderCountries;
    }

    /**
     * Method which checks if a country can reach all the other
     * countries in the list while only moving through countries in the list
     *
     * @param countriesToReach HashMap of countries the method is trying to reach
     * @return true if all countries are reached, false if not
     */
    public boolean canReach(HashMap<Integer,Country> countriesToReach){


        ArrayList<Country> observed = new ArrayList<>();

        Collection<Country> countryCollection = countriesToReach.values();

        ArrayList<Country> notObserved = new ArrayList<>(countryCollection);

        observed.add(this); //add calling object to observed list (must be present in CountriesToReach)

        while (true){

            int observedCount = observed.size();

            notObserved.removeAll(observed); //remove observed countries from not observed list

            if(notObserved.isEmpty()) return true; //if notObserved is empty => all countries found => success

            ArrayList<Country> observedCopy = new ArrayList<>(observed);
            //copy of observed list, needed because we cannot iterate over a list and modify it at the same time

            for (Country o: observed){  // for each observed country

                HashMap<Integer,Country> neighbors = o.getBorderCountries(); //get neighbors

                for(Country n: neighbors.values()){ //for each neighbor found

                    //if not observed and in search list, add to observed list
                    if(!observedCopy.contains(n) && notObserved.contains(n)){
                        observedCopy.add(n);
                    }

                }

            }

            //if size of observed countries hasn't changed => fail

            if(observedCount == observedCopy.size()) return false;

            observed = observedCopy; //assign new observed list

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
     * @param reinforcements count of reinforcements on country
     */
    public void setReinforcements(int reinforcements) {
        this.d_reinforcements = reinforcements;
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

        StringBuilder outPut = new StringBuilder("Name: " + this.d_countryName + "\n");

        outPut.append("Continent: ").append(this.d_continent.d_continentName).append("\n");

        outPut.append("Border Countries: ");

        int loopIndex = 0;

        for (Country c : this.getBorderCountries().values()) {

            loopIndex++;

            outPut.append(c.getCountryName());

            if (loopIndex < this.getBorderCountries().size()) outPut.append(", ");

        }

        return outPut.toString();

    }

}
