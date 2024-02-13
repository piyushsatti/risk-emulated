package main.java.models.map;

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
     * Accessor method for private d_target variable
     * @return reference to target Country
     */
    public Country getD_target() {
        return d_target;
    }

    /**
     * Border constructor, requires a Country object
     * @param country reference to target Country for Border object
     */
    public Border(Country country){
        this.d_target = country;
    }
}
