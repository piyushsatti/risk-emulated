package models.worldmap;
/**
 * The Border class represents a border between two countries on a world map.
 * It connects two countries and provides access to the target country.
 */
public class Border {

    /**
     * The target country that shares the border.
     */
    private final Country d_target;

    /**
     * Constructs a new Border object connecting to the specified target country.
     *
     * @param p_country The country beyond this border.
     */
    public Border(Country p_country) {
        this.d_target = p_country;
    }

    /**
     * Retrieves the target country beyond this border.
     *
     * @return The target country connected by this border.
     */
    public Country getTarget() {
        return d_target;
    }

}
