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
    private final int d_bonus;

    /**
     * Constructor
     *
     * @param p_id Integer identifier
     * @param p_name Continent name
     */
    public Continent(int p_id, String p_name, int p_bonus) {

        this.d_continentID = p_id;

        this.d_continentName = p_name;

        this.d_bonus = p_bonus; //bonus set to zero for now (to be used in the future)

    }

    /**
     * Accessor method
     *
     * @return Continent integer identifier
     */
    public int getContinentID() {
        return d_continentID;
    }

    /**
     * Accessor method
     *
     * @return Bonus value
     */
    public int getBonus() {
        return d_bonus;
    }

    /**
     * Getter method for continent name
     *
     * @return continent name string
     */
    public String getContinentName()
    {
        return d_continentName;
    }
}
