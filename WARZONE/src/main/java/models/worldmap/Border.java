package main.java.models.worldmap;

/**
 * Class representing the borders of a country.
 * Each Country object will hold a hashmap of borders (key = target country name)
 */
public class Border {

    /**
     * Variable which is a reference to the target country
     * Meaning this border "points" to the target (directed graph)
     */
    private final Country d_target;

    /**
     * Border constructor, requires a Country object
     *
     * @param p_country reference to target Country for Border object
     */
    public Border(Country p_country) {
        this.d_target = p_country;
    }

    /**
     * Accessor method for private d_target variable
     * @return reference to target Country
     */
    public Country getTarget() {
        return d_target;
    }

}
