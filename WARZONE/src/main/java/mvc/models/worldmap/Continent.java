package mvc.models.worldmap;

/**
 * Class representing the Continents on the Warzone map
 * The Continent objects are identified by an Integer ID.
 * Additional attributes are the continent name and bonus value.
 */
public class Continent {

    /**
     * Integer identifier
     */
    private int d_continentID;

    /**
     * Name of continent
     */
    public String d_continentName;

    /**
     * Value of bonus associated with control of the continent
     */
    private final int d_bonus;


    /**
     * Constructs a new continent with the specified ID, name, and bonus.
     *
     * @param p_id    The ID of the continent.
     * @param p_name  The name of the continent.
     * @param p_bonus The bonus value of the continent.
     */
    public Continent(int p_id, String p_name, int p_bonus) {
        this.d_continentID = p_id;
        this.d_continentName = p_name;
        this.d_bonus = p_bonus; // Bonus set to zero for now (to be used in the future)
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
    public String getContinentName() {
        return d_continentName;
    }

    /**
     * Sets the ID of the continent.
     *
     * @param p_id The ID to set for the continent.
     */
    public void set_continentID(int p_id) {
        this.d_continentID = p_id;
    }
}
