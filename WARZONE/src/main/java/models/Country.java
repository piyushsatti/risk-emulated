package models;
import java.util.*;

public class Country {


    private String d_countryName;

    public ArrayList<Border> d_borderList;

    public Continent d_continent;





    public Country(String name, Continent continent){
        this.d_countryName = name;
        this.d_borderList = new ArrayList<>();
        this.d_continent = continent;
    }

    public void addBorder(Country c){
        this.d_borderList.add(new Border(c));
    }

    /**
     * Method that returns the list of Border objects associated with a Country
     * @return List of Border objects
     */
    public ArrayList<Border> getBorders(){
        return this.d_borderList;
    }

    /**
     * Method which returns list of neighboring countries (outgoing borders)
     * @return ArrayList of neighboring countries
     */
    public ArrayList<Country> getBorderCountries(){
        ArrayList <Country> borderCountries = new ArrayList<>();
        for(Border b: this.d_borderList){
            borderCountries.add(b.d_target);
        }

        return borderCountries;
    }

    public String toString(){
        String outPut = "Name: " + this.d_countryName + "\n";
        outPut += "Continent: " + this.d_continent.d_continentName + "\n";
        outPut += "Border Countries: ";

        int loopIndex = 0;

        for (Country c : this.getBorderCountries()){
            loopIndex++;
            outPut += c.d_countryName;
            if(loopIndex < this.getBorderCountries().size()) outPut += ", ";
        }

        return outPut;
    }

    /**
     * Method which checks if a country can reach all the other
     * countries in the list while only moving through countries in the list
     * @param countriesToReach List of countries the method is trying to reach
     * @return true if all countries are reached, false if not
     */
    public boolean canReach(ArrayList<Country> countriesToReach){

        ArrayList<Country> observed = new ArrayList<>();
        ArrayList<Country> notObserved = new ArrayList<>(countriesToReach);
        observed.add(this); //add calling object to observed list (must be present in CountriesToReach)

        while (true){

            int observedCount = observed.size();

            notObserved.removeAll(observed); //remove observed countries from not observed list

            if(notObserved.isEmpty()) return true; //if notObserved is empty => all countries found => success

            ArrayList<Country> observedCopy = new ArrayList<>(observed);
            //copy of observed list, needed because we cannot iterate over a list and modify it at the same time

            for (Country o: observed){  // for each observed country

                ArrayList<Country> neighbors = o.getBorderCountries(); //get neighbors

                for(Country n: neighbors){ //for each neighbor found

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

    public String getCountryName(){
        return this.d_countryName;
    }

}
