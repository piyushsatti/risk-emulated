package main.java.models.map;

public class Continent {
    /**
     * Name of continent
     */
    public String d_continentName;

    /**
     * Value of bonus associated with control of the continent
     */
    public int bonus;

    public Continent(String name, int bonusValue){
        this.d_continentName = name;
        this.bonus = bonusValue;
    }

}