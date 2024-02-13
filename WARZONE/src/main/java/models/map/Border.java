package main.java.models.map;

/**
 * Class representing the borders of a country.
 * Contains variable d_target pointing to a single neighbor
 */
public class Border {
    public Country d_target;

    public Border(Country c){
        this.d_target = c;
    }
}
