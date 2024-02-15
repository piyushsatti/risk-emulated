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

    /**
     * Hashmap containing Border objects associated with country
     */
    private final HashMap<Integer, Border> d_borders;

    /**
     * Continent associated with the country
     */
    private final Continent d_continent;

    private int d_deployedReinforcements;

    public int getD_deployedReinforcements() {
        return d_deployedReinforcements;
    }

    public void setD_deployedReinforcements(int p_deployedReinforcements) {
        this.d_deployedReinforcements = p_deployedReinforcements;
    }

    /**
     * Country constructor
     * @param id Identifier integer
     * @param name Name of country String
     * @param continent Reference to associated continent
     */
    public Country(int id, String name, Continent continent){
        this.d_countryID = id;
        this.d_countryName = name;
        this.d_borders = new HashMap<>();
        this.d_continent = continent;
        this.d_deployedReinforcements = 0;
    }
    public void printCountryDetails(){
        System.out.print("Country ID: "+this.d_countryID +" Country Name: " + this.d_countryName + "Deployed Reinforcements: "+ this.d_deployedReinforcements);
    }

    /**
     * Accessor method for d_countryName attribute
     * @return d_countryName String
     */
    public String getD_countryName() {
        return d_countryName;
    }

    /**
     * Accessor method for d_countryID attribute
     * @return d_countryID integer
     */
    public int getD_countryID(){
        return d_countryID;
    }

    /**
     * Getter method for Continent object
     * @return Continent object attribute
     */
    public Continent getD_continent() {
        return d_continent;
    }

    /**
     * Method which adds Border to d_borders Hashmap
     * @param country country which the added border will point to
     */
    public void addBorder(Country country){
        this.d_borders.put(country.getD_countryID(),new Border(country));
    }

    /**
     * Method that returns the HashMap of Border objects associated with a Country
     * @return HashMap of Border objects
     */
    public HashMap<Integer, Border> getBorders(){
        return this.d_borders;
    }

    /**
     * Method which returns HashMap of neighboring countries (outgoing borders)
     * @return HashMap of neighboring countries
     */
    public HashMap<Integer, Country> getBorderCountries(){
        HashMap <Integer, Country> borderCountries = new HashMap<>();

        for (Border b : this.d_borders.values()) {
            borderCountries.put(b.getD_target().getD_countryID(), b.getD_target());
        }

        return borderCountries;
    }

    /**
     * String summary of Country object
     * @return String of Country details (Name, Continent, Borders) to be used in print statements
     */
    public String toString(){
        String outPut = "Name: " + this.d_countryName + "\n";
        outPut += "Continent: " + this.d_continent.d_continentName + "\n";
        outPut += "Border Countries: ";

        int loopIndex = 0;

        for (Country c : this.getBorderCountries().values()){
            loopIndex++;
            outPut += c.getD_countryName();
            if(loopIndex < this.getBorderCountries().size()) outPut += ", ";
        }

        return outPut;
    }

    /**
     * Method which checks if a country can reach all the other
     * countries in the list while only moving through countries in the list
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


}
