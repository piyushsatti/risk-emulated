package main.java.models.map;

public class Continent {

    private int d_continentID;


    /**
     * Name of continent
     */
    public String d_continentName;

    /**
     * Value of bonus associated with control of the continent
     */
    public int bonus;


    public Continent(int id, String name){
        this.d_continentID = id;
        this.d_continentName = name;
        this.bonus = 0;
    }

}
