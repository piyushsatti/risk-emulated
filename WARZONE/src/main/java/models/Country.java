package models;
import java.util.*;

public class Country {
    private int d_countryID;

    private String d_countryName;
    private int d_continentID;
    private int d_deployedArmies;
    private List<Integer> d_neighborListID;
    private Player d_assignedPlayer;

    public ArrayList<Border> d_borderList;

    public Country(int d_countryID, int d_continentID, int d_deployedArmies, List<Integer> d_neighborListID, Player d_assignedPlayer) {
        this.d_countryID = d_countryID;
        this.d_continentID = d_continentID;
        this.d_deployedArmies = d_deployedArmies;
        this.d_neighborListID = d_neighborListID;
        this.d_assignedPlayer = d_assignedPlayer;
        this.d_borderList = new ArrayList<Border>();
    }

    public Country(String name, int continentID){
        this.d_countryName = name;
        this.d_continentID = continentID;
        this.d_borderList = new ArrayList<>();
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
        outPut += "Continent: " + this.d_continentID + "\n";
        outPut += "Border Countries: ";

        int loopIndex = 0;

        for (Country c : this.getBorderCountries()){
            loopIndex++;
            outPut += c.d_countryName;
            if(loopIndex < this.getBorderCountries().size()) outPut += ", ";
        }

        return outPut;
    }
}
