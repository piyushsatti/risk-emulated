package main.java.models.worldmap;

/**
 * Class representing the Continents on the Warzone map
 * The Continent objects are identified by an Integer ID.
 * Additional attributes are the continent name and bonus value.
 */
public class Continent {

    /**
     * Integer identifier
     */
    private final int d_continentID;

    /**
     * Name of continent
     */
    public String d_continentName;

    /**
     * Value of bonus associated with control of the continent
     */
    private final int bonus;


    /**
     * Constructor
     * @param id Integer identifier
     * @param name Continent name
     */
    public Continent(int id, String name, int bonus) {
        this.d_continentID = id;
        this.d_continentName = name;
        this.bonus = bonus; //bonus set to zero for now (to be used in the future)
    }

    /**
     * Accessor method
     * @return Continent integer identifier
     */
    public int getD_continentID() {
        return d_continentID;
    }

    /**
     * Accessor method
     * @return Bonus value
     */
    public int getBonus() {
        return bonus;
    }
}
